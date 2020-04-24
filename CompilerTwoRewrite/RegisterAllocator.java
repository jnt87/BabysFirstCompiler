import java.util.*;
import ir.*;
import mips.RegisterNode;


public class RegisterAllocator {

    public static HashMap<IRFunction, HashMap<String, String>> virtualActualMap;

    public static void test_allocation(HashMap<IRFunction, List<String>> info) {
        virtualActualMap = new HashMap<>();
        for (IRFunction func : info.keySet()) {
            ArrayList<String> availableReg = new ArrayList<>();
            virtualActualMap.put(func, new HashMap<>());
            availableReg.addAll(Arrays.asList(InstructionSelection.usableRegisters));
            for (String virtualReg : InstructionSelection.usedRegisters.get(func)) {
                if (availableReg.contains(virtualReg)) {
                    availableReg.remove(virtualReg);
                    System.out.println("skipped " + virtualReg);
                    continue;
                } else if (Arrays.asList(InstructionSelection.reserved).contains(virtualReg)) {
                    System.out.println("skipped reserve " + virtualReg);
                    continue;
                }
                virtualActualMap.get(func).put(virtualReg, availableReg.remove(0));
            }

            

            System.out.println("REPLACING");
            System.out.println("Pairing for func " + func.name + " " + virtualActualMap.get(func));
            for (int i = 0; i < info.get(func).size(); i++) {
                HashMap<String, String> pair = virtualActualMap.get(func);
                for (String virtual : pair.keySet()) {
                    if (pair.get(virtual) == null) {
                        continue;
                    } else if (Arrays.asList(InstructionSelection.reserved).contains(pair.get(virtual))) {
                        System.out.println("skip change reserve " + virtual);
                        continue;
                    }
                    info.get(func).set(i, info.get(func).get(i).replaceAll("\\" + virtual, "\\" + pair.get(virtual)));
                }
            }
        }
    }

    public static List<String> spillAlgorithm(List<String> info, List<String> spillList, HashMap<String, String> pairing, List<RegisterNode> dependencies) {
        System.out.println("the spill list is " + spillList);
        System.out.println("The allocation choice is " + pairing);
        List<String> info_result = new ArrayList<>();
        if (spillList.size() != 0) {
            String init = "";
            int numSpill = spillList.size() * 4;
            init += String.format("\tli %s, %d\n", "$v0", 9);
            init += String.format("\tli %s, %d\n", "$a0", numSpill);
            init += String.format("\tsyscall\n");
            init += String.format("\tmove %s, %s\n", "$gp", "$v0");
            info.add(1, init);

            info = reconstituteCode(info);
            for (int var = 0; var < info.size(); var++) {
                String line = info.get(var);
                if (line.trim().equals("")) {
                    continue;
                }
                String[] decompose = line.trim().split(", | ");
                for (int i = 0; i < decompose.length; i++) {
                    if (decompose[i].contains("(")) {
                        decompose[i] = decompose[i].substring(decompose[i].indexOf("(") + 1, decompose[i].indexOf(")"));
                    }
                }

                int[] found = checkHowManySpill(decompose, spillList);
                int count = numOnes(found);
                switch (count) {
                    case 0:
                        info_result.add(info.get(var));
                        break;
                    case 1:
                    {
                        int i = 0;
                        for (i = 0; i < found.length; i++) {
                            if (found[i] == 1) {
                                break;
                            }
                        }
                        String spillReg = decompose[i];
                        if (isMove(decompose[0], i)) {
                            if (i == 1) {
                                // spill the target
                                System.out.println("Case detected");
                                String newLine = String.format("\tsw %s, %d(%s)\n", decompose[2], spillList.indexOf(spillReg) * 4, "$gp");
                                
                                // info.set(var, newLine);
                                System.out.println(newLine);
                                info_result.add(newLine);
                            } else {
                                System.out.println("The instruction2 occur is " + info.get(var));
                                
                                // spill on the source, same code as for a use
                                // info.set(var, info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                                String newLine = String.format("\tlw %s, %d(%s)\n", decompose[1], spillList.indexOf(spillReg) * 4, "$gp");
                                // info.add(var, newLine);
                                info_result.add(newLine);
                                info.add(info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                                System.out.println(newLine);
                                System.out.println(info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                            }
                        } else if (isDef(decompose[0], i)) {
                            // info.set(var, info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                            String newLine = String.format("\tsw %s, %d(%s)\n", "$t8", spillList.indexOf(spillReg) * 4, "$gp");
                            // info.add(var + 1, newLine);
                            info_result.add(info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                            info_result.add(newLine);
                        } else if (isUse(decompose[0], i)) {
                            // info.set(var, info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                            String newLine = String.format("\tlw %s, %d(%s)\n", "$t8", spillList.indexOf(spillReg) * 4, "$gp");
                            // info.add(var, newLine);
                            info_result.add(newLine);
                            info_result.add(info.get(var).replaceAll("\\" + spillReg, "\\$t8"));
                        }
                        break;
                    }
                    case 2:
                    {
                        int loc1 = -1;
                        int loc2 = -1;
                        for (int i = 0; i < found.length; i++) {
                            if (found[i] == 1) {
                                if (loc1 == -1) {
                                    loc1 = i;
                                } else {
                                    loc2 = i;
                                    break;
                                }
                            }
                        }
                        String spillReg1 = decompose[loc1];
                        String spillReg2 = decompose[loc2];
                        System.out.println("Decompose is " + Arrays.asList(decompose));
                        if (isMove(decompose[0], 0)) {
                            String newLine = String.format("\tlw %s, %d(%s)\n", "$t9", spillList.indexOf(spillReg2) * 4, "$gp");
                            newLine += String.format("\tsw %s, %d(%s)\n", "$t9", spillList.indexOf(spillReg1) * 4, "$gp");
                            // info.set(var, newLine);
                            info_result.add(newLine);
                            System.out.println(newLine);
                        } else if (loc1 != 1 || (loc1 == 1 && !isDef(decompose[0], loc1))) {
                            // case both is use
                            // info.set(var, info.get(var).replaceAll("\\" + spillReg1, "\\$t8"));
                            // info.set(var, info.get(var).replaceAll("\\" + spillReg2, "\\$t9"));
                            String newLine = String.format("\tlw %s, %d(%s)\n", "$t8", spillList.indexOf(spillReg1) * 4, "$gp");
                            // info.add(var, newLine);
                            newLine += String.format("\tlw %s, %d(%s)\n", "$t9", spillList.indexOf(spillReg2) * 4, "$gp");
                            // info.add(var, newLine);

                            info_result.add(newLine);
                            info_result.add(info.get(var).replaceAll("\\" + spillReg1, "\\$t8").replaceAll("\\" + spillReg2, "\\$t9"));
                        } else {
                            // case first one is a def
                            // info.set(var, info.get(var).replaceAll("\\" + spillReg2, "\\$t9").replaceAll("\\" + spillReg1, "\\$t8"));
                            String newLine = String.format("\tlw %s, %d(%s)\n", "$t9", spillList.indexOf(spillReg2) * 4, "$gp");
                            // info.add(var, newLine);

                            String newLine2 = String.format("\tsw %s, %d(%s)\n", "$t9", spillList.indexOf(spillReg1) * 4, "$gp");
                            // info.add(var + 2, newLine2);

                            info_result.add(newLine);
                            info_result.add(info.get(var).replaceAll("\\" + spillReg2, "\\$t9").replaceAll("\\" + spillReg1, "\\$t8"));
                            info_result.add(newLine2);
                        }
                        break;
                    }
                    case 3:
                    {
                        // the only scenario where this is possible is if the index 1 is def, index 2 is use, and index 3 is use
                        // only possible operation with this combination is with a definition -> only 2 registers needed to spill
                        String spillReg1 = decompose[1];
                        String spillReg2 = decompose[2];
                        String spillReg3 = decompose[3];
                        String newLine = "";
                        newLine += String.format("\tlw %s, %d(%s)\n", "$t8", spillList.indexOf(spillReg2) * 4, "$gp");
                        newLine += String.format("\tlw %s, %d(%s)\n", "$t9", spillList.indexOf(spillReg3) * 4, "$gp");
                        String modifyLine = info.get(var);
                        modifyLine = modifyLine.replaceAll("\\" + spillReg1, "\\$t8");
                        modifyLine = modifyLine.replaceAll("\\" + spillReg2, "\\$t8");
                        modifyLine = modifyLine.replaceAll("\\" + spillReg3, "\\$t9");
                        newLine += modifyLine;
                        newLine += String.format("\tsw %s, %d(%s)\n", "$t8", spillList.indexOf(spillReg1) * 4, "$gp");
                        info_result.add(newLine);
                        break;
                    }
                    default:
                        System.out.println("Line where went wrong is " + info.get(var));
                        throw new RuntimeException("SPILLING WENT TERRIBLY WRONG SOMEWHERE SOMEHOW");
                }
            }

            // Now replace all virtual reg with the actual reg
            for (int i = 0; i < info_result.size(); i++) {
                for (String virtual : pairing.keySet()) {
                    info_result.set(i, info_result.get(i).replaceAll("\\" + virtual, "\\" + pairing.get(virtual)));
                }
            }

            return info_result;
        } 

        // Now replace all virtual reg with the actual reg
        for (int i = 0; i < info.size(); i++) {
            for (String virtual : pairing.keySet()) {
                info.set(i, info.get(i).replaceAll("\\" + virtual, "\\" + pairing.get(virtual)));
            }
        }

        return info;
    }

    private static boolean isMove(String command, int pos) {
        return command.equals("move");
    }

    private static boolean isDef(String command, int pos) {
        String useDef = "add addu addi sub subu div divu rem remu mul and neg " +
        "nor not or ori xor xori sll sllv srl srlv mfhi mflo la li lb lw slt " + 
        "slti sltu";
        return pos == 1 && useDef.contains(command);
    }

    private static boolean isUse(String command, int pos) {
        return !isDef(command, pos);
    }

    private static int[] checkHowManySpill(String[] decompose, List<String> spill) {
        int[] count = new int[decompose.length];
        for (int i = 1; i < decompose.length; i++) {
            count[i] = 0;
            if (spill.contains(decompose[i])) {
                count[i] = 1;
            }
        }
        return count;
    }

    private static int numOnes(int[] input) {
        int count = 0;
        for (int val : input) {
            if (val == 1) {
                count++;
            }
        }
        return count;
    }

    // separates every line in the code into separate components
    private static List<String> reconstituteCode(List<String> orig) {
        List<String> newList = new ArrayList<>();
        
        for (String line : orig) {
            String[] splitted = line.split("\n");
            for (String split : splitted) {
                newList.add(split + "\n");
            }
        }

        return newList;
    }
}