import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.AbstractGraph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.List;
import java.util.Objects;

public class DominantPeak implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        List<Vertex> vertices = graph1.getVertices();
        for (int i = 0; i < graph1.getVertexCount(); i++)
        {
            int count = 0;
            Vertex vertex = vertices.get(i);
            for (int j = 0; j < graph1.getVertexCount(); j++)
            {
                int key = 0;
                Vertex v = vertices.get(j);
                if (!Objects.equals(v.getIndex(), vertex.getIndex()))
                {
                    for (AbstractEdge abstractEdge: vertex.getEdgeList()) {
                        if (Objects.equals(v.getIndex(), abstractEdge.getV().getIndex()) || Objects.equals(v.getIndex(), abstractEdge.getW().getIndex()))
                            key = 1;
                    }
                }
                if (key == 1) count++;
            }
            if (count == graph1.getVertexCount() - 1) return true;
        }
        return false;
    }
}
