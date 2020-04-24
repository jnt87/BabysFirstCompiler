import java.util.*;
import ir.*;
import ir.datatype.IRArrayType;
import ir.operand.IROperand;
import ir.operand.IRVariableOperand;

public class InstructionSelection {

    public static HashMap<String, String> registerValuePair;    // variable in IR is the key and register used to stored is value
    public static HashMap<String, String> argumentRegister;
    public static HashMap<IRFunction, List<String>> usedRegisters;
    private static IRFunction currFunc;
    private static boolean conversionComplete = false;

    private static boolean DEBUG = false;

    public static String[] preserveRegisters = new String[] {"$a0", "$a1", "$a2",
        "$a3", "$8", "$9", "$10", "$11", "$12", "$13", "$14", "$15", "$16", "$17", "$18",
        "$19", "$20", "$21", "$22", "$23", "$28", "$29", "$ra"};  // Registers we can override
        // NOTE: For this calling convention, I deem every register usable except for $0, $1, $26, and $27.
        // All other registers must be preserved between subroutine calls
        // $30 or $fp is now being used as a temporaray operator, now reserved
        // $28 or $gp is now reserved for spill operations
    public static String[] usableRegisters = new String[] {"$8", "$9", "$10", "$11", "$12", "$13", "$14", "$15", "$16", "$17", "$18",
    "$19", "$20", "$21", "$22", "$23"};
    public static String[] reserved = new String[] {"$0", "$1", "$24", "$25", "$26", "$27","$28", "$29", "$30", "$31",
        "$zero", "$at", "$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra"};
    
    public static boolean virtualRegConvention = false;
    private static HashMap<String, List<String>> spilledRegisters;      // the key is the function name and the value is a list of spilt registers

    public static void initialSetup() {
        registerValuePair = new HashMap<>();
        argumentRegister = new HashMap<>();
        usedRegisters = new HashMap<>();
        spilledRegisters = new HashMap<>();
        argumentRegister.put("$a0", "$a0");
        argumentRegister.put("$a1", "$a1");
        argumentRegister.put("$a2", "$a2");
        argumentRegister.put("$a3", "$a3");
    }

    public static HashMap<IRFunction, List<String>> handleInstruction(IRProgram program) {
        initialSetup();
        List<IRFunction> functions = program.functions;
        HashMap<IRFunction, List<String>> translated = new HashMap<>();

        for (IRFunction function : functions) {
            currFunc = function;
            usedRegisters.put(function, new ArrayList<>());
            spilledRegisters.put(function.name, new ArrayList<>());
            usedRegisters.get(function).add("$ra");
            registerValuePair.clear();
            translated.put(function, new ArrayList<String>());
            translated.get(function).add(String.format("%s():\n", function.name));
            translated.get(function).add(extractArguments(function.parameters));
            if (DEBUG) {
                System.out.println("Arguments " + function.parameters + " for function " + function.name);
                System.out.println("The var is " + function.variables);
                System.out.println("The param is " + function.parameters);    
            }
            
            for (IRVariableOperand var : function.variables) {
                String init = "";
                if (!function.parameters.contains(var) && var.type instanceof IRArrayType) {
                    init += String.format("\tmove %s, %s\n", "$30", "$a0");
                    init += String.format("\tli %s, %d\n", "$v0", 9);
                    init += String.format("\tli %s, %d\n", "$a0", ((IRArrayType) var.type).getSize() * 4);
                    init += String.format("\tsyscall\n");
                    init += String.format("\tmove %s, %s\n", getRegisterVar(var.getName()), "$v0");
                    init += String.format("\tmove %s, %s\n", "$a0", "$30");
                } //else {
                // this portion is in charge of preinitializing variables if this is needed (don't think so but maybe)
                //     init += String.format("\tli %s, 0\n", getRegisterVar(var.getName()));
                // }
                translated.get(function).add(init);
            }
            
            for (IRInstruction instruc : function.instructions) {
                String result = "";
                
                switch (instruc.opCode) {
                    case ASSIGN:
                        result = assign(instruc);
                        break;
                    case ADD:
                        result = add(instruc);
                        break;
                    case SUB:
                        result = sub(instruc);
                        break;
                    case MULT:
                        // result = mult(instruc);
                        result = binary_op(instruc, "mul");
                        break;
                    case DIV:
                        // result = div(instruc);
                        result = binary_op(instruc, "div");
                        break;
                    case AND:
                        // result = and(instruc);
                        result = binary_op(instruc, "and");
                        break;
                    case OR:
                        // result = or(instruc);
                        result = binary_op(instruc, "or");
                        break;
                    case GOTO:
                        result = goto_op(instruc);
                        break;
                    case BREQ:
                        result = branch(instruc, "beq");
                        break;
                    case BRNEQ:
                        result = branch(instruc, "bne");
                        break;
                    case BRLT:
                        result = branch(instruc, "blt");
                        break;
                    case BRGT:
                        result = branch(instruc, "bgt");
                        break;
                    case BRLEQ:
                        result = branch(instruc, "ble");
                        break;
                    case BRGEQ:
                        result = branch(instruc, "bge");
                        break;
                    case RETURN:
                        result = return_op(instruc);
                        break;
                    case CALL:
                        result = call(instruc);
                        break;
                    case CALLR:
                        result = callr(instruc);
                        break;
                    case ARRAY_STORE:
                        result = array_store(instruc);
                        break;
                    case ARRAY_LOAD:
                        result = array_load(instruc);
                        break;
                    case LABEL:
                        result = label(instruc);
                        break;
                    default:
                        result = "YOU MESSED UP SOMEHOW";
                }
                translated.get(function).add(result);
                if (DEBUG) {
                    // System.out.println("Original: " + instruc.toString());
                    // System.out.println("Mips: " + result);
                    // if (!result.equals("")) {
                    //     System.out.print(result);
                    // }
                }
            }

            if (function.returnType == null) {
                translated.get(function).add("\tjr $ra\n");
            }
            if (function.name.equals("main")) {
                String line = "";
                line += String.format("\tli $v0, 10\n");
                line += String.format("\tsyscall\n");
                translated.get(function).add(line);
            }
            if (DEBUG) {
                System.out.println("Value Register Pair: " + registerValuePair);
                System.out.println("----FUNCTION ENDED----");
            }
        }
        conversionComplete = true;
        return translated;

    }

    public static String assign(IRInstruction instruction) {
        String line = "\t#assign\n";
        IROperand[] operands = instruction.operands;
        if (operands.length > 2) {
            return array_assign(instruction);
        }
        if (checkImmediate(operands[1].toString())) {
            line += String.format("\tli %s, %s\n", "$30", operands[1].toString());
            line += String.format("\tmove %s, %s\n", getRegisterVar(operands[0].toString()), "$30");
            return line;
        }
        line += String.format("\tmove %s, %s\n", getRegisterVar(operands[0].toString()), getRegisterVar(operands[1].toString()));
        return line;
    }

    public static String array_assign(IRInstruction instruction) {
        String line = "\t#array_assign\n";
        IROperand[] operands = instruction.operands;
        try {
            line += String.format("\tli %s, %s\n", "$30", operands[2].toString());
        } catch (Exception e) {
            System.out.println("Exception occur for " + instruction);
            throw new RuntimeException("manual");
        }
        
        for (int i = 0; i < Integer.parseInt(operands[1].getValue()); i++) {
            line += String.format("\tsw %s, %d(%s)\n", "$30", (i * 4), getRegisterVar(operands[0].toString()));
        }
        return line;
    }

    public static String add(IRInstruction instruction) {
        String line = "\t#add\n";
        IROperand[] operands = instruction.operands;
        if (!checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            String storeTo = getRegisterVar(operands[0].toString());
            String add1 = getRegisterVar(operands[1].toString());
            String add2 = getRegisterVar(operands[2].toString());
            line += String.format("\tadd %s, %s, %s\n", storeTo, add1, add2);
            return line;
        } else if (!checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            String storeTo = getRegisterVar(operands[0].toString());
            String add1 = getRegisterVar(operands[1].toString());
            line += String.format("\taddi %s, %s, %s\n", storeTo, add1, operands[2].toString());
            return line;
        } else if (checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            String storeTo = getRegisterVar(operands[0].toString());
            String add2 = getRegisterVar(operands[2].toString());
            line += String.format("\taddi %s, %s, %s\n", storeTo, operands[1].toString(), add2);
            return line;
        } else {
            return "\twelp, oh well\n";
        }
    }

    public static String sub(IRInstruction instruction) {
        String line = "\t#sub\n";
        IROperand[] operands = instruction.operands;
        if (!checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            String storeTo = getRegisterVar(operands[0].toString());
            String add1 = getRegisterVar(operands[1].toString());
            String add2 = getRegisterVar(operands[2].toString());
            line += String.format("\tsub %s, %s, %s\n", storeTo, add1, add2);
            return line;
        } else if (!checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            String storeTo = getRegisterVar(operands[0].toString());
            String add1 = getRegisterVar(operands[1].toString());
            line += String.format("\taddi %s, %s, -%s\n", storeTo, add1, operands[2].toString());
            return line;
        } else if (checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            String storeTo = getRegisterVar(operands[0].toString());
            String add2 = getRegisterVar(operands[2].toString());
            line += String.format("\taddi %s, %s, -%s\n", storeTo, operands[1].toString(), add2);
            return line;
        } else {
            return "\twelp, oh well\n";
        }
    }
    
    // All binary op except for addition and subtraction
    public static String binary_op(IRInstruction instruction, String op) {
        String line = "\t#or\n";
        IROperand[] operands = instruction.operands;
        String storeTo = getRegisterVar(operands[0].toString());
        if (!checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            String add1 = getRegisterVar(operands[1].toString());
            String add2 = getRegisterVar(operands[2].toString());
            line += String.format("\t%s %s, %s, %s\n", op, storeTo, add1, add2);
            return line;
        } else if (!checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            String add1 = getRegisterVar(operands[1].toString());
            line += String.format("\tli $30, %s\n", operands[2].toString());
            line += String.format("\t%s %s, %s, %s\n", op, storeTo, add1, "$30");
            return line;
        } else if (checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            String add2 = getRegisterVar(operands[2].toString());
            line += String.format("\tli $30, %s\n", operands[1].toString());
            line += String.format("\t%s %s, %s, %s\n", op, storeTo, "$30", add2);
            return line;
        } else {
            return String.format("\t%s %s, %s, %s\n", op, storeTo, operands[1].toString(), operands[2].toString());
        }
    }

    public static String goto_op(IRInstruction instruction) {
        return "\t#goto\n" + String.format("\tj %s\n", instruction.operands[0].toString());
    }

    public static String branch(IRInstruction instruction, String command) {
        String line = "\t#branch\n";
        IROperand[] operands = instruction.operands;
        String add1 = "";
        String add2 = "";
        boolean op1 = checkImmediate(operands[1].toString());
        boolean op2 = checkImmediate(operands[2].toString());

        String label = registerValuePair.containsKey(operands[0].toString()) ? getRegisterVar(operands[0].toString()) : operands[0].toString();

        if (!op1 && !op2) {
            add1 = getRegisterVar(operands[1].toString());
            add2 = getRegisterVar(operands[2].toString());
            line += String.format("\t%s %s, %s, %s\n", command, add1, add2, label);
        } else if (op1 && !op2) {
            add2 = getRegisterVar(operands[2].toString());
            line += String.format("\tli %s, %s\n", "$30", operands[1].toString());
            line += String.format("\t%s %s, %s, %s\n", command, "$30", add2, label);
        } else if (!op1 && op2) {
            add1 = getRegisterVar(operands[1].toString());
            line += String.format("\tli %s, %s\n", "$30", operands[2].toString());
            line += String.format("\t%s %s, %s, %s\n", command, add1, "$30", label);
        } else {
            return "\t--------------ERROR--------------\n";
        }
        return line;
    }

    /**
     * TODO: Delete when sure is unneeded
     */
    public static String oldBranchCodePreserve(IRInstruction instruction, String command) {
        String line = "";
        IROperand[] operands = instruction.operands;
        String add1 = checkImmediate(operands[1].toString()) ? getRegisterVar(operands[1].toString()) : operands[1].toString();
        String add2 = getRegisterVar(operands[2].toString());
        String label = registerValuePair.containsKey(operands[0].toString()) ? getRegisterVar(operands[0].toString()) : operands[0].toString();

        line += String.format("\t%s %s, %s, %s\n", command, add1, add2, label);
        return line;
    }

    public static String return_op(IRInstruction instruction) {
        String line = "\t#return\n";
        if (checkImmediate(instruction.operands[0].toString())) {
            line += String.format("\tli %s, %s\n", "$v0",instruction.operands[0].toString());
            line += String.format("\tjr %s\n", "$ra");
            return line;
        }
        line += String.format("\tmove %s, %s\n", "$v0", getRegisterVar(instruction.operands[0].toString()));
        line += String.format("\tjr %s\n", "$ra");
        return line;
    }

    public static String call(IRInstruction instruction) {
        String line = "\t#call\n";
        IROperand[] operands = instruction.operands;
        switch (operands[0].toString().toLowerCase()) {
            case "puti":
                line += puti(instruction);
                break;
            case "putf":
                line += putf(instruction);
                break;
            case "putc":
                line += putc(instruction);
                break;
            default:
                line += preserveRegister(instruction);
                for (int i = operands.length - 1; i >= 1; i--) {
                    line += "\taddi $sp, $sp, -4\n";
                    if (checkImmediate(operands[i].toString())) {
                        line += String.format("\tli %s, %s\n", "$30", operands[i].toString());
                        line += line += "\tsw $30, ($sp)\n";
                        continue;
                    }
                    line += "\tsw " + registerValuePair.get(instruction.operands[i].toString()) + ", ($sp)\n";
                }
                line += String.format("\tjal %s\n", operands[0].toString());
                // int argRemov = ((operands.length - 1) * 4) < 0 ? 0 : ((operands.length - 1) * 4);
                // line += String.format("\taddi %s, %s, %d\n", "$sp", "$sp", argRemov);
                line += restoreRegisters(instruction);
                break;
        }
        return line;
    }

    public static String callr(IRInstruction instruction) {
        String line = "\t#callr\n";
        IROperand[] operands = instruction.operands;
        
        switch (operands[1].toString().toLowerCase()) {
            case "geti":
                line += geti(instruction);
                break;
            case "getf":
                line += getf(instruction);
                break;
            case "getc":
                line += getc(instruction);
                break;
            default:
                line += preserveRegister(instruction);
                for (int i = operands.length - 1; i >= 2; i--) {
                    line += "\taddi $sp, $sp, -4\n";
                    line += "\tsw " + registerValuePair.get(instruction.operands[i].toString()) + ", ($sp)\n";
                }
                line += String.format("\tjal %s\n", operands[1].toString());
                // int argRemov = ((operands.length - 2) * 4) < 0 ? 0 : ((operands.length - 2) * 4);
                // line += String.format("\taddi %s, %s, %d\n", "$sp", "$sp", argRemov);
                line += String.format("\tmove $30, $v0\n");
                line += restoreRegisters(instruction);
                line += String.format("\tmove %s, %s\n", getRegisterVar(operands[0].toString()), "$30");
                break;
        }
        return line;
    }

    public static String array_store(IRInstruction instruction) {
        String line = "\t#array_store\n";
        IROperand[] operands = instruction.operands;

        if (checkImmediate(operands[0].toString()) && !checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            int offset = Integer.parseInt(operands[2].toString()) * 4;
            line += String.format("\tli %s, %s\n", "$30", operands[0].toString());
            line += String.format("\tsw %s, %d(%s)\n", "$30", offset, getRegisterVar(operands[1].toString()));
        } else if (!checkImmediate(operands[0].toString()) && !checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            int offset = Integer.parseInt(operands[2].toString()) * 4;
            line += String.format("\tsw %s, %d(%s)\n", getRegisterVar(operands[0].toString()), offset, getRegisterVar(operands[1].toString()));
        } else if (!checkImmediate(operands[0].toString()) && !checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            line += String.format("\tli %s, %d\n", "$30", 4);
            line += String.format("\tmul %s, %s, %s\n", "$30", getRegisterVar(operands[2].toString()), "$30");
            line += String.format("\tadd %s, %s, %s\n", "$30", getRegisterVar(operands[1].toString()), "$30");
            line += String.format("\tsw %s, 0(%s)\n", getRegisterVar(operands[0].toString()), "$30");
        } else if (checkImmediate(operands[0].toString()) && !checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            line += String.format("\tli %s, %s\n", "$v1", operands[0].toString());
            line += String.format("\tli %s, %d\n", "$30", 4);
            line += String.format("\tmul %s, %s, %s\n", "$30", getRegisterVar(operands[2].toString()), "$30");
            line += String.format("\tadd %s, %s, %s\n", "$30", getRegisterVar(operands[1].toString()), "$30");
            line += String.format("\tsw %s, 0(%s)\n", "$v1", "$30");
        }
        return line;
    }

    public static String array_load(IRInstruction instruction) {
        String line = "\t#array_load\n";
        IROperand[] operands = instruction.operands;
        if (!checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            line += String.format("\tli %s, %d\n", "$30", 4);
            line += String.format("\tmul %s, %s, %s\n", "$30", "$30", getRegisterVar(operands[2].toString()));
            line += String.format("\tadd %s, %s, %s\n", "$30", getRegisterVar(operands[1].toString()), "$30");
            line += String.format("\tlw %s, 0(%s)\n", getRegisterVar(operands[0].toString()), "$30");
        } else if (!checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            int offset = Integer.parseInt(operands[2].toString()) * 4;
            line += String.format("\tlw %s, %d(%s)\n", getRegisterVar(operands[0].toString()), offset, getRegisterVar(operands[1].toString()));
        } else {
            line += "\tWHAT THE HELL\n";
        }
        return line;
    }

    // Method meant to condense array_store and array_load
    public static String array_op(IRInstruction instruction, String op) {
        String line = "\t#array_op\n";
        IROperand[] operands = instruction.operands;
        if (!checkImmediate(operands[1].toString()) && !checkImmediate(operands[2].toString())) {
            line += String.format("\tadd %s, %s, $s\n", "$30", getRegisterVar(operands[1].toString()), getRegisterVar(operands[2].toString()));
            line += String.format("\t%s %s, %s\n", op, getRegisterVar(operands[0].toString()), "$30");
        } else if (!checkImmediate(operands[1].toString()) && checkImmediate(operands[2].toString())) {
            line += String.format("\t%s %s, %s(%s)\n", op, getRegisterVar(operands[0].toString()), operands[2].toString(), getRegisterVar(operands[1].toString()));
        } else {
            line += "\tWHAT THE HELL\n";
        }
        return line;
    }

    public static String label(IRInstruction instruction) {
        return String.format("%s:\n", instruction.operands[0].toString());
    }

    public static String geti(IRInstruction instruction) {
        String line = "\t#geti\n";
        line += String.format("\tli %s, %d\n", "$v0", 5);
        line += String.format("\tsyscall\n");
        line += String.format("\tmove %s, %s\n", getRegisterVar(instruction.operands[0].toString()), "$v0");
        return line;
    }

    public static String getf(IRInstruction instruction) {
        String line = "\t#getf\n";
        line += String.format("\tli %s, %d\n", "$v0", 6);
        line += String.format("\tsyscall\n");
        line += String.format("\tmove %s, %s\n", getRegisterVar(instruction.operands[0].toString()), "$v0");
        return line;
    }

    // Note that geti and getc has the same implementation
    public static String getc(IRInstruction instruction) {
        String line = "\t#getc\n";
        line += String.format("\tli %s, %d\n", "$v0", 5);
        line += String.format("\tsyscall\n");
        line += String.format("\tmove %s, %s\n", getRegisterVar(instruction.operands[0].toString()), "$v0");
        return line;
    }

    public static String puti(IRInstruction instruction) {
        String line = "\t#puti\n";
        line += String.format("\tmove %s, %s\n", "$30", "$a0");
        if (checkImmediate(instruction.operands[1].toString())) {
            line += String.format("\tmove %s, %s\n", "$a0", instruction.operands[1].toString());
        } else {
            line += String.format("\tmove %s, %s\n", "$a0", getRegisterVar(instruction.operands[1].toString()));
        }
        line += String.format("\tli %s, %d\n", "$v0", 1);
        line += String.format("\tsyscall\n");
        line += String.format("\tmove %s, %s\n", "$a0", "$30");
        return line;
    }

    public static String putf(IRInstruction instruction) {
        String line = "\t#putf\n";
        line += String.format("\tmove %s, %s\n", "$30", "$f12");
        line += String.format("\tmove %s, %s\n", "$f12", getRegisterVar(instruction.operands[1].toString()));
        line += String.format("\tli %s, %d\n", "$v0", 2);
        line += String.format("\tsyscall\n");
        line += String.format("\tmove %s, %s\n", "$f12", "$30");
        return line;
    }

    public static String putc(IRInstruction instruction) {
        String line = "\t#putc\n";
        line += String.format("\tmove %s, %s\n", "$30", "$a0");
        line += String.format("\tli %s, %d\n", "$v0", 11);
        if (checkImmediate(instruction.operands[1].toString())) {
            line += String.format("\tli %s, %s\n", "$a0", instruction.operands[1].toString());
        } else {
            line += String.format("\tli %s, %s\n", "$a0", getRegisterVar(instruction.operands[1].toString()));
        }
        line += String.format("\tsyscall\n");
        line += String.format("\tmove %s, %s\n", "$a0", "$30");
        return line;
    }

    public static boolean checkImmediate(String operand) {
        for (int i = 0; i < operand.length(); i++) {
            char character = operand.charAt(i);
            if (character == '-' || character == '+' || character == '.') {
                continue;
            }
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }

    public static String preserveRegister(IRInstruction instruction) {
        String convention = "";
        if (virtualRegConvention) {
            for (String register : usedRegisters.get(currFunc)) {
                convention += "\taddi $sp, $sp, -4\n";
                convention += "\tsw " + register + ", ($sp)\n";
            }
        } else {
            // PERFORM REGULAR REGISTER STORAGE FOR EVERY SINGLE REGISTER AVAILABLE FOR USE
            for (String register : preserveRegisters) {
                convention += "\taddi $sp, $sp, -4\n";
                convention += "\tsw " + register + ", ($sp)\n";
            }
        }
        

        return convention;
    }

    public static String restoreRegisters(IRInstruction instruction) {
        String convention = "";
        if (virtualRegConvention) {
            for (int i = usedRegisters.get(currFunc).size() - 1; i >= 0; i--) {
                String register = usedRegisters.get(currFunc).get(i);
                convention += "\tlw " + register + ", ($sp)\n";
                convention += "\taddi $sp, $sp, 4\n";
            }
        } else {
            // PERFORM REGULAR REGISTER RESTORATION FOR EVERY SINGLE REGISTER
            for (int i = preserveRegisters.length - 1; i >= 0; i--) {
                String register = preserveRegisters[i];
                convention += "\tlw " + register + ", ($sp)\n";
                convention += "\taddi $sp, $sp, 4\n";
            }
        }
        return convention;
    }

    public static String extractArguments(List<IRVariableOperand> operands) {
        String line = "";
        for (int i = 0; i < operands.size() && i < 4; i++) {
            line += String.format("\tlw %s, (%s)\n", "$a" + i, "$sp");
            registerValuePair.put(operands.get(i).toString(), "$a" + i);
            usedRegisters.get(currFunc).add("$a" + i);
            line += String.format("\taddi $sp, $sp, 4\n");
        }

        for (int i = 4; i < operands.size(); i++) {
            line += String.format("\tlw %s, (%s)\n", getRegisterVar(operands.get(i).toString()), "$sp");
            line += String.format("\taddi $sp, $sp, 4\n");
        }
        return line;
    }

    /**
     * Returns the register associated with a variable
     * If no such register exists, create one and return it
     * @param var The varaible from Tiger IR
     */
    public static String getRegisterVar(String var) {
        if (registerValuePair.containsKey(var)) {
            return registerValuePair.get(var);
        }

        for (int i = 0; true; i++) {
            if (!registerValuePair.containsValue("$z" + i)) {
                registerValuePair.put(var, "$z" + i);
                usedRegisters.get(currFunc).add("$z" + i);
                return "$z" + i;
            }
        }
    }

    public static void registerSpilling(String funcName, String spillRegister) {
        if (!conversionComplete) {
            throw new IllegalStateException("The code has not been translated. Call handleInstruction first");
        }
        List<String> registerList = spilledRegisters.get(funcName);
        if (!registerList.contains(spillRegister)) {
            registerList.add(spillRegister);
            
        }
    }
}