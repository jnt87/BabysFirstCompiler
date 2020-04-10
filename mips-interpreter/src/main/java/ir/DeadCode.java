package ir;

import ir.IRInstruction.OpCode;
import ir.operand.IROperand;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DeadCode {

    public static OpCode[] assignOps = {OpCode.ASSIGN, OpCode.ADD, OpCode.SUB, OpCode.MULT, OpCode.DIV, OpCode.CALLR, OpCode.ARRAY_LOAD};
    // ARRAY_STORE is a special case to consider if we are considering

    public static OpCode[] crtiicalOps = {OpCode.GOTO, OpCode.BREQ, OpCode.BRNEQ,
        OpCode.BRLT, OpCode.BRGT, OpCode.BRLEQ, OpCode.BRGEQ, OpCode.RETURN,
        OpCode.CALL, OpCode.CALLR, OpCode.ARRAY_STORE, OpCode.LABEL};

    public static boolean change = false;
    /**
     * This is the initial optimization, removes all unused variables
     * Defined as in variables that are initialized but never part of a critical instruction
     * regardless of whether reachable or not
     * 
     * Call this function in IRCFG in the constructor where the intial program is read in/assigned
     * @param program The entirity of the program the file will look at
     */
    public static void removeUnusedVar(IRProgram program) {
        List<IRFunction> functions = program.functions;
        
        for (IRFunction function : functions) {
            ArrayList<IRInstruction> workList = new ArrayList<>();
            List<IRInstruction> instructions = function.instructions;
            for (IRInstruction instruction : instructions) {
                instruction.isCritical = false;
                if (isCritical(instruction)) {
                    instruction.isCritical = true;
                    workList.add(instruction);
                }
            }

            while (!workList.isEmpty()) {
                IRInstruction temp = workList.remove(0);
                for (IROperand operand : temp.operands) {
                    for (IRInstruction instruction : instructions) {
                        if (!instruction.isCritical && Arrays.asList(instruction.operands).contains(operand)) {
                            instruction.isCritical = true;
                            workList.add(instruction);
                        }
                    }
                }
            }

            sweep(function);
            
        }
    }

    /**
     * Rewrote functionality for reaching def using the CFG tree in more modular
     * recursive technique
     * @param cfg The CFG for the entire program
     */
    public static void reachingDefRewrite(IRCFG cfg) {
        List<IRNode> rootList = cfg.rootList;
        generateInOut(cfg);
        for (IRNode root : rootList) {
            HashSet<IRNode> visited = new HashSet<>();
            ArrayList<IRInstruction> workList = new ArrayList<>();
            recurseInitialCrit(root, visited, workList);

            while (!workList.isEmpty()) {
                IRInstruction instruction = workList.remove(0);

                IRNode container = findNodeContaining(instruction, root, new HashSet<IRNode>());

                if (container == null) {
                    System.out.println(instruction);
                    throw new IllegalArgumentException("Container is null");    // this should never happen
                }

                int line = findIndexOfInstruction(container, instruction);

                if (Arrays.asList(assignOps).contains(instruction.opCode)) {
                    for (int i = 1; i < instruction.operands.length; i++) {
                        findCritReach(container, instruction.operands[i], line, workList);
                    }
                } else {
                    for (IROperand operand : instruction.operands) {
                        findCritReach(container, operand, line, workList);
                    }
                }                
            }
            //System.out.println("Complete One Function");
        }

        for (IRNode root : rootList) {
            sweep(root, new HashSet<IRNode>()); // this sweeps from the IRNode
        }

        for (IRFunction function : cfg.program.functions) {
            sweep(function);    // this sweeps from the actual program
        }
    }

    /**
     * Given an instruction, the node it is located in and the line number
     * in the node, this function marks the definition that reaches it as 
     * being critical
     * @param curr The IRNode which the critical instruction is located
     * @param operand The operand that we are searching for
     * @param index The index the original instruction is located in
     * @param workList The workList of all critical operations we have not yet checked the operands of
     */
    private static void findCritReach(IRNode curr, IROperand operand, int index, ArrayList<IRInstruction> workList) {
        List<IRInstruction> instructions = curr.instructions;

        boolean found = false;
        for (int i = index - 1; i >= 0; i--) {
            IRInstruction currInstruc = instructions.get(i);
            if (Arrays.asList(assignOps).contains(currInstruc.opCode) && currInstruc.operands[0].equals(operand)) {
                found = true;
                currInstruc.isCritical = true;
                workList.add(currInstruc);
                break;
            }
        }

        if (!found) {
            for (IRInstruction instruc : curr.IN) {
                if (Arrays.asList(assignOps).contains(instruc.opCode) && instruc.operands[0].equals(operand) && !instruc.isCritical) {
                    found = true;
                    instruc.isCritical = true;
                    workList.add(instruc);
                    // DO NOT ADD BREAK STATEMENT HERE, TRUST ME
                }
            }
        }
    }

    /**
     * Private helper method to find the index of a given instruction in a
     * node. Note this function only works if the instruction exists in the
     * node
     * @param node The node we are looking for the instruction from
     * @param instruction The instruction that we are looking for
     */
    private static int findIndexOfInstruction (IRNode node, IRInstruction instruction) {
        for (int i = 0; i < node.instructions.size(); i++) {
            if (instruction == node.instructions.get(i)) {
                return i;
            }
        }
        return -1;  // This case should never occur unless checking if IRNode contains an IRInstruction
    }

    /**
     * Private method that finds all of the instructions that are deemed
     * to be critical based solely on the OpCode and adds it to the worklist
     * @param curr The current node that we are traversing
     * @param visited The list of IRNodes that we have already visited
     * @param workList The workList of all critical operations we have not yet checked the operands of
     */
    private static void recurseInitialCrit(IRNode curr, HashSet<IRNode> visited, ArrayList<IRInstruction> workList) {
        if (!visited.contains(curr)) {
            visited.add(curr);
            for (IRInstruction instruction : curr.instructions) {
                instruction.isCritical = false;
                if (isCritical(instruction)) {
                    instruction.isCritical = true;
                    workList.add(instruction);
                }
            }
            
            for (IRNode child : curr.edgeList) {
                recurseInitialCrit(child, visited, workList);
            }
        }
    }

    /**
     * Private method where given an IRInstruction finds the IRNode that
     * contains said instruction. If it is not found, return null. Note
     * in the case of this program the function should never return null
     * @param instruction The instruction that we are looking for
     * @param curr The IRNode that we are currently looking for the instruction in
     * @param visitedSet The set of all IRNode that we have already traversed
     */
    private static IRNode findNodeContaining (IRInstruction instruction, IRNode curr, HashSet<IRNode> visitedSet) {
        IRNode found = null;
        if (!visitedSet.contains(curr)) {
            visitedSet.add(curr);
            if (curr.instructions.contains(instruction)) {
                return curr;
            } else {
                for (IRNode child : curr.edgeList) {
                    found = findNodeContaining(instruction, child, visitedSet);

                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return found;
    }

    /**
     * Removes all IRInstruction not marked as critical from the IRFunction
     * @param function The function being swept
     */
    public static void sweep(IRFunction function) {
        List<IRInstruction> instructions = function.instructions;
        List<IRInstruction> criticalInstruc = new ArrayList<>();

        for (IRInstruction instruction : instructions) {
            if (instruction.isCritical) {
                criticalInstruc.add(instruction);
            } else {
                change = true;
            }
        }
        // This is done to prevent issues relating to iterating through list and simultaneously removing it
        instructions.clear();
        instructions.addAll(criticalInstruc);
    }
    
    /**
     * Removes all IRInstruction not marked as critical from an entire CFG
     * starting from the root node
     * @param curr The root node we are sweeping
     * @param visited The set of all IRNode that has already been swept 
     */
    public static void sweep (IRNode curr, HashSet<IRNode> visited) {
        if (!visited.contains(curr)) {
            visited.add(curr);
            ArrayList<IRInstruction> temp = new ArrayList<>();
            for (IRInstruction instruction : curr.instructions) {
                if (instruction.isCritical) {
                    temp.add(instruction);
                } else {
                    change = true;
                }
            }
            curr.instructions.clear();
            curr.instructions.addAll(temp);

            for (IRNode child : curr.edgeList) {
                sweep(child, visited);
            }
        }
    }

    /**
     * This decomposes the program into lined inputs, not the blocks created in IRCFG
     * @param program
     */
    public static void displayProgram(IRProgram program) {
        for (IRFunction function : program.functions) {
            System.out.println(function);
        }
    }

    /**
     * Determines whether or not an instruction is critical based on its
     * OpCode
     * @param instruction The instruction we are checking if critical
     */
    public static boolean isCritical(IRInstruction instruction) {
        for (OpCode op : crtiicalOps) {
            if (instruction.opCode == op) {
                return true;
            }
        }
        return false;
    }

    /*
   * The code following this point pertains to the generation of the IN, OUT, GEN, and KILL
   * Set. The code RELIES on the CFG to be properly constructed to function properly
   */ 

    /**
     * Generate the IN and OUT set for the CFG
     * @param cfg The CFG that IN and OUT set is generated for
     */
    public static void generateInOut(IRCFG cfg) {
        //IRNode curr = cfg.root;
        ArrayList<IRNode> rootList = cfg.rootList;

        for (IRNode curr : rootList) {
            HashSet<IRNode> visited = new HashSet<>();
            clearSet(curr, new HashSet<IRNode>());
            // Generates the KILL and GEN set for each node
            generateGenKillHelper(curr, visited, cfg);

            // Generate the IN and OUT set for each node
            boolean change = false;
            int i = 0;
            do {
                visited = new HashSet<>();
                change = recurseInOutHelper(curr, visited);
                i++;
            } while (change || i < 10);
        }
    }

    /**
     * Clears all of the IN, OUT, GEN, KILL Sets
     * @param curr
     * @param visited
     */
    private static void clearSet(IRNode curr, HashSet<IRNode> visited) {
        if (!visited.contains(curr)) {
            visited.add(curr);
            curr.IN.clear();
            curr.OUT.clear();
            curr.GEN.clear();
            curr.KILL.clear();
            
            for (IRNode child : curr.edgeList) {
                clearSet(child, visited);
            }
        }
    }

    /**
     * Private helper method that continues to regenerate the IN and OUT
     * set until no more changes can be made in generation
     * @param curr The current node that the IN and OUT set is generated for
     * @param visited The set of all IRNode IN and OUT already generated for
     * @return Whether or not IN and OUT set was changed
     */
    private static boolean recurseInOutHelper(IRNode curr, HashSet<IRNode> visited) {
        boolean change = generateInOutHelper(curr);
        for (IRNode child : curr.edgeList) {
            if (!visited.contains(child)) {
                visited.add(child);
                change = recurseInOutHelper(child, visited);
            }
        }
        return change;
    }

    /**
     * Second private method for generating IN and OUT set from CFG
     * @param curr Current node we are generating IN and OUT for
     * @return Whether or not something was added to the IN or OUT
     */
    private static boolean generateInOutHelper(IRNode curr) {
        boolean IN_change = false;
        boolean OUT_change = false;
        for (IRNode parent : curr.parentNode) {
            IN_change = IN_change || curr.IN.addAll(parent.OUT);
        }
        HashSet<IRInstruction> temp = new HashSet<>(curr.IN);
        temp.removeAll(curr.KILL);
        refineGen(curr);
        OUT_change = curr.OUT.addAll(curr.GEN) || curr.OUT.addAll(temp);
        return IN_change || OUT_change;
    }

    /**
     * Adds all IRInstruction generated that is not later killed to the 
     * GEN set in the IRNode 
     * @param curr The IRNode that we are currently refining the GEN set for
     */
    private static void refineGen(IRNode curr) {
        ArrayList<IRInstruction> temp = new ArrayList<>();
        
        for (int i = 0; i < curr.instructions.size(); i++) {
            IRInstruction instruction = curr.instructions.get(i);
            boolean keep = true;

            for (int j = i + 1; j < curr.instructions.size(); j++) {
                IRInstruction after = curr.instructions.get(j);

                if (Arrays.asList(assignOps).contains(after.opCode) & after.operands[0].equals(instruction.operands[0])) {
                    keep = false;
                }

            }

            if (keep) {
                temp.add(instruction);
            }
        }

        curr.GEN.clear();
        curr.GEN.addAll(temp);
    }

    /**
     * Private helper method that generates the KILL set for the function
     * @param curr The current IRNode KILL set is generated for
     * @param visited The set of all IRNode already visited
     * @param cfg The entire graph structure of that we are looking to extract the KILL nodes from
     */
    private static void generateGenKillHelper(IRNode curr, HashSet<IRNode> visited, IRCFG cfg) {
        for (IRInstruction instruction : curr.instructions) {
            Set<IRInstruction> killPortion = findAllAssignmentOf(instruction, cfg);
            if (Arrays.asList(assignOps).contains(instruction.opCode)) {
                curr.GEN.add(instruction);
            }
            if (killPortion != null) {
                curr.KILL.addAll(killPortion);
            }
        }

        curr.KILL.removeAll(curr.GEN);
        for (IRNode child : curr.edgeList) {
            if (!visited.contains(child)) {
                visited.add(child);
                generateGenKillHelper(child, visited, cfg);
            }
        }
    }

    /**
     * This function generates the kill for a specific instruction
     * @param instruction The current instruction you are looking 
     * @param cfg The cfg that you are looking at
     * @return The kill set that contains all definition of the current variable
     */
    private static Set<IRInstruction> findAllAssignmentOf (IRInstruction instruction, IRCFG cfg) {
        Set<IRInstruction> found = new HashSet<>();
        if (Arrays.asList(assignOps).contains(instruction.opCode)) {
            ArrayList<ArrayList<ArrayList<IRInstruction>>> structure = cfg.blocks;
            for (ArrayList<ArrayList<IRInstruction>> function : structure) {
                for (ArrayList<IRInstruction> block : function) {
                    for (IRInstruction instruc : block) {
                        if (Arrays.asList(assignOps).contains(instruc.opCode)) {
                            if (instruc.operands[0].toString().equals(instruction.operands[0].toString())) {
                                found.add(instruc);
                            }
                        }
                    }
                }
            }
            //found.remove(instruction);
            return found;
        }
        return null;
    }

    /**
     * Displays all of the IN and OUT sets in the CFG in a neat, concise manner
     * @param cfg The CFG that we are displaying
     */
    public static void displayInOutSet(IRCFG cfg) {
        ArrayList<IRNode> rootList = cfg.rootList;
        HashSet<IRNode> visited = new HashSet<>();

        for (IRNode root : rootList) {
            displayHelper(root, visited);
            System.out.println("----FUNC-----");
        }
    }

    /**
     * Private helper method to display the created CFG
     * @param curr The current IRNode IN and OUT is diplayed for
     * @param visited The set of all IRNode already visited
     */
    private static void displayHelper (IRNode curr, HashSet<IRNode> visited) {
        if (curr != null && !visited.contains(curr)) {
            visited.add(curr);
            System.out.println("Starting at line " + curr.instructions.get(0));
            System.out.println(curr.IN);
            System.out.println(curr.OUT);
            System.out.println(curr.KILL);
            System.out.println(curr.GEN);
            System.out.println("-------------");
            
            for (IRNode child : curr.edgeList) {
                displayHelper(child, visited);
            }
        }
    }

    /**
     * Methods that displays the IRInstruction separated by the Nodes
     * @param cfg The CFG that displaying the CFG from
     */
    public static void displayIRNodes(IRCFG cfg) {
        for (IRNode root : cfg.rootList) {
            displayIRNodeHelper(root, new HashSet<>());
            System.out.println("--------End Function--------");
        }
    }

    /**
     * Private helper method to display the IRInstruction separated by the
     * node
     * @param curr The current Node that we are printing IRInstructions from
     * @param visited The set of all IRNode already
     */
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

    public static void clearAllCritMark(IRProgram program) {
        for (IRFunction function : program.functions) {
            for (IRInstruction instruction : function.instructions) {
                instruction.isCritical = false;
            }
        }
    }
}
