import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractGraph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.List;


public class VertexEvenCount implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        UndirectedGraph undirGraph = new UndirectedGraph(graph);
        int count = 0;
        int c = 0;
        List<Vertex> vertices = undirGraph.getVertices();
        for(int i = 0; i < graph.getVertexCount(); i++)
        {
            Vertex vertex = vertices.get(i);
            c = vertex.getEdgeList().size();
            if(c % 2 == 0)
                count += 1;
        }
        return count;
    }
}

