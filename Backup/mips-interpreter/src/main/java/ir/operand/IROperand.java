package ir.operand;

import ir.IRInstruction;

public abstract class IROperand {

    protected String value;

    protected IRInstruction parent;

    public IROperand(String value, IRInstruction parent) {
        this.value = value;
        this.parent = parent;
    }

    public IRInstruction getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean equals(Object other) {
        if (!(other instanceof IROperand)) {
            return false;
        } else if (this == other) {
            return true;
        } else {
            return this.value.equals(((IROperand)(other)).value);
        }
    }
    
    public void changeValue(String value) {
    	this.value = value;
    }
    
    public String getValue() {
    	return this.value;
    }

}
