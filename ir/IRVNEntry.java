package ir;

public class IRVNEntry {
	public String variable;
	public double hashValue;
	public IRInstruction instruction = null;
	
	public IRVNEntry(String variable, double hash) {
		this.variable = variable;
		this.hashValue = hash;
	}
	
	public IRVNEntry(String variable, double hash, IRInstruction instruction) {
		this.variable = variable;
		this.hashValue = hash;
		this.instruction = instruction;
	}
	
	public String toString() {
		return "Entry: "+this.variable+" -> "+this.hashValue;
	}
}
