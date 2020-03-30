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

    public static void main(String[] args) {
        String sampleProgram = "quicksort():            bge $lo, $hi, end\n" +
        "add $mid, $lo, $hi\n" +
        "div $mid, $mid, $t1\n" +
        "add $temp, $A, $mid\n" +
        "lw $pivot, 0($temp)\n" +
        "subi $i, $lo, 1\n" +
        "addi $j, $hi, 1\n" +
        "loop0:  loop1:          addi $i, $i, 1\n" +
        "add $temp, $A, $i\n" +
        "lw $x, 0($temp)\n" +
        "move $ti, $x\n" +
        "blt $ti, $pivot, loop1\n" +
        "loop2:          subi $j, $j, 1\n" +
        "add $temp, $A, $j\n" +
        "lw $x, 0($temp)\n" +
        "move $tj, $x\n" +
        "bgt $tj, $pivot, loop2\n" +
        "bge $i, $j, exit0\n" +
        "add $temp, A, j\n" +
        "sw $ti, 0($temp)\n" +
        "add $temp, A, i\n" +
        "sw $tj, 0($temp)\n" +
        "jump $loop0\n" +
        "exit0:          addi $29, $29, -96\n" +
        "sw $t0, 56($29)\n" +
        "sw $t1, 60($29)\n" +
        "sw $t2, 64($29)\n" +
        "sw $t3, 68($29)\n" +
        "sw $t4, 72($29)\n" +
        "sw $t5, 76($29)\n" +
        "sw $t6, 80($29)\n" +
        "sw $t7, 84($29)\n" +
        "sw $t8, 88($29)\n" +
        "sw $t9, 92($29)\n" +
        "sw $s0, 16($29)\n" +
        "sw $s1, 20($29)\n" +
        "sw $s2, 24($29)\n" +
        "sw $s3, 28($29)\n" +
        "sw $s4, 32($29)\n" +
        "sw $s5, 36($29)\n" +
        "sw $s6, 40($29)\n" +
        "sw $s7, 44($29)\n" +
        "sw $s8, 48($29)\n" +
        "sw $31, 52($29)\n" +
        "sw $A, 4($29)\n" +
        "sw $lo, 8($29)\n" +
        "sw $j, 12($29)\n" +
        "jal quicksort\n" +
        "lw $31, 52($29)\n" +
        "lw $t0, 56($29)\n" +
        "lw $t1, 60($29)\n" +
        "lw $t2, 64($29)\n" +
        "lw $t3, 68($29)\n" +
        "lw $t4, 72($29)\n" +
        "lw $t5, 76($29)\n" +
        "lw $t6, 80($29)\n" +
        "lw $t7, 84($29)\n" +
        "lw $t8, 88($29)\n" +
        "lw $t9, 92($29)\n" +
        "addi $29, $29, 96\n" +
        "addi $j, $j, 1\n" +
        "addi $29, $29, -96\n" +
        "sw $t0, 56($29)\n" +
        "sw $t1, 60($29)\n" +
        "sw $t2, 64($29)\n" +
        "sw $t3, 68($29)\n" +
        "sw $t4, 72($29)\n" +
        "sw $t5, 76($29)\n" +
        "sw $t6, 80($29)\n" +
        "sw $t7, 84($29)\n" +
        "sw $t8, 88($29)\n" +
        "sw $t9, 92($29)\n" +
        "sw $s0, 16($29)\n" +
        "sw $s1, 20($29)\n" +
        "sw $s2, 24($29)\n" +
        "sw $s3, 28($29)\n" +
        "sw $s4, 32($29)\n" +
        "sw $s5, 36($29)\n" +
        "sw $s6, 40($29)\n" +
        "sw $s7, 44($29)\n" +
        "sw $s8, 48($29)\n" +
        "sw $31, 52($29)\n" +
        "sw $A, 4($29)\n" +
        "sw $j, 8($29)\n" +
        "sw $hi, 12($29)\n" +
        "jal quicksort\n" +
        "lw $31, 52($29)\n" +
        "lw $t0, 56($29)\n" +
        "lw $t1, 60($29)\n" +
        "lw $t2, 64($29)\n" +
        "lw $t3, 68($29)\n" +
        "lw $t4, 72($29)\n" +
        "lw $t5, 76($29)\n" +
        "lw $t6, 80($29)\n" +
        "lw $t7, 84($29)\n" +
        "lw $t8, 88($29)\n" +
        "lw $t9, 92($29)\n" +
        "addi $29, $29, 96\n" +
        "end:\n" +
        "\n" +
        "main(): addi $29, $29, -400\n" +
        "move $A, $29 # this is a local array\n" +
        "li $v0, 5\n" +
        "syscall\n" +
        "move $n, $v0\n" +
        "bgt $n, $100, return\n" +
        "subi $n, $n, 1\n" +
        "li $i, 0\n" +
        "loop0:          bgt $i, $n, exit0\n" +
        "li $v0, 5\n" +
        "syscall\n" +
        "move $t, $v0\n" +
        "add $temp, A, i\n" +
        "sw $t, 0($temp)\n" +
        "addi $i, $i, 1\n" +
        "jump $loop0\n" +
        "exit0:          addi $29, $29, -96\n" +
        "sw $t0, 56($29)\n" +
        "sw $t1, 60($29)\n" +
        "sw $t2, 64($29)\n" +
        "sw $t3, 68($29)\n" +
        "sw $t4, 72($29)\n" +
        "sw $t5, 76($29)\n" +
        "sw $t6, 80($29)\n" +
        "sw $t7, 84($29)\n" +
        "sw $t8, 88($29)\n" +
        "sw $t9, 92($29)\n" +
        "sw $s0, 16($29)\n" +
        "sw $s1, 20($29)\n" +
        "sw $s2, 24($29)\n" +
        "sw $s3, 28($29)\n" +
        "sw $s4, 32($29)\n" +
        "sw $s5, 36($29)\n" +
        "sw $s6, 40($29)\n" +
        "sw $s7, 44($29)\n" +
        "sw $s8, 48($29)\n" +
        "sw $31, 52($29)\n" +
        "sw $A, 4($29)\n" +
        "li $temp, 0\n" +
        "sw $temp, 8($29)\n" +
        "sw $n, 12($29)\n" +
        "jal quicksort\n" +
        "lw $31, 52($29)\n" +
        "lw $t0, 56($29)\n" +
        "lw $t1, 60($29)\n" +
        "lw $t2, 64($29)\n" +
        "lw $t3, 68($29)\n" +
        "lw $t4, 72($29)\n" +
        "lw $t5, 76($29)\n" +
        "lw $t6, 80($29)\n" +
        "lw $t7, 84($29)\n" +
        "lw $t8, 88($29)\n" +
        "lw $t9, 92($29)\n" +
        "addi $29, $29, 96\n" +
        "li $i, 0\n" +
        "loop1:          bgt $i, $n, exit1\n" +
        "add $temp, $A, $i\n" +
        "lw $t, 0($temp)\n" +
        "li $a0, $t\n" +
        "li $v0, 1\n" +
        "syscall\n" +
        "li $a0, $10\n" +
        "li $v0, 11\n" +
        "syscall\n" +
        "addi $i, $i, 1\n" +
        "jump $loop1\n" +
        "exit1:          jr $31";
        
        live_range(sampleProgram);
        System.out.println("The program has finished runnning");
    }
}