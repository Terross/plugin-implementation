import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.List;
import java.lang.*;

public class PowerSet implements GraphCharacteristic {
    private int vertexCount;

    @Override
    public Integer execute(Graph graph)
    // return format - amount of elements in vertex degree set
    {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        int size = 0;
        vertexCount = graph1.getVertexCount();
        if (vertexCount == 0) return 0;
        if (vertexCount == 1) return 1;

        List<Vertex> vertices = graph1.getVertices();
        int edge_counts[] = new int[graph1.getEdgeCount()+1];
        for (int i =0; i < graph1.getEdgeCount(); i++)
            edge_counts[i] = 0;

        for (int i = 0; i < vertexCount; i++) {
            var v = vertices.get(i);
            var edgesV = v.getEdgeList();
            edge_counts[edgesV.size()] = 1;
        }

        for(int i = 0; i < graph1.getEdgeCount()+1; i++)
        {
            if(edge_counts[i] == 1) {
                System.out.println(i);
                size++;
            }
        }
        return size;
    }
}
