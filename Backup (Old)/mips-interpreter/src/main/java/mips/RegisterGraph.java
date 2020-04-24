package mips;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class RegisterGraph {
	public ArrayList<RegisterNode> nodes;
	
	//Hardcoded graph for testing
	public RegisterGraph() {
		RegisterNode node1 = new RegisterNode("$temp0");
		RegisterNode node2 = new RegisterNode("$temp1");
		RegisterNode node3 = new RegisterNode("$temp2");
		
		node1.addEdge(node2);
		//node1.addEdge(node3);
		
		this.nodes = new ArrayList<>(Arrays.asList(node1,node2,node3));
		Collections.sort(this.nodes);
		System.out.println("Graph before coloring: "+this);
	}
	
	public RegisterGraph(ArrayList<RegisterNode> nodes) {
		this.nodes = nodes;
		Collections.sort(this.nodes);
	}
	
	public String hardCodedAssembly() {
		String assembly = new String();
		assembly = assembly + "add $temp1, $x2, $x3\nadd $temp0, $x0, $x1\nmult $temp2, $temp1, $x4\nadd $y, $temp0, $temp2";
		return assembly;
	}
	
	public ArrayList<String> hardCodedFreeRegisters(){
		ArrayList<String> output = new ArrayList<>();
		output.add("t0");
		output.add("t1");
		output.add("t2");
		output.add("t3");
		return output;
	}
	
	public void addNewNode(RegisterNode newNode) {
		this.nodes.add(newNode);
	}
	
	public String toString() {
		return this.nodes.toString();
	}
	
	/**
	 * Coloring algorithm that changes the color of the nodes
	 * 
	 * @param k 	Number of colors available
	 * @return		Boolean indicating if coloring was done without spilling
	 */
	public boolean color(int k) {
		Stack<RegisterNode> stack = new Stack<>();
		for (RegisterNode node:this.nodes) {
			if(node.visited==false) {
				//if(node.getDegree()>k) return false;
				stack.push(node);
				node.visited = true;
			}
		}
		
		while(!stack.empty()) {
			RegisterNode node = stack.pop();
			int availableColor = node.getSmallestAvailableColor(k);
			if(availableColor == -1) return false;
			node.color = availableColor;
		}
		return false;
	}
	
	/**
	 * Generates the assembly after replacing the virtual registers by physical ones
	 * 
	 * @param assembly 			String of the assembly code
	 * @param freeRegisters		List of free physical registers
	 * @return					String with the assembly after register allocation
	 */
	public String replaceRegisters(String assembly, ArrayList<String> freeRegisters) {
		String newAssembly = new String();
		for (int ii = 0; ii<assembly.length(); ii++) {
			if(assembly.charAt(ii)=='$') {
				String registerName = getRegisterName(assembly,ii);
				String newRegisterName = replaceName(registerName,freeRegisters);
				if (newRegisterName!=null) {
					newAssembly = newAssembly + newRegisterName;
					ii = ii + registerName.length();
				}else {
					newAssembly = newAssembly + '$';
				}
			}else {
				newAssembly = newAssembly + assembly.charAt(ii);
			}
		}
		return newAssembly;
	}
	
	/**
	 * Gets the name of a register from the position of the '$' that precedes it 
	 * 
	 * @param assembly 		String of the assembly code
	 * @param dollarIndex 	Index representing the position of the dollar sign
	 * @return 				String containing the name of the register
	 */
	public String getRegisterName(String assembly, int dollarIndex) {
		String registerName = new String();
		int i = dollarIndex+1;
		while(true) {
			if(i==assembly.length()) break;
			if(assembly.charAt(i)!='\n' && assembly.charAt(i)!='\t' && assembly.charAt(i)!=',' && assembly.charAt(i)!=' ') {
				registerName = registerName+assembly.charAt(i);
				i++;
			}else {
				break;
			}
		}
		return registerName;
	}
	
	
	/**
	 * Calculates the name of the physical register associated with a virtual one once coloring finished
	 * 
	 * @param registerToReplace	String without '$' that has to be replaced
	 * @param freeRegisters		List of free physical registers
	 * @return 					String with the name of the physical register
	 */
	public String replaceName(String registerToReplace, ArrayList<String> freeRegisters) {
		for(RegisterNode node:this.nodes) {
			if (node.register.equals("$"+registerToReplace)) {
				if (node.color!=-1) {
					System.out.println("node: "+node);
					System.out.println("Free registers: "+freeRegisters);
					return "$"+freeRegisters.get(node.color);
				}else {
					return "Spill";
				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * Gets the virtual registers that need to be spilled after coloring
	 * 
	 * @return		List of registers that need to be spilled
	 */
	public ArrayList<String> registersToSpill(){
		ArrayList<String> returnList = new ArrayList();
		for (RegisterNode node:this.nodes) {
			if (node.color == -1) returnList.add(node.register);
		}
		return returnList;
	}
	
	
	/**
	 * Gets hashmap of the virtual registers as key and their corresponding physical one
	 * 
	 * @param freeRegisters		List of free registers
	 * @return					Hashmap with the assignment
	 */
	public HashMap<String,String> correspondingPhysicalRegisters(ArrayList freeRegisters){
		HashMap<String,String> returnHashMap = new HashMap<String,String>();
		
		for (RegisterNode node:this.nodes) {
			if (node.color != -1) returnHashMap.put(node.register, freeRegisters.get(node.color).toString());
		}
		
		return returnHashMap;
	}
}
