package ir;

import ir.datatype.IRType;
import ir.operand.IRVariableOperand;

import java.util.List;

public class IRFunction {

    public String name;

    public IRType returnType;

    public List<IRVariableOperand> parameters;

    public List<IRVariableOperand> variables;

    public List<IRInstruction> instructions;

    public IRFunction(String name, IRType returnType,
                      List<IRVariableOperand> parameters, List<IRVariableOperand> variables,
                      List<IRInstruction> instructions) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.variables = variables;
        this.instructions = instructions;
    }

    public String toString() {
        String returnVal = String.format("Function name is %s(", this.name);
        for (IRVariableOperand param : this.parameters) {
            returnVal += param + ", ";
        }
        returnVal = returnVal.substring(returnVal.length() - 2).equals(", ") ? returnVal.substring(0, returnVal.length() - 2) : returnVal;
        returnVal += ")\n";
        for (IRInstruction instruction : instructions) {
            returnVal += instruction.toString() + "\n";
        }
        return returnVal;
    }
}
