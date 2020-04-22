package ir.operand;

import ir.IRInstruction;
import ir.datatype.IRType;

public class IRVariableOperand extends IROperand {

    public IRType type;

    public IRVariableOperand(IRType type, String name, IRInstruction parent) {
        super(name, parent);
        this.type = type;
    }

    public String getName() {
        return value;
    }

    public boolean equals(Object other) {
        if (!(other instanceof IRVariableOperand)) {
            return false;
        } else if (this == other) {
            return true;
        } else {
            return this.getName().equals(((IRVariableOperand) other).getName());
        }
    }
}
