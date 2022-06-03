import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

public class TriangulatedGraph implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        if (graph1.getVertexCount() <= 2) return false;
        boolean T;
        for (Vertex v1 : graph1.getVertices())
        {
            var edges1 = v1.getEdgeList();
            for(var edge1 : edges1)
            {
                Vertex v2 = edge1.other(v1);
                var edges2 = v2.getEdgeList();
                T = false;
                for(var edge2 : edges2)
                {
                    if (!edge1.equals(edge2))
                    {
                        Vertex v3 = edge2.other(v2);
                        var edges3 = v3.getEdgeList();
                        for (var edge3 : edges3)
                        {
                            if (!edge3.equals(edge2))
                                if (edge3.other(v3).equals(v1)) T = true;
                        }
                    }
                }
                if(!T) return false;
            }
        }
        return graph1.getEdgeCount() == 3*graph1.getVertexCount() - 6;
    }
}
