import ir.*;

import java.io.PrintStream;
import java.io.*;
import java.util.*;


public class Compiler {
    public static void main(String[] args) throws Exception {
        //Parse the IR file
        IRReader irReader = new IRReader();
        boolean en_cp;
        String IRFile;
        String Optimized_IRFile;
        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("-c")) {
            en_cp = true;
            IRFile = args[1];
            Optimized_IRFile = args[2];
        } else {
            en_cp = false;
            IRFile = args[0];
            Optimized_IRFile = args[1];
        }
        IRProgram program = irReader.parseIRFile(IRFile);
        //Print the IR to another file
        IRPrinter filePrinter = new IRPrinter(new PrintStream(Optimized_IRFile));

        
        
        IRCFG irCfg = new IRCFG(program);
        //Create an IR printer that prints to stdout for debugging

        do {
            DeadCode.change = false;
            DeadCode.reachingDefRewrite(irCfg);
        } while (DeadCode.change);

        if (en_cp) {
            //SVN and CP optimizations
            IRRedundancyElimination re = new IRRedundancyElimination(irCfg);
            IRConstantPropagation cp = new IRConstantPropagation();
            for (IRNode root:irCfg.rootList) {
                re.SVNHelper(root);
            }
            for (IRNode root:irCfg.rootList) {
                re.SVNHelperEEB(root, irCfg);
            }
            for (IRNode root:irCfg.rootList) {
                cp.cpHelper(root,irCfg);
            }

            for (IRFunction func : program.functions) {
                for (IRInstruction ins : func.instructions) {
                    System.out.println(ins);
                }
            }

            filePrinter.printProgram(program);
            IRProgram program2 = irReader.parseIRFile(Optimized_IRFile);
            filePrinter = new IRPrinter(new PrintStream(Optimized_IRFile));
            IRCFG irCfg2 = new IRCFG(program2);
            do {
                DeadCode.change = false;
                DeadCode.reachingDefRewrite(irCfg2);
            } while (DeadCode.change);

            filePrinter.printProgram(program2);
        } else {
            filePrinter.printProgram(program);
        }

        MipsTranslator.fake_main(args);
}

    public static void displayCFG(IRCFG cfg) {
        System.out.println(cfg.blocks.size());
        for (ArrayList<ArrayList<IRInstruction>> functions : cfg.blocks) {
            for (ArrayList<IRInstruction> instructions : functions) {
                for (IRInstruction instruction : instructions) {
                    System.out.println(instruction);
                }
                System.out.println("---End of Instruction---");
            }
            System.out.println("---End of Function---");
        }
        System.out.println("---End of Block---");
    }

    public static List<IRFunction> convertBlockStructure(ArrayList<ArrayList<ArrayList<IRInstruction>>> block) {
        List<IRFunction> functions = new ArrayList<>();

        return functions;
    }

    public static void hardCodedCFG(IRCFG cfg) {
        System.out.println(cfg.blocks);
    }


    public static void displayIRNodes(IRCFG cfg) {
        for (IRNode root : cfg.rootList) {
            displayIRNodeHelper(root, new HashSet<>());
            System.out.println("-----------End Function------------");
        }
    }

    private static void displayIRNodeHelper(IRNode curr, HashSet<IRNode> visited) {
        if (curr != null && !visited.contains(curr)) {
            visited.add(curr);
            for (IRInstruction instruction : curr.instructions) {
                System.out.println(instruction);
            }

            System.out.println("--------End Node--------");
            
            for (IRNode child : curr.edgeList) {
                displayIRNodeHelper(child, visited);
            }
        }
    }

    private static void displayProgram(IRProgram program) {
        List<IRFunction> functions = program.functions;

        for (IRFunction function : functions) {
            for (IRInstruction instruction : function.instructions) {
                System.out.println(instruction);
            }
        }
    }
    
}
