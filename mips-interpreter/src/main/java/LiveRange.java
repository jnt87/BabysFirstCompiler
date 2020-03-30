import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Arrays;
// import ir.*;

public class LiveRange {
    private class Vector {
        public int liveStart;
        public int liveEnd;
    
        public Vector (int liveStart, int liveEnd) {
            this.liveStart = liveStart;
            this.liveEnd = liveEnd;
        }
    }

    private static String[] registersToIgnore = {"$29"};

    private static boolean DEBUG = false;

    

    public static void live_range(String program) {
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
            functionLiveRanges.put(function, calculate_live(listToStringArray(functions.get(function))));
        }

        for (String function : functionLiveRanges.keySet()) {
            HashMap<String, Vector> func = functionLiveRanges.get(function);
            System.out.printf("\nFunction is %s\n-------------------\n", function);
            for (String register : func.keySet()) {
                Vector vec = func.get(register);
                System.out.printf("Register %s has a live range from %d to %d\n", register, vec.liveStart, vec.liveEnd);
            }
        }
    }

    public static HashMap<String, Vector> calculate_live(String[] program) {
        HashMap<String, Vector> liveRanges = new HashMap<String, Vector>();

        for (int i = 0; i < program.length; i++) {
            if (new StringTokenizer(program[i], " |, ").countTokens() < 3) {
                continue;
            }
            String register = retrieveRegister(program[i]);
            if (Arrays.asList(registersToIgnore).contains(register)) {
                continue;   // ignores registers that matches this criteria
                            // replace conditional with a custom function if ignoring more than just certain registers
            }
            if (!liveRanges.containsKey(register)) {
                liveRanges.put(register, new LiveRange().new Vector(i, i));
                for (int j = i + 1; j < program.length; j++) {
                    if (new StringTokenizer(program[i], " |, ").countTokens() < 3) {
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
        //System.out.println("ARRAY CONVERTED IS " + Arrays.asList(strArray));
        return strArray;
    }
}