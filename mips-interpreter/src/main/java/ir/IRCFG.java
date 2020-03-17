package ir;

import ir.datatype.*;
import ir.operand.*;
import ir.IRInstruction.OpCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

//File for creating and dealing with Control Flow Graphs
// Please see page 241 of the book for  psuedo code
public class IRCFG extends GraphStructure {
    //debug flag
    public final Boolean DEBUG = true;
    //list of the leaders of blocks - where should this be implemented?
    public IRProgram program;
    public List<IRInstruction> leaders;
    public List<IRInstruction> last;
    public ArrayList<ArrayList<ArrayList<IRInstruction>>> blocks;
    
    public ArrayList<IRNode> rootList;

    //find the leaders of the blocks
    public void IRFindLeaders() {
        // adds the first instruction as a leader
        boolean is_leader = true;
        for (IRFunction function : this.program.functions) {
            is_leader = true;
            for(IRInstruction instruction : function.instructions) {
                if(instruction.opCode == IRInstruction.OpCode.LABEL || is_leader) {
                    //add leaders to the list
                    this.leaders.add(instruction);
                    is_leader = false;
                } 
                switch(instruction.opCode) {
                    case BREQ: 
                    case BRNEQ:
                    case BRLT:
                    case BRGT: 
                    case BRLEQ:
                    case BRGEQ:
                    case GOTO:
                        is_leader = true;
                        break;	
                }
            }
        }
        if(DEBUG) { //if debug the print the number of leaders found
            System.out.print("Number of leaders: ");
            System.out.println(this.leaders.size());
        }
    }

    public IRCFG(IRProgram program) {
            this.program = program;
            DeadCode.removeUnusedVar(program);
            this.leaders = new ArrayList<IRInstruction>();
            this.last = new ArrayList<IRInstruction>();
            this.blocks = new ArrayList<ArrayList<ArrayList<IRInstruction>>>();
            IRFindLeaders();
            IRFindLast();
            ConstructCFG();
    }

		public void IRFindLast() {
				if(DEBUG) System.out.println("Entering IRFindLast()...");
				boolean not_leader = false;
				int k = 1; // used to index into instructions for separate function
				boolean k_lt_size = true;
				int instruction_count = 0;
				boolean leader_in_func = true;
				for( IRFunction func : this.program.functions ) {
						k = 1;
						ArrayList<ArrayList<IRInstruction>> function = new ArrayList<ArrayList<IRInstruction>>();
						instruction_count = func.instructions.size();
						for(IRInstruction lead : this.leaders) {
								leader_in_func = func.instructions.contains(lead);
								if(leader_in_func) {
										ArrayList<IRInstruction> block = new ArrayList<IRInstruction>();
										block.add(lead);
										k_lt_size = k < instruction_count;
										if(k_lt_size) not_leader = !this.leaders.contains(func.instructions.get(k));
										if(k_lt_size && not_leader && leader_in_func) do {
												block.add(func.instructions.get(k));
												k++;
												k_lt_size = k < instruction_count;
												if(k_lt_size) not_leader = !this.leaders.contains(func.instructions.get(k));
										} while (k_lt_size && !this.leaders.contains(func.instructions.get(k)) && leader_in_func);
										this.last.add(func.instructions.get(k-1));
										k++;
										function.add(block);
								}
						}
						this.blocks.add(function);
				}
		}

		public void ConstructCFG() {
			ArrayList<IRNode> nodes = new ArrayList<>();
      ArrayList<IRNode> roots = new ArrayList<>();
      int index = 0;
			for (ArrayList<ArrayList<IRInstruction>> functions : this.blocks) {
				boolean flag = false;
				for (ArrayList<IRInstruction> blocks : functions) {
					IRNode creationNode = new IRNode();
					creationNode.instructions = blocks;
					creationNode.function = index;
					nodes.add(creationNode);
					if(flag==false) {
						flag = true;
						roots.add(creationNode);
					}
				}
				index++;
			}
			
			//For merging the nodes after creating them
			for (IRNode node : nodes) {
				//System.out.println(node.getLastInstruction().opCode);
				//System.out.println("Last Instruction: "+ node.instructions.get(node.instructions.size()-1));
				switch(node.instructions.get(node.instructions.size()-1).opCode) {
					case GOTO:
						IROperand nextBlockLabel = node.instructions.get(node.instructions.size()-1).operands[0];
						for (IRNode goNode : nodes) {
							if(goNode.instructions.get(0).operands[0].toString().equals(nextBlockLabel.toString())) {
								node.edgeList.add(goNode);
								goNode.parentNode.add(node);
							}
						}
						break;
					case BREQ: 
					case BRNEQ:
					case BRLT:
					case BRGT: 
					case BRLEQ:
					case BRGEQ:
						IROperand nextBlockLabel1 = node.instructions.get(node.instructions.size()-1).operands[0];
						for (IRNode goNode : nodes) {
							int line = node.instructions.get(node.instructions.size()-1).irLineNumber+1;
							if(goNode.instructions.get(0).operands[0].toString().equals(nextBlockLabel1.toString())) {
								node.edgeList.add(goNode);
								goNode.parentNode.add(node);
							}
							if(goNode.instructions.get(0).irLineNumber==line) {
								node.edgeList.add(goNode);
								goNode.parentNode.add(node);
							}
						}
				}
				this.rootList = roots;
				
			}
			
			for (IRNode node : nodes) {
				if (node.instructions.get(0).opCode.equals(IRInstruction.OpCode.LABEL)) {
					int numInstruction = node.instructions.get(0).irLineNumber-1;
					for (IRNode possibleParent : nodes) {
						if (possibleParent.instructions.get(possibleParent.instructions.size()-1).irLineNumber == numInstruction && possibleParent.function==node.function) {
							switch(possibleParent.instructions.get(possibleParent.instructions.size()-1).opCode) {
							case BREQ: 
							case BRNEQ:
							case BRLT:
							case BRGT: 
							case BRLEQ:
							case BRGEQ:
							case GOTO:
								break;
							default:
								node.parentNode.add(possibleParent);
								possibleParent.edgeList.add(node);
							}
						}
					}
				}
			}
						
			
		}
		
    public ArrayList<IRNode> hardCodeCFG() {
        ArrayList<IRNode> list = new ArrayList<>();
        ArrayList<ArrayList<IRInstruction>> function1 = blocks.get(0);
        ArrayList<ArrayList<IRInstruction>> function2 = blocks.get(1);

        IRNode root = new IRNode();
        root.instructions = function1.get(0);
        IRNode child1 = new IRNode();
        child1.instructions = function1.get(1);
        child1.parentNode.add(root);
        IRNode child2 = new IRNode();
        child2.instructions = function1.get(2);
        child2.parentNode.add(root);
        root.edgeList.add(child1);
        root.edgeList.add(child2);
        IRNode child3 = new IRNode();
        child3.instructions = function1.get(3);
        child3.parentNode.add(child1);
        child3.parentNode.add(child2);
        child1.edgeList.add(child3);
        child2.edgeList.add(child3);
        list.add(root);


        IRNode root2 = new IRNode();
        root2.instructions = function2.get(0);
        list.add(root2);

        return list;
    }


    /**
     * Curr is the the point from which you want to find the EBB from
     * This algo assumes that the passed in node is guarenteed to be a 
     * part of the EBB
     * @param curr
     * @return
     */
    public ArrayList<IRNode> EBBFinder(IRNode curr) {
        ArrayList<IRNode> visited = new ArrayList<>();
        IRNode root = new IRNode(curr);
        EBBHelper(root, visited);

        
        ArrayList<IRNode> paths = new ArrayList<>();
        generatePaths(root, new IRNode(), paths);
        // for (IRNode path_node : paths) {
        //     System.out.println("-----------------DISPLAYING PATH----------------");
        //     displayNode(path_node, visited);
        //     System.out.println("---------------- DISPLAY EBB FINISHED --------------------");
        // }
        
        return paths;
    }

    private void EBBHelper(IRNode curr, ArrayList<IRNode> visited) {
        
        ArrayList<IRNode> edgeList = new ArrayList<>(curr.edgeList);
        curr.edgeList.clear();
        for (IRNode child : edgeList) {
            if (child.parentNode.size() < 2) {
                // System.out.println(child.parentNode.size());
                // System.out.println(child.instructions);
                curr.edgeList.add(child);
                EBBHelper(child, visited);
            }
        }
    }

    private void generatePaths(IRNode curr, IRNode currPath, ArrayList<IRNode> paths) {
        if (curr.edgeList.size() == 0) {
            for (IRInstruction instruction : curr.instructions) {
                currPath.instructions.add(instruction);
            }
            paths.add(currPath);
        } else {
            for (IRInstruction instruction : curr.instructions) {
                currPath.instructions.add(instruction);
            }
            for (IRNode child : curr.edgeList) {
                generatePaths(child, new IRNode(currPath), paths);
            }
        }
    }

    private void displayNode(IRNode curr, ArrayList<IRNode> visited) {
        if (!visited.contains(curr)) {
            visited.add(curr);
            System.out.println(curr);
            for (IRNode child : curr.edgeList) {
                displayNode(child, visited);
            }
        }
    }
}