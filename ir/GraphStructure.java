package ir;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class GraphStructure {
    public IRNode root;

    /**
     * Temporary constructor that takes in input to generate the graph
     * Remove if adding this to IRCFG
     */
    public GraphStructure () {
        //CURRENTLY EMPTY
    }

    public GraphStructure (IRNode root) {
        this.root = root;
    }

    /**
     * dfs traversal if you just want to traverse from the initial root/start of the program
     * @return The list of all blocks that were reached by the program
     */
    public List<IRNode> dfs () {
        return dfs(this.root);
    }

    /**
     * dfs traversal if you want to find the dfs algo starting from a specific node
     * @param start The starting node to begin the dfs algorithm
     * @return The list of all blocks reached by the program starting at IRNode start
     */
    public List<IRNode> dfs (IRNode start) {
        if (start == null) {
            throw new IllegalArgumentException("Attempting to traverse the graph that contains a null node");
        }
        Set<IRNode> visitedSet = new HashSet<>();
        List<IRNode> visitedList = new LinkedList<>();
        dfsHelper(start, visitedSet, visitedList);
        return visitedList;
    }

    /**
     * Private helper method to help recursively execute the dfs algoritm
     * @param curr The current node being recursed on
     * @param visitedSet The set of all nodes already visited by the program
     * @param visitedList The list of all nodes that the program has already traversed through
     */
    private void dfsHelper(IRNode curr, Set<IRNode> visitedSet, List<IRNode> visitedList) {
        visitedList.add(curr);
        visitedSet.add(curr);

        if (curr.edgeList != null) {
            for (IRNode child : curr.edgeList) {
                if (!visitedSet.contains(child)) {
                    dfsHelper(child, visitedSet, visitedList);
                }
            }
        }
    }

    /**
     * Getter method returns the root of the graph algorithm
     * @return root IRNode of the GraphStructure
     */
    public IRNode getRoot() {
        return this.root;
    }
}