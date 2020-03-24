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
		RegisterNode node1 = new RegisterNode(1);
		RegisterNode node2 = new RegisterNode(2);
		RegisterNode node3 = new RegisterNode(3);
		RegisterNode node4 = new RegisterNode(4);
		
		node1.addEdge(node2);
		node1.addEdge(node3);
		node2.addEdge(node4);
		node4.addEdge(node3);
		
		this.nodes = new ArrayList<>(Arrays.asList(node1,node2,node3,node4));
		Collections.sort(this.nodes);
		System.out.println("Graph before coloring: "+this);
	}
	
	public RegisterGraph(ArrayList<RegisterNode> nodes) {
		this.nodes = nodes;
		Collections.sort(this.nodes);
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
				if(node.getDegree()>k) return false;
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
}
