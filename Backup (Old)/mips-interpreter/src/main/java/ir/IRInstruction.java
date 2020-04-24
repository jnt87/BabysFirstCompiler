package ir;

import ir.operand.IROperand;

public class IRInstruction {

    public enum OpCode {
        ASSIGN,
        ADD, SUB, MULT, DIV, AND, OR,
        GOTO,
        BREQ, BRNEQ, BRLT, BRGT, BRLEQ, BRGEQ,
        RETURN,
        CALL, CALLR,
        ARRAY_STORE, ARRAY_LOAD,
        LABEL;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public OpCode opCode;

    public IROperand[] operands;

    public int irLineNumber;

    public boolean isCritical;  //this is a custom var that we've added to the program

    public IRInstruction() {}

    public IRInstruction(OpCode opCode, IROperand[] operands, int irLineNumber) {
        this.opCode = opCode;
        this.operands = operands;
        this.irLineNumber = irLineNumber;
    }

    public String toString() {
        String returnVal = String.format("%s: %s", this.irLineNumber, this.opCode.toString());
        for (IROperand operand : this.operands) {
            returnVal += ", " + operand;
        }
        return returnVal;
    }

}
