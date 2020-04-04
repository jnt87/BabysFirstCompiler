package mips;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
	
	//Coloring using Chaiting-Brigs
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
	
	//Replace registers
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
	
	//Get the register name from the dollar character
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
	
	//Return the new name for the register
	public String replaceName(String registerToReplace, ArrayList<String> freeRegisters) {
		for(RegisterNode node:this.nodes) {
			if (node.register.equals("$"+registerToReplace)) {
				if (node.color!=-1) {
					return "$"+freeRegisters.get(node.color);
				}else {
					return "Spill";
				}
			}
		}
		
		return null;
	}
}
