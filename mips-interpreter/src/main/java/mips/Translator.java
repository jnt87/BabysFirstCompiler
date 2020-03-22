package mips;

import ir.*;
import ir.datatype.*;
import ir.operand.*;
import ir.IRInstruction.OpCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Translator {
		public final boolean DEBUG = true;
		public IRProgram program;
		public List<String> LOC;

		public Translator(IRProgram program) {
				this.program = program;
				this.LOC = new ArrayList<String>();
				String temp = "";
				for (IRFunction func : this.program.functions) {
						for (IRInstruction ins : func.instructions) {
								temp = translate_instruction(ins);
								if (this.DEBUG) {
										System.out.println(ins);
										System.out.println(temp);
										System.out.println();
								}
						}
				}
		}

		public void test() {
				System.out.println("test");
		}

		public boolean is_var(IROperand operand) {
				//write code to help determine if its an i-type instruction
				return true;
		}

		public String translate_instruction(IRInstruction ins) {
				String mips_ins = "wip";
				switch ( ins.opCode ) {
						//case ASSIGN:
						case ASSIGN: 
								mips_ins = assign(ins);
								break;
						case ADD:
								mips_ins = add(ins);
								break;
						case SUB:
								mips_ins = sub(ins);
								break;
						case MULT:
								mips_ins = mult(ins);
								break;
						case DIV: 
								mips_ins = div(ins);
								break;
						case AND: 
								mips_ins = and(ins);
								break;
						case OR:
								mips_ins = or(ins);
								break;
						case GOTO:
								mips_ins = jump(ins);
								break;
						case BREQ:
								mips_ins = breq(ins);
								break;
						case BRNEQ:
								mips_ins = brneq(ins);
								break;
						case BRLT:
								mips_ins = brlt(ins);
								break;
						case BRGT:
								mips_ins = brgt(ins);
								break;
						case BRLEQ:
								mips_ins = brleq(ins);
								break;
						case BRGEQ:
								mips_ins = brgeq(ins);
								break;
						case RETURN:
								mips_ins = ret(ins);
								break;
						case CALL:
								mips_ins = call(ins);
								break;
						case CALLR:
								mips_ins = call(ins);
								break;
						case ARRAY_STORE:
								mips_ins = astore(ins);
								break;
						case ARRAY_LOAD:
								mips_ins = aload(ins);
								break;
						default:
								break;
				}
				return mips_ins;
		}

		public String assign(IRInstruction tigerir) {
				String mips = "";
				if (tigerir.operands.length > 2) { //array assign
						mips = "error array assign";
				} else {
						if (is_var(tigerir.operands[1])) {
								mips = "\tmove " + tigerir.operands[0] + ", " + tigerir.operands[1] + "\n";
						} else {
								mips = "\tli " + tigerir.operands[0] + ", " + (Integer.parseInt(tigerir.operands[1].getValue()) % 65535) + "\n";
								mips = "\tlui " + tigerir.operands[0] + ", " + (Integer.parseInt(tigerir.operands[1].getValue()) >> 16) + "\n";
						}
				}
				return mips;
		}

		public String add(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) {
						mips = "\tadd " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else {
						mips = "\taddi " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				}
				return mips;
		}

		public String sub(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) {
						mips = "\tsub " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else {
						mips = "\tsubi " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				}
				return mips;
		}

		public String mult(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) { //unsigned?
						mips = "\tmul " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else { //always signed
						mips = mips + "\tli t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) % 65535) + "\n";
						if (true) { // if operand >>16 
								mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						}
						mips = "\tmul " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", t1\n";
				}
				return mips;
		}

		public String div(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) { //unsigned?
						mips = "\tdiv " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else { //always signed
						mips = mips + "\tli t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) % 65535) + "\n";
						if (true) {
								mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						}
						mips = "\tdiv " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", t1\n";
				}
				return mips;
		}
		public String and(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) { //unsigned?
						mips = "\tand " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} /*else { //always signed
						mips = mips + "\tli t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) % 65535) + "\n";
						if (true) {
								mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						}
						mips = "\tdiv " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", t1\n";
				}*/
				return mips;
		}
		public String or(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) { //unsigned?
						mips = "\tor " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else { //always signed
						//mips = mips + "\tli t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) % 65535) + "\n";
						//if (true) {
						//		mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						//}
						mips = "\tori " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", "+tigerir.operands[2] + "\n";
				}
				return mips;
		}
		public String jump(IRInstruction tigerir) {
				String mips = "";
				mips = "\tjump " + tigerir.operands[0] + "\n";
				return mips;
		}
		public String breq(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbeq " + tigerir.operands[1] + ", " + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brneq(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbne " + tigerir.operands[1] + ", " + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brlt(IRInstruction tigerir) {
				String mips = "";
				mips = "\tblt " + tigerir.operands[1] + ", " + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brgt(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbgt " + tigerir.operands[1] + ", " + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brleq(IRInstruction tigerir) {
				String mips = "";
				mips = mips + "\tsub t1, " + tigerir.operands[2] + ", " + tigerir.operands[1] + "\n";
				mips = mips + "\tbgez t1,"+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brgeq(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbge "+ tigerir.operands[1] + ", " + tigerir.operands[2] + ", " + tigerir.operands[0] + "\n";
				return mips;
		}
		public String ret(IRInstruction tigerir) {
				String mips = "";
				mips = mips + "sw " + tigerir.operands[0] + ", $30\n";
				mips = mips + "move $29, $30\n";
				mips = mips + "\tjr $31\n";
				return mips;
		}

		public String call(IRInstruction tigerir) {
				String mips = "";
				//alloc space
				int size  = stack_size(tigerir.operands[0].getValue());
				mips = mips + "\taddi $29, $29, -" + size + "\n";
				//store temps
				//bookkeeping
				//params
				mips = mips + "\tjal " + tigerir.operands[0] + "\n";
				return mips;
		}

		public String callr(IRInstruction tigerir) {
				String mips = "";
				//alloc space
				//store temps
				//bookkeeping
				//params
				//empty rv
				mips = mips + "\tjal " + tigerir.operands[0] + "\n";
				return mips;
		}

		public String astore(IRInstruction tigerir) {
				String mips = "";
				mips = mips + "\taddi t1, " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				mips = mips + "\tsw " + tigerir.operands[0] + ", t1\n";
				return mips;
		}
		public String aload(IRInstruction tigerir) {
				String mips = "";
				mips = mips + "\taddi t1, " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				mips = mips + "\tlw " + tigerir.operands[0] + ", t1\n";
				return mips;
		}

		public int stack_size(String func_name) {
				int size = 0;
				for(IRFunction f : this.program.functions) {
						int temp = f.name.compareTo(func_name);
						if (temp == 0)	{
								System.out.println("Params: ");
								for( IRVariableOperand par : f.parameters) {
										System.out.println(par);
								}
								//System.out.println("Vars: ");
								for( IRVariableOperand var : f.variables) {
										System.out.print(var+"-->");
										System.out.println(var.type);
										//if(var.type.toString().compareTo("int") != 0 | var.type.toString().compareTo("float") != 0){
										if(var.type instanceof IRArrayType) {
												System.out.println("Size: "+((IRArrayType)var.type).getSize());
										}
								}
								size = size + (f.variables.size() * 4); //local data
								size = size + 4; //return value
								
						}
				}
				return size;
		}
		public int variable_size(IRVariableOperand var) {
				return 0;
		}
}
