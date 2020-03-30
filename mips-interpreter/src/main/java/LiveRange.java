import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import ir.*;

public class LiveRange {

    private class Vector {
        public int liveStart;
        public int liveEnd;
    
        public Vector (int liveStart, int liveEnd) {
            this.liveStart = liveStart;
            this.liveEnd = liveEnd;
        }
    }

    public static void live_range(String program) {
        // decompress the input to it's respective function calls
        HashMap<String, ArrayList<String>> functions = new HashMap<>();
        Scanner sc = new Scanner(program);
        String funcName = null;


        // note for me, this part might error if the funcName is not properly formatted when passed in
        while (sc.hasNext()) {
            String nextLine = sc.nextLine();
            if (nextLine.contains(":")) {
                if (nextLine.split(":")[0].contains("(")) {
                    // currently deciding if line contains : and if before colon
                    // has parenthesis represents a function name
                    funcName = nextLine.split(":")[0].split(")")[0];
                    if (functions.get(funcName) == null) {
                        functions.put(funcName, new ArrayList<String>());
                        functions.get(funcName).add(nextLine.split(":")[1]);
                    } else {
                        functions.get(funcName).add(nextLine.split(":")[1]);
                    }
                } else {
                    if (functions.get(funcName) == null) {
                        // by current design should never happen but just in case
                        functions.put(funcName, new ArrayList<String>());
                        functions.get(funcName).add(nextLine.split(":")[1]);
                    } else {
                        functions.get(funcName).add(nextLine.split(":")[1]);
                    }
                }
                
            }
        }
        sc.close();

        HashMap<String, HashMap<String, Vector>> functionLiveRanges = new HashMap<>();  // should contain the live ranges for every function
        for (String function : functions.keySet()) {
            functionLiveRanges.put(function, calculate_live((String[])functions.get(function).toArray()));
        }

    }

    public static HashMap<String, Vector> calculate_live(String[] program) {
        HashMap<String, Vector> liveRanges = new HashMap<String, Vector>();

        for (int i = 0; i < program.length; i++) {
            String register = retrieveRegister(program[i]);
            if (!liveRanges.containsKey(register)) {
                liveRanges.put(register, new LiveRange().new Vector(i, i));
                for (int j = i + 1; j < program.length; j++) {
                    String temp_register = retrieveRegister(program[i]);
                    String[] operands = retrieveOperands(program[i]);

                    if (temp_register.equals(register)) {
                        liveRanges.get(register).liveEnd = j;
                        continue;
                    }
                    for (String operand : operands) {
                        if (operand.equals(register)) {
                            liveRanges.get(register).liveEnd = j;
                            break;
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
        sc.close();
        return register;
    }

    public static String[] retrieveOperands(String instruction) {
        ArrayList<String> list = new ArrayList<>();
        Scanner sc = new Scanner(instruction);
        sc.useDelimiter(" | ,");
        sc.next();
        sc.next();
        while (sc.hasNext()) {
            list.add(sc.next());
        }
        sc.close();
        return (String[]) list.toArray();
    }

    public static void main(String[] args) {
        
    }
}