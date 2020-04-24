import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import java.util.Arrays;
import mips.RegisterNode;

public class LiveRange {

    public static HashMap<String, String> functionMipsCode;

    private static String[] mipsRegisters = new String[] {
        "$0", "$1", "$2", "$3", "$4", "$5", "$6", "$7", "$8", "$9", "$10",
        "$11", "$12", "$13", "$14", "$15", "$16", "$17", "$18", "$19", "$20",
        "$21", "$22", "$23", "$24", "$25", "$26", "$27", "$28", "$29", "$30",
        "$31", "$32", 
        "$zero", "$at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", "$t0", "$t1",
        "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", 
        "$s4", "$s5", "$s6", "$s7", "$t8", "$t9", "$k0", "$k1", "$gp", "$sp",
        "$fp", "$ra"};

    private class Vector {
        public int liveStart;
        public int liveEnd;
    
        public Vector (int liveStart, int liveEnd) {
            this.liveStart = liveStart;
            this.liveEnd = liveEnd;
        }
    }

    private static boolean DEBUG = false;

    public static HashMap<String, List<RegisterNode>> generateDependencies(String program) {
        // functionMipsCode = new HashMap<>();
        functionMipsCode = genFunctionMipsCode(program);
        HashMap<String, HashMap<String, Vector>> functionLiveRanges = live_range(program);  // contains live ranges of every function
        HashMap<String, List<RegisterNode>> functionRegisterNode = new HashMap<>(); // final return that contaians every RegisterNode separated by function

        for (String function : functionLiveRanges.keySet()) {
            HashMap<String, Vector> indiv_live_ranges = functionLiveRanges.get(function);   // contains live ranges for a specific function
            // functionRegisterNode.put(function, new ArrayList<RegisterNode>());
            HashMap<String, RegisterNode> tempAllNodes = new HashMap<>();   // temporary set that contains all RegisterNode for current function
            for (String register : indiv_live_ranges.keySet()) {
                // creates a node for every register
                tempAllNodes.put(register, new RegisterNode(register));
            }

            for (String register : tempAllNodes.keySet()) {
                for (String otherRegister : tempAllNodes.keySet()) {
                    if (otherRegister.equals(register)) {
                        continue;
                    }
                    Vector current = indiv_live_ranges.get(register);
                    Vector other = indiv_live_ranges.get(otherRegister);

                    // check if the two ranges overlap
                    if ((current.liveStart >= other.liveStart && current.liveStart <= other.liveEnd) || (current.liveEnd >= other.liveStart && current.liveEnd <= other.liveEnd)) {
                        RegisterNode currNode = tempAllNodes.get(register);
                        RegisterNode otherNode = tempAllNodes.get(otherRegister);
                        if (!currNode.adjacentNodes.contains(otherNode)) {
                            currNode.adjacentNodes.add(otherNode);
                        }
                        if (!otherNode.adjacentNodes.contains(currNode)) {
                            otherNode.adjacentNodes.add(currNode);
                        }
                    }
                }
            }
            functionRegisterNode.put(function, new ArrayList<RegisterNode>(tempAllNodes.values()));
        }

        return functionRegisterNode;
    }

    public static HashMap<String, HashMap<String, Vector>> live_range(String program) {
        // decompress the input to it's respective function calls
        HashMap<String, ArrayList<String>> functions = new HashMap<>();
        Scanner sc = new Scanner(program);
        String funcName = "";

        // note for me, this part might error if the funcName is not properly formatted when passed in
        while (sc.hasNext()) {
            String nextLine = sc.nextLine();
            if (nextLine.contains(".") || nextLine.trim().length() == 0) {
                continue;
            }
            if (nextLine.contains(":")) {
                if (nextLine.split(":")[0].contains("(")) {
                    // currently deciding if line contains : and if before colon
                    // has parenthesis represents a function name
                    funcName = nextLine.split(":")[0].split("\\(")[0].trim();
                    if (functions.get(funcName) == null) {
                        functions.put(funcName, new ArrayList<String>());
                    }
                }
                String[] splitted = nextLine.split(":");
                functions.get(funcName).add(splitted.length > 1 ? splitted[1].trim() : splitted[0].trim());

            } else {
                functions.get(funcName).add(nextLine.trim());
            }
        }
        sc.close();

        HashMap<String, HashMap<String, Vector>> functionLiveRanges = new HashMap<>();  // should contain the live ranges for every function
        for (String function : functions.keySet()) {
            // functionMipsCode.put(function, concatenateProgram(functions.get(function)));
            functionLiveRanges.put(function, calculate_live(listToStringArray(functions.get(function))));
        }

        for (String function : functionLiveRanges.keySet()) {
            HashMap<String, Vector> func = functionLiveRanges.get(function);
            if (DEBUG) {
                System.out.printf("\nFunction is %s\n-------------------\n", function);
            }
            for (String register : func.keySet()) {
                Vector vec = func.get(register);
                if (DEBUG) {
                    System.out.printf("Register %s has a live range from %d to %d\n", register, vec.liveStart, vec.liveEnd);
                }
            }
        }

        return functionLiveRanges;
    }

    public static HashMap<String, Vector> calculate_live(String[] program) {
        HashMap<String, Vector> liveRanges = new HashMap<String, Vector>();
        if (DEBUG) {
            System.out.println("Program in live range is ---------------------------------------------\n" + program[0]);
        }
        // Purpose of this variable is to keep track of each label and where they are
        HashMap<String, Integer> label_loc = new HashMap<>();
        
        for (int i = 0; i < program.length; i++) {
            if (!program[i].contains(" ") && !program[i].contains("#") && !program[i].contains("syscall")) {
                label_loc.put(program[i], i);
            }
            if (new StringTokenizer(program[i], " |, ").countTokens() < 3) {
                continue;
            }
            String register = retrieveRegister(program[i]);
            if (!isCheckedRegister(register)) {
                continue;   // ignores registers that matches this criteria
                            // replace conditional with a custom function if ignoring more than just certain registers
                            // TODO: CHECK REGISTERS TO IGNORE
            }
            if (!liveRanges.containsKey(register)) {
                liveRanges.put(register, new LiveRange().new Vector(i, i));
                for (int j = i + 1; j < program.length; j++) {
                    for (String label : label_loc.keySet()) {
                        if (program[j].contains(label)) {
                            if (liveRanges.get(register).liveStart > label_loc.get(label)) {
                                liveRanges.get(register).liveStart = label_loc.get(label);
                            }
                        }
                    }
                    if (new StringTokenizer(program[j], " |, ").countTokens() < 3) {
                        continue;
                    }

                    String temp_register = retrieveRegister(program[j]);
                    String[] operands = retrieveOperands(program[j]);

                    if (temp_register.equals(register)) {
                        liveRanges.get(register).liveEnd = j;
                        continue;
                    }
                    for (String operand : operands) {
                        if (operand.equals(register)) {
                            liveRanges.get(register).liveEnd = j;
                            continue;
                        }
                    }
                }
            }
        }        

        return liveRanges;
    }

    public static String retrieveRegister(String instruction) {
        Scanner sc = new Scanner(instruction);
        sc.useDelimiter(" | ,");
        sc.next();  // this is the command
        String register = sc.next();
        register = register.replaceAll(",", "");
        sc.close();

        if (DEBUG) {
            System.out.println("REGISTER RETRIEVED IS " + register);
        }
        return register;
    }

    public static String[] retrieveOperands(String instruction) {
        ArrayList<String> list = new ArrayList<>();
        Scanner sc = new Scanner(instruction);
        sc.useDelimiter(" | ,");
        sc.next();
        sc.next();
        while (sc.hasNext()) {
            list.add(sc.next().trim().replaceAll(",", ""));
        }
        sc.close();

        if (DEBUG) {
            System.out.println("OPERANDS RETRIEVED IS " + list.toString());
        }
        return listToStringArray(list);
    }

    private static String[] listToStringArray(List<String> list) {
        String[] strArray = new String[list.size()];
        System.arraycopy(list.toArray(), 0, strArray, 0, strArray.length);
        return strArray;
    }

    private static boolean isCheckedRegister(String register) {
        return !Arrays.asList(mipsRegisters).contains(register);
    }

    private static String concatenateProgram(List<String> program) {
        String returnVal = "";
        
        for (String line : program) {
            if (line.contains(":")) {
                returnVal += line + "\n";
            } else {
                returnVal += "\t" + line + "\n";
            }
        }

        return returnVal;
    }

    private static HashMap<String, String> genFunctionMipsCode(String program) {
        Scanner sc = new Scanner(program);
        List<String> lines = new ArrayList<>();
        HashMap<String, List<String>> functions = new HashMap<>();

        String funcName = null;
        while (sc.hasNextLine()) {
            boolean newFunc = false;
            String line = sc.nextLine();
            if (line.contains(":") && line.contains("(") && line.contains(")")) {
                //found a function
                newFunc = true;
                funcName = line.split(":")[0].split("\\(")[0].trim();
            }
            if (funcName != null && functions.get(funcName) == null) {
                functions.put(funcName, new ArrayList<String>());
            }
            if (newFunc) {
                String[] splitted = line.split(":");
                functions.get(funcName).add(splitted.length > 1 ? splitted[1].trim() : splitted[0].trim());
            } else if (funcName != null) {
                functions.get(funcName).add(line.trim());
            }
        }

        HashMap<String, String> returnVal = new HashMap<>();

        for (String key : functions.keySet()) {
            returnVal.put(key, concatenateProgram(functions.get(key)));
        }

        return returnVal;
    }
}