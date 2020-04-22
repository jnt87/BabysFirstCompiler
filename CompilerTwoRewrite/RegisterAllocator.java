import java.util.*;
import ir.*;


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
}