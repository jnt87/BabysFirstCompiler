package ir;

import ir.datatype.*;
import ir.operand.*;
import ir.IRInstruction.OpCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class IRRedundancyElimination {
	
	public ArrayList<IRNode> rootList;
	public ArrayList<IRVNEntry> vn = new ArrayList<>();
	public int lastHashUsed = 0;
	public IRCFG cfg;
	public ArrayList<IRNode> visited = new ArrayList<IRNode>();
	
	public IRRedundancyElimination(IRCFG cfg) {
		this.rootList = cfg.rootList;
		this.cfg = cfg;
	}
	
	public void SVN(IRNode node) {
        this.vn = new ArrayList<>();
		for (IRInstruction instruction: node.instructions) {
			if (instruction.opCode.equals(instruction.opCode.ASSIGN)) {
				if (inTableVariable(instruction.operands[1].toString())<0) {
					IRVNEntry entry1 = new IRVNEntry(instruction.operands[1].toString(),lastHashUsed);
					IRVNEntry entry2 = new IRVNEntry(instruction.operands[0].toString(),lastHashUsed);
					vn.add(entry1);
					vn.add(entry2);
					lastHashUsed++;
				} else {
					IRVNEntry entry2 = new IRVNEntry(instruction.operands[0].toString(),inTableVariable(instruction.operands[1].toString()));
					vn.add(entry2);
				}
			}else if(convertOperator(instruction.opCode)>0){
				String leftPart = instruction.operands[0].toString();
				String leftOperand = instruction.operands[1].toString();
				double leftOperandVN;
				String rightOperand = instruction.operands[2].toString();
				double rightOperandVN;
				double operator = convertOperator(instruction.opCode);
				
				//Order the operands if needed
				if (operator<5 && operator>0) {
					if(leftOperand.compareTo(rightOperand)>0) {
						String auxiliar = leftOperand;
						leftOperand = rightOperand;
						rightOperand = auxiliar;
					}
				}
				
				//Look for both operands VN
				if (inTableVariable(leftOperand)<0) {
					IRVNEntry entry = new IRVNEntry(leftOperand,lastHashUsed);
					vn.add(entry);
					leftOperandVN = lastHashUsed;
					lastHashUsed++;
				}else {
					leftOperandVN = inTableVariable(leftOperand);
				}
				if (inTableVariable(rightOperand)<0) {
					IRVNEntry entry = new IRVNEntry(rightOperand,lastHashUsed);
					vn.add(entry);
					rightOperandVN = lastHashUsed;
					lastHashUsed++;
				}else {
					rightOperandVN = inTableVariable(rightOperand);
				}
				
				//Create hash with <operator,leftOperand,rightOperand>
				double hash = CPH3(operator,leftOperandVN,rightOperandVN);
				
				//Look for hash in table
				String substitute = inTableHash(hash);
				IRInstruction substituteInstruction = inTableHashInstruction(hash);
				if (substitute!=null) {
					IROperand[] newOperands = new IROperand[2];
					newOperands[0] = instruction.operands[0];
					newOperands[1] = substituteInstruction.operands[0];
					
					instruction.opCode = instruction.opCode.ASSIGN;
					instruction.operands = newOperands;
				} else {
					if (inTableVariable(leftPart)>=0) {
						changeVN(leftPart,hash,instruction);
					} else {
						IRVNEntry entry = new IRVNEntry(leftPart,hash,instruction);
						vn.add(entry);
					}
				}
				
			}
		}
	}
	
	public void SVNHelper(IRNode root) {
		if (!this.visited.contains(root)) {
			SVN(root);
			this.visited.add(root);
			if (root.edgeList.size()>0) {
				for (IRNode child: root.edgeList) {
					SVNHelper(child);
				}
			}	
		}
	}
	
	public void SVNHelperEEB(IRNode root, IRCFG cfg) {
		if (!this.visited.contains(root)) {
			for (IRNode path : cfg.EBBFinder(root)) {
				SVN(path);
			}
			this.visited.add(root);
			if (root.edgeList.size()>0) {
				for (IRNode child: root.edgeList) {
					SVNHelperEEB(child,cfg);
				}
			}	
		}
	}
	
	//Transform the operation code into an double for posterior hashing
	public double convertOperator(OpCode opCode) {
		switch(opCode) {
			case ADD:
				return 1;
			case MULT:
				return 2;
			case AND:
				return 3;
			case OR:
				return 4;
			case SUB:
				return 5;
			case DIV:
				return 6;
			default:
				return -1;
		}
		
	}
	
	//Look for an entry by variable and if it finds it return the VN
	public double inTableVariable(String variable) {
		if(this.vn.size()>0) {
			for (IRVNEntry entry: this.vn) {
				if (variable.equals(entry.variable)) {
					return entry.hashValue;
				}
			}
		}
		return -1;
	}
	
	//Look for an entry by hash and if it finds it return true
	public String inTableHash(double hash) {
		if(this.vn.size()>0) {
			for (IRVNEntry entry: this.vn) {
				if (hash == entry.hashValue) {
					return entry.variable;
				}
			}
		}
		return null;
	}
	
	//Look for an entry by hash and if it finds it return true
		public IRInstruction inTableHashInstruction(double hash) {
			if(this.vn.size()>0) {
				for (IRVNEntry entry: this.vn) {
					if (hash == entry.hashValue) {
						return entry.instruction;
					}
				}
			}
			return null;
		}
	
	//Change VN value of an entry introducing the variable
	public void changeVN(String variable, double hash, IRInstruction instruction) {
		if(this.vn.size()>0) {
			for (IRVNEntry entry: this.vn) {
				if (variable.equals(entry.variable)) {
					entry.hashValue = hash;
					entry.instruction = instruction;
				}
			}
		}
	}
	
	public double CantorPairingHash(double d, double zd) {
		return 0.5*(d+zd)*(d+zd+1)+zd;
	}
	
	public double CPH3 (double x, double y, double z) {
		return CantorPairingHash(CantorPairingHash(x,y),z);
	}
	
	public void print() {
		System.out.println("**VN-Table**");
		for (IRVNEntry entry: this.vn) {
			System.out.println(entry.toString());
		}
	}
	
	public IROperand getOperand() {
		//cfg.program.
		return null;
	}
}
