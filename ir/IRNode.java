package ir;

import ir.IRInstruction;
import ir.operand.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class IRNode {
    public List<IRInstruction> instructions = new ArrayList<>();
    public ArrayList<IRNode> edgeList = new ArrayList<>();
    public ArrayList<IRNode> parentNode = new ArrayList<>();
    public int function;

    public Set<IRInstruction> IN = new HashSet<>();
    public Set<IRInstruction> OUT = new HashSet<>();
    public Set<IRInstruction> GEN = new HashSet<>();
    public Set<IRInstruction> KILL = new HashSet<>();

    public IRNode() {}

    public IRNode (IRNode node) {
        this.instructions = new ArrayList<>(node.instructions);
        this.parentNode = new ArrayList<>(node.parentNode);
        this.edgeList = new ArrayList<>(node.edgeList);
        this.IN = node.IN;
        this.OUT = node.OUT;
        this.GEN = node.GEN;
        this.KILL = node.KILL;
    }

    public String toString() {
        String returnVal = "";

        for (IRInstruction instruction : instructions) {
            returnVal += instruction.toString() + " :: ";
        }

        return returnVal;
    }

    /**
     * Function that generates the singleton sets that reaches this current definition
     * @return
     */
    public HashSet<IRInstruction> generateSingleton(boolean oneOperand) {
        ArrayList<IRInstruction> list = new ArrayList<>();
        for (IRNode parent : this.edgeList) {
            list.addAll(parent.OUT);
        }


        ArrayList<IRInstruction> singleton = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
        	if (list.get(i).opCode.equals(IRInstruction.OpCode.ASSIGN)) {
                boolean duplicate = false;
                for (int j = 0; j < list.size(); j++) {
                    if ((list.get(i) != list.get(j) && list.get(i).operands[0].getValue().equals(list.get(j).operands[0].getValue()))) { //||
                        //Arrays.asList(DeadCode.assignOps).contains(list.get(j).opCode)) {
                        if (!(list.get(i).operands.length <= 1 || list.get(j).operands.length <= 1)) {
                            if (list.get(i).operands[1].getValue()!=list.get(j).operands[1].getValue()) {
                                duplicate = true;
                            }  
                        }
                    }
                }
                if (!duplicate) {
                    singleton.add(list.get(i));
                }
        	}
            
            
        }

        list.clear();
        if (oneOperand) {
            for (int i = 0; i < singleton.size(); i++) {
                if (singleton.get(i).operands.length <= 1) {
                    list.add(singleton.get(i));
                }
            }
            singleton.clear();
            singleton.addAll(list);
        }

        HashSet<IRInstruction> returnVal = new HashSet<IRInstruction>();
        returnVal.addAll(singleton);
        return returnVal;
    }
}