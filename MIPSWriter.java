import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.io.PrintWriter;
import java.io.File;
import ir.IRFunction;

public class MIPSWriter {
    public static void writeProgramToFile(String fileName, HashMap<IRFunction, List<String>> info) throws Exception {
        PrintWriter writer = new PrintWriter(new File(fileName));
        writer.println(".text");
        writer.println(".globl main");

        if (InstructionSelection.virtualRegConvention) {
            for (IRFunction func : InstructionSelection.usedRegisters.keySet()) {
                HashMap<String, String> pair = RegisterAllocator.virtualActualMap.get(func);
                for (String label : InstructionSelection.usedRegisters.get(func)) {
                    if (pair.get(label) == null) {
                        writer.print(String.format("\tli %s, 0\n", label));
                        continue;
                    }
                    writer.print(String.format("\tli %s, 0\n", pair.get(label)));
                }
            }
            writer.println("j main");
        }

        for (IRFunction function : info.keySet()) {
            List<String> bodyList = info.get(function);
            HashSet<String> labels = new HashSet<>();

            for (int i = 0; i < bodyList.size(); i++) {
                if (bodyList.get(i).contains(":") && !bodyList.get(i).contains("()")) {
                    labels.add(bodyList.get(i).split(":")[0]);
                }
            }

            for (String body : bodyList) {
                if (body.contains(":") && !body.contains("()")) {
                    // labels.add(body.split(":")[0]);
                    body = body.replaceAll(":", "_" + function.name + ":");
                } else if (body.contains("()")) {
                    body = body.replaceAll("[()]", "");
                } else {
                    for (String label : labels) {
                        if (body.contains(label)) {
                            body = body.replaceAll(label, label + "_" + function.name);
                        }
                    }
                }
                
                writer.write(body);
            }
            writer.flush();
        }
        writer.close();
    }
}