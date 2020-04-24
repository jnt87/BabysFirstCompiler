package ir;

import ir.operand.IROperand;

public class IRCPEntry {
	IROperand left;
	IROperand right;
	
	public IRCPEntry(IROperand left, IROperand right) {
		this.left = left;
		this.right = right;
	}
}
