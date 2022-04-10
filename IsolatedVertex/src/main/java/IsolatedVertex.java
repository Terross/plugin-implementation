import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

public class IsolatedVertex implements GraphProperty {

    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph abstractGraph = new UndirectedGraph(graph);
        for(var vertex: abstractGraph.getVertices()){
            if(vertex.getEdgeList().isEmpty())
                return true;
        }
        return false;
    }
}
