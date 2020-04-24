import ir.*;
import mips.*;

import java.io.PrintStream;
import java.io.*;
import java.util.*;

public class MipsTranslator {
    public static void fake_main(String[] args) throws Exception {
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
        IRProgram program = irReader.parseIRFile(Optimized_IRFile);

        HashMap<IRFunction, List<String>> mipsVersion = InstructionSelection.handleInstruction(program);

        String code = "";
        for (IRFunction func : mipsVersion.keySet()) {
            List<String> instruct = mipsVersion.get(func);

            for (String line : instruct) {
                if (line.trim().equals("")) {
                    continue;
                }
                code += line;
            }
        }

        HashMap<String, List<RegisterNode>> dependencies_orig = LiveRange.generateDependencies(code);
        // converted the dependencie to another form
        HashMap<IRFunction, List<RegisterNode>> dependencies = convertForm(dependencies_orig, mipsVersion.keySet());

        System.out.println("dependencies is " + dependencies_orig);

        for (IRFunction func : dependencies.keySet()) {
            RegisterGraph graph = new RegisterGraph(dependencies.get(func));
            // graph.color(InstructionSelection.usableRegisters.length);
            graph.color(0);
            HashMap<String, String> pairing = graph.correspondingPhysicalRegisters(Arrays.asList(InstructionSelection.usableRegisters));
            List<String> spillList = graph.registersToSpill();
            // System.out.println("Graph generated");
            // System.out.println("Orig list is " + Arrays.asList(InstructionSelection.usableRegisters));
            // System.out.println("Pairing is " + pairing);
            mipsVersion.put(func, RegisterAllocator.spillAlgorithm(mipsVersion.get(func), spillList, pairing, dependencies.get(func)));
        }
        // System.out.println("replacement complete");
        // System.out.println(mipsVersion);
        // System.out.println("The completed code is " + code);
        // RegisterAllocator.spillAlgorithm(mipsVersion, new ArrayList<>(), new HashMap<>());

        //RegisterAllocator.test_allocation(mipsVersion);
        MIPSWriter.writeProgramToFile(Optimized_IRFile.substring(0, Optimized_IRFile.indexOf(".")) + ".s", mipsVersion);
    }


    // convertion a HashMap<String funcName, List<RegisterNode>> into
    // HashMap<IRFunction, List<RegisterNode>
    private static HashMap<IRFunction, List<RegisterNode>> convertForm(HashMap<String, List<RegisterNode>> orig, Set<IRFunction> functions) {
        HashMap<IRFunction, List<RegisterNode>> convert = new HashMap<>();

        for (String funcName : orig.keySet()) {
            for (IRFunction func : functions) {
                if (func.name.equals(funcName)) {
                    convert.put(func, orig.get(funcName));
                    break;
                }
            }
        }

        return convert;
    }
}