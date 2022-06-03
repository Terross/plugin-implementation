import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

public class PerfectGraph implements GraphProperty {
    public boolean execute(Graph graph)
    {
        return ChromaticNumber.getIndex(graph) == FindMaxClique.Find(graph);
    }
}
