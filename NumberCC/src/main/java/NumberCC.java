import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.Arrays;

public class NumberCC implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        boolean []visited = new boolean[graph1.getVertexCount()];
        Arrays.fill(visited, false);
        int kol = 0;
        for(Vertex v: graph1.getVertices())
        {
            if(!visited[v.getIndex()])
            {
                DFS(v, visited);
                kol++;
            }
        }
        return kol;
    }

    public void DFS(Vertex v, boolean[] visited)
    {
        visited[v.getIndex()] = true;
        for(AbstractEdge edge: v.getEdgeList())
        {
            if(!visited[edge.other(v).getIndex()])
                DFS(edge.other(v), visited);
        }
    }
}
