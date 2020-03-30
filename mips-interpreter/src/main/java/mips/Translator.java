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
		public String code;

		public Translator(IRProgram program) {
				this.program = program;
				boolean temp_debug = false;
				this.LOC = new ArrayList<String>();
				String code = ".text\n.align 2\n.globl main\n";
				String temp = "";
				for (IRFunction func : this.program.functions) {
						code = code + "\n\n" + func.name + ":\t";
						if(func.parameters.size() > 4) {
								for(int i = 3; i < func.parameters.size(); i++) {

										code = code + "lw $" + func.parameters.get(i).getName() + " " + (12 + (i * 4)) + "($29)\n";

								}
						};
						for(IRVariableOperand var : func.variables) {
								if( !func.parameters.contains(var) & var.type instanceof IRArrayType) {
										int size  = ((IRArrayType)var.type).getSize() * 4; 
										code = code + "addi $29, $29, -" + size + "\n";
										code = code + "move $"+ var.getName() + ", $29 # this is a local array\n";
								}
						}


						for (IRInstruction ins : func.instructions) {
								temp = translate_instruction(ins);
								if (this.DEBUG && temp_debug) {
										System.out.println(ins);
										System.out.println(temp);
										System.out.println();
								}
								code = code + temp;
						}
				}
				if (this.DEBUG && temp_debug) System.out.println(code);
				this.code = code + "\n\n"; //spim needs a blank line for some reason
		}

		public void test() {
				System.out.println(code);
		}

		public boolean is_var(IROperand operand) {
				//write code to help determine if its an i-type instruction
				String val = operand.toString();
				if(operand == null) return false;
				try {
						int y = Integer.parseInt(val);
				} catch(Exception nfe) {
						return true;
				}
				return false;
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
								mips_ins = callr(ins);
								break;
						case ARRAY_STORE:
								mips_ins = astore(ins);
								break;
						case ARRAY_LOAD:
								mips_ins = aload(ins);
								break;
						case LABEL:
								if(ins.operands[0].toString().compareTo("return") == 0) {
										mips_ins = ret(ins);
								} else {
										mips_ins = ins.operands[0] + ":\t";
								}
								break;
						default:
								break;
				}
				return mips_ins;
		}

		public String assign(IRInstruction tigerir) {
				String mips = "";
				if (tigerir.operands.length > 2) { //array assign
						mips = mips + array_assign(tigerir);;
				} else {
						if (is_var(tigerir.operands[1])) {
								mips = "\tmove $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + "\n";
						} else {
								mips = "\tli $" + tigerir.operands[0] + ", " + tigerir.operands[1] + "\n";
								//mips = "\tlui " + tigerir.operands[0] + ", " + (Integer.parseInt(tigerir.operands[1].getValue()) >> 16) + "\n";
						}
				}
				return mips;
		}
		
		public String array_assign(IRInstruction tigerir) {
				String mips = "";
				mips = mips + "li $temp, " + tigerir.operands[3] + "\n";
				for (int i = 0; i < Integer.parseInt(tigerir.operands[1].getValue()); i++) {
						mips = mips + "sw $temp, "+ (i * 4) + "($" + tigerir.operands[0] + ")\n";
				}
				return mips;
		}

		public String add(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1]) & is_var(tigerir.operands[2])) {
						mips = mips+"\tadd $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + "\n";
				} else if (is_var(tigerir.operands[1]) & !is_var(tigerir.operands[2])) {
						mips = mips+"\taddi $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else {
						mips = mips + "wtf\n";
				}
				return mips;
		}

		public String sub(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1]) & is_var(tigerir.operands[2])) {
						mips = "\tsub $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + "\n";
				} else if (is_var(tigerir.operands[1]) & !is_var(tigerir.operands[2])) {
						mips = "\tsubi $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else {
						mips = mips + "wtf\n";
				}
				return mips;
		}

		public String mult(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[2])) { //unsigned?
						mips = "\tmul " + tigerir.operands[0] + ", " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
				} else { //always signed
						mips = mips + "\tli $t1, " + tigerir.operands[2] + "\n";
						if (true) { // if operand >>16 
								//mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						}
						mips = "\tmul $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $t1\n";
				}
				return mips;
		}

		public String div(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[2])) { //unsigned?
						mips = "\tdiv $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + "\n";
				} else { //always signed
						mips = mips + "\tli $t1, " + tigerir.operands[2] + "\n";
						if (true) {
								//mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						}
						mips = "\tdiv $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $t1\n";
				}
				return mips;
		}
		public String and(IRInstruction tigerir) {
				String mips = "";
				if (is_var(tigerir.operands[1])) { //unsigned?
						mips = "\tand $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + "\n";
				} else {
						mips = "\tandi $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", "+tigerir.operands[2] + "\n";

				}	
				/*else { //always signed
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
						mips = "\tor $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + "\n";
				} else { //always signed
						//mips = mips + "\tli t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) % 65535) + "\n";
						//if (true) {
						//		mips = mips + "\tlui t1, " + (Integer.parseInt(tigerir.operands[2].getValue()) >> 16) + "\n";
						//}
						mips = "\tori $" + tigerir.operands[0] + ", $" + tigerir.operands[1] + ", "+tigerir.operands[2] + "\n";
				}
				return mips;
		}
		public String jump(IRInstruction tigerir) {
				String mips = "";
				mips = "\tjump $" + tigerir.operands[0] + "\n";
				return mips;
		}
		public String breq(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbeq $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brneq(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbne $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brlt(IRInstruction tigerir) {
				String mips = "";
				mips = "\tblt $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brgt(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbgt $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + ", "+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brleq(IRInstruction tigerir) {
				String mips = "";
				mips = mips + "\tsub $t1, $" + tigerir.operands[2] + ", $" + tigerir.operands[1] + "\n";
				mips = mips + "\tbgez $t1, $"+ tigerir.operands[0] + "\n";
				return mips;
		}
		public String brgeq(IRInstruction tigerir) {
				String mips = "";
				mips = "\tbge $"+ tigerir.operands[1] + ", $" + tigerir.operands[2] + ", " + tigerir.operands[0] + "\n";
				return mips;
		}
		public String ret(IRInstruction tigerir) {
				String mips = "";
				//mips = mips + "sw " + tigerir.operands[0] + ", $30\n";
				//mips = mips + "move $29, $30\n";
				if(tigerir.operands.length > 1) {
						mips = mips + "move $v0, "+tigerir.operands[1] + "\n";
				}
				mips = mips + "\tjr $31\n";
				return mips;
		}

		public String call(IRInstruction tigerir) {
				String mips = "";
				boolean has_rv = false;
				switch(tigerir.operands[0].toString()) 
				{
						case "puti": 
								mips = mips + "\tli $a0, $"+tigerir.operands[1]+ "\n";;
								mips = mips + "\tli $v0, 1\n";
								mips = mips + "\tsyscall\n";
								return mips;
						case "putc":
								mips = mips + "\tli $a0, $"+tigerir.operands[1]+ "\n";;
								mips = mips + "\tli $v0, 11\n";
								mips = mips + "\tsyscall\n";
								return mips;
						default:
									//calculate and alloc size
								int max_temps = 10;
								int space = max_temps * 4;
								int aligned_space = space + (space % 8);
								int size_arg_saved_ra = par_size_total(tigerir, has_rv) + saved_size(9) + ra_size();
								int size_arg_saved_ra_needed = size_arg_saved_ra + (size_arg_saved_ra % 8); //pad added at the top 
								int size_needed = size_arg_saved_ra_needed + aligned_space;
			
								//allocate size
								mips = mips + "\taddi $29, $29, -" + size_needed + "\n";
						
								//bookkeeping offset calculations
								//args/params have no offset
								int saved_offset = par_size_total(tigerir, has_rv);
								int ra_offset = saved_offset + saved_size(9); //same as above, could be 8 if we use fp
								int temp_offset = size_arg_saved_ra_needed; //padding 
			
								//store temps
								mips = mips + store_temps(max_temps, temp_offset); //goes to local storage for function
			
								//bookkeeping
								mips = mips + store_saved(9, saved_offset);
								mips = mips + store_ra(ra_offset);
			
								//params
								mips = mips + store_params(tigerir, has_rv);
								//go to the fucntion
								mips = mips + "\tjal " + tigerir.operands[0] + "\n";
						
								//return from func
								mips = mips + load_ra(ra_offset);
								//load temps
								mips = mips + load_temps(max_temps, temp_offset);
						
								mips = mips + "\taddi $29, $29, "+size_needed+"\n";
								return mips;
					}
		}

		public String callr(IRInstruction tigerir) {
				String mips = "";
				boolean has_rv = true;
				switch(tigerir.operands[1].toString()) 
				{
						case "geti": 
								mips = mips + "\tli $v0, 5\n";
								mips = mips + "\tsyscall\n";
								mips = mips + "\tmove $" + tigerir.operands[0]+ ", $v0\n";
								return mips;
						case "getc":
								mips = mips + "\tli $v0, 12\n";
								mips = mips + "\tsyscall\n";
								mips = mips + "\tmove $"+ tigerir.operands[0]+ ", $v0\n";;
								return mips;
						default:
									//calculate and alloc size
								int max_temps = 10;
								int space = max_temps * 4;
								int aligned_space = space + (space % 8);
								int size_arg_saved_ra = par_size_total(tigerir, has_rv) + saved_size(9) + ra_size();
								int size_arg_saved_ra_needed = size_arg_saved_ra + (size_arg_saved_ra % 8); //pad added at the top 
								int size_needed = size_arg_saved_ra_needed + aligned_space;
			
								//allocate size
								mips = mips + "\taddi $29, $29, -" + size_needed + "\n";
						
								//bookkeeping offset calculations
								//args/params have no offset
								int saved_offset = par_size_total(tigerir, has_rv);
								int ra_offset = saved_offset + saved_size(9); //same as above, could be 8 if we use fp
								int temp_offset = size_arg_saved_ra_needed; //padding 
			
								//store temps
								mips = mips + store_temps(max_temps, temp_offset); //goes to local storage for function
			
								//bookkeeping
								mips = mips + store_saved(9, saved_offset);
								mips = mips + store_ra(ra_offset);
			
								//params
								mips = mips + store_params(tigerir, has_rv);
								//go to the fucntion
								mips = mips + "\tjal " + tigerir.operands[1] + "\n";
								
								//get return value
								mips = mips + "\tmove $"+ tigerir.operands[0]+ ", $v0\n";

								//return from func
								mips = mips + load_ra(ra_offset);
								//load temps
								mips = mips + load_temps(max_temps, temp_offset);
						
								mips = mips + "\taddi $29, $29, "+size_needed+"\n";
								return mips;
					}
		}
		public String astore(IRInstruction tigerir) {
				String mips = "";
				if(is_var(tigerir.operands[1]) & is_var(tigerir.operands[2])) {
						mips = mips + "\tadd $temp, " + tigerir.operands[1] + ", " + tigerir.operands[2] + "\n";
						mips = mips + "\tsw $" + tigerir.operands[0] + ", 0($temp)\n";
				} else if (is_var(tigerir.operands[1]) & !is_var(tigerir.operands[2])) {
						mips = mips + "\tsw $" + tigerir.operands[0] + ", " + tigerir.operands[2] + "($"+ tigerir.operands[1] + ")\n";
				}
				return mips;
		}
		public String aload(IRInstruction tigerir) {
				String mips = "";
				if(is_var(tigerir.operands[1]) & is_var(tigerir.operands[2])) {
						mips = mips + "\tadd $temp, $" + tigerir.operands[1] + ", $" + tigerir.operands[2] + "\n";
						mips = mips + "\tlw $" + tigerir.operands[0] + ", 0($temp)\n";
				} else if (is_var(tigerir.operands[1]) & !is_var(tigerir.operands[2])) {
						mips = mips + "\tlw " + tigerir.operands[0] + ", "+ tigerir.operands[2] +"("+ tigerir.operands[1]+")\n";
				}
				return mips;
		}

		public int par_size_total(IRInstruction ir_ins, boolean has_rv) {
				int size = 0;
				for( IROperand par : ir_ins.operands) {
						//size = size + variable_size(par);
						//size = size + 4; //they are all int
						size = size + 4;
				}
				if(has_rv) { size = size - 4; }
				return size;
		}

		public int var_size_total(String func_name) {
				int size = 0;
				for(IRFunction f : this.program.functions) {
						int temp = f.name.compareTo(func_name);
						if (temp == 0)	{
								for( IRVariableOperand var : f.variables) {
										size  = size + variable_size(var);
								}
								for( IRVariableOperand par : f.parameters) {
										size = size - variable_size(par);
								}
						}
				}
				return size;
		}
		public int variable_size(IRVariableOperand var) {
				int size = 1;
				int element_size = 4; // assume int type, but can add check for float
				if(var.type instanceof IRArrayType) {
						size = ((IRArrayType)var.type).getSize();
						if(this.DEBUG) {
								System.out.println("Name: "+ var);
								System.out.println("Type: "+ var.type);
								System.out.println("Size: "+ size);
						}
				}
				return (element_size * size);
		}
		
		public String store_temps(int max, int sp_offset) {
				String mips = "";
				for(int i = 0; i < max; i++) {
						mips = mips + "\tsw $t" + i + ", " + ((i * 4) + sp_offset) + "($29)\n"; 
				}
				return mips;
		}
		public String load_temps(int max, int sp_offset) {
				String mips = "";
				for(int i = 0; i < max; i++) {
						mips = mips + "\tlw $t" + i + ", " + ((i * 4) + sp_offset) + "($29)\n"; 
				}
				return mips;
		}
		
		public int ra_size() {
				return 4;
		}
		public String store_ra(int sp_offset) {
				String mips = "\tsw $31, "+ sp_offset +"($29)\n";
				return mips;
		}
		public String load_ra(int sp_offset) {
				String mips = "\tlw $31, "+ sp_offset +"($29)\n";
				return mips;
		}

		public int saved_size(int max) {
				int size = 0;
				for (int i = 0; i < max; i++) {
						size  = size + 1;
				}
				return size * 4;
		}
		public String store_saved(int max, int sp_offset) {
				String mips = "";
				for(int i = 0; i < max; i++) {
						mips = mips + "\tsw $s" + i + ", " + (sp_offset + (i * 4)) + "($29)\n"; 
				}
				return mips;
		}
		public String load_saved(int max, int sp_offset) {
				String mips = "";
				for(int i = 0; i < max; i++) {
						mips = mips + "\tlw $s" + i + ", " + (sp_offset + (i * 4)) + "($29)\n"; 
				}
				return mips;
		}

		public String store_params(IRInstruction ins, boolean has_rv) {
				//sp_offset will always be zero during this phase 
				String mips = "";
				int x  = 1;
				if(has_rv) x++;
				for(int i = x; i < ins.operands.length; i++) {
						if(i < 5) {
								if (is_var(ins.operands[i])) {
										mips = mips + "\tsw $"+ins.operands[i] +", "+ (i*4) + "($29)\n";
								} else {
										mips = mips + "\tli $temp, "+ins.operands[i]+"\n";//add lui
										mips = mips + "\tsw $temp, "+ (i*4) + "($29)\n";
								}
						} else {
								if (is_var(ins.operands[i])) {
										mips = mips + "\tsw $"+ins.operands[i] +", "+ (i*4) + "($29)\n";
										mips = mips + "\tmove $a" + i+ ", $" + ins.operands[i] + "\n";
								} else {
										mips = mips + "\tli $t1, $"+ins.operands[i]+"\n";//add lui
										mips = mips + "\tsw $t1, "+ (i*4) + "($29)\n";
										mips = mips + "\tmove $a" + i+ ", $" + ins.operands[i] + "\n";
								}
						}
				}
				return mips;
		}

}
