import ir.*;
import main.java.mips.operand.Register;

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

        System.out.println("The completed code is " + code);
        // RegisterAllocator.spillAlgorithm(mipsVersion, new ArrayList<>(), new HashMap<>());
        RegisterAllocator.test_allocation(mipsVersion);
        MIPSWriter.writeProgramToFile(Optimized_IRFile.substring(0, Optimized_IRFile.indexOf(".")) + ".s", mipsVersion);
    }
}