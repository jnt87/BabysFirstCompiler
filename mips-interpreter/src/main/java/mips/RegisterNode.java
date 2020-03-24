package mips;

import java.util.ArrayList;

public class RegisterNode implements Comparable{
	
	public int registerNumber;
	public int color = -1; //Set the color to uncolored
	public ArrayList<RegisterNode> adjacentNodes;
	public boolean visited;
	
	public RegisterNode(int registerNumber) {
		this.registerNumber = registerNumber;
		this.adjacentNodes = new ArrayList<>();
		this.visited = false;
	}
	
	public void addEdge(RegisterNode adjacentNode) {
		this.adjacentNodes.add(adjacentNode);
		adjacentNode.adjacentNodes.add(this);
	}
	
	public int getDegree() {
		int degree = 0;
		for (RegisterNode node:this.adjacentNodes) {
			if(node.visited==false) degree++; 
		}
		return degree;
	}
	
	public ArrayList<Integer> getAdjacentColors(){
		ArrayList<Integer> colors = new ArrayList<>();
		for (RegisterNode node:this.adjacentNodes) {
			if(node.color!=-1) colors.add(node.color);
		}
		return colors;
	}
	
	public int getSmallestAvailableColor(int k) {
		for (int ii = 0; ii<k; ii++) {
			if (!this.getAdjacentColors().contains(ii)) return ii;
		}
		return -1;
	}
	
	public String toString() {
		return this.registerNumber+" - "+this.color;
	}

	@Override
	public int compareTo(Object o) {
		return ((RegisterNode)o).getDegree()-this.getDegree();
	}
	
}
