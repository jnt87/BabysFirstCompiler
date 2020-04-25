import java.util.Set;
import java.util.HashSet;

public class MIPSNode {
    public Set<MIPSNode> dependencies = new HashSet<>();
    public String registerName;

    public MIPSNode(String name) {
        this.registerName = name;
    }

    public MIPSNode(String name, Set<MIPSNode> dependencies) {
        this.registerName = name;
        this.dependencies = dependencies;
    }

    public String toString() {
        String returnVal = "\nMIPSNode Dependencies for " + registerName + " and " + dependencies.size() + "\n";

        for (MIPSNode depend : dependencies) {
            returnVal += depend.registerName + "\t";
        }
        return returnVal;
    }
}