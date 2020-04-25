package ir;

import ir.IRInstruction.OpCode;
import ir.operand.IROperand;

import java.util.ArrayList;
import java.util.HashSet;


public class IRConstantPropagation {
	
	public ArrayList<IRNode> visited = new ArrayList<IRNode>();
	
	public void cp(IRNode node) {
		ArrayList<IRCPEntry> copyTable = new ArrayList<IRCPEntry>();
		for (IRInstruction instruction : node.instructions) {
			if (instruction.opCode.equals(instruction.opCode.ASSIGN)) {
				instruction.operands[1] = replace(instruction.operands[1],copyTable);
			}else if(convertOperator(instruction.opCode)){
				instruction.operands[1] = replace(instruction.operands[1],copyTable);
				instruction.operands[2] = replace(instruction.operands[2],copyTable);
			}
			if (instruction.opCode.equals(instruction.opCode.ASSIGN) || convertOperator(instruction.opCode)) {
				copyTable = remove(instruction.operands[0],copyTable);
			}
			if (instruction.opCode.equals(instruction.opCode.ASSIGN)) {
				IRCPEntry insertion = new IRCPEntry(instruction.operands[0],instruction.operands[1]);
				copyTable.add(insertion);
			}
		}
	}
	
	public void cpHelper(IRNode root,IRCFG cfg) {
		if (!this.visited.contains(root)) {
			HashSet<IRInstruction> singletonHashSet = root.generateSingleton(false);
			ArrayList<IRInstruction> singletonInstructions = new ArrayList<IRInstruction>(singletonHashSet);
			this.visited.add(root);
			root.instructions.addAll(0,singletonInstructions);
			cp(root);
			root.instructions.removeAll(singletonInstructions);
			if (root.edgeList.size()>0) {
				for (IRNode child: root.edgeList) {
					cpHelper(child,cfg);
				}
			}
		}
	}
	
	public IROperand replace(IROperand operand,ArrayList<IRCPEntry> copyTable) {
		if (copyTable.size()>0) {
			for (IRCPEntry entry : copyTable) {
				if (operand.getValue().equals(entry.left.getValue())) {
					return entry.right;
				}
			}
		}
		return operand;
	}
	
	public ArrayList<IRCPEntry> remove(IROperand operand, ArrayList<IRCPEntry> copyTable) {
		ArrayList<IRCPEntry> output = new ArrayList<IRCPEntry>();
		if (copyTable.size()>0){
			for (IRCPEntry entry : copyTable) {
				if (!entry.left.getValue().equals(operand.getValue()) && !entry.right.getValue().equals(operand.getValue())) {
					output.add(entry);
				}
			}
		}
		return output;
	}
	
	public boolean convertOperator(OpCode opCode) {
		switch(opCode) {
			case ADD:
			case MULT:
			case AND:
			case OR:
			case SUB:
			case DIV:
			case BREQ:
			case BRNEQ:
			case BRLT:
			case BRGT:
			case BRLEQ:
			case BRGEQ:
				return true;
			default:
				return false;
		}
		
	}
}

