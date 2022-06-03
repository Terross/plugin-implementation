import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.Arrays;

public class HamiltonNeorGraph implements GraphProperty {
    int v_count;
    int[] used;
    Vertex start;
    boolean OK = false;
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        v_count = graph.getVertexCount();
        if(v_count == 1) return true;
        used = new int[v_count];
        start = graph1.getVertices().get(0);
        Arrays.fill(used, 0);
        DFS(start, 1, true);
        return OK;
    }

    public void DFS(Vertex v, int num, boolean first)
    {
        if(v.equals(start) && !first && num - 1 == v_count) { OK = true; used[v.getIndex()] = 1;}
        else if(v.equals(start) && !first && num != v_count) {used[v.getIndex()] = 0; return;}
        var edges = v.getEdgeList();
        for(var e : edges)
        {
            if(used[e.other(v).getIndex()] == 0) {
                used[e.other(v).getIndex()] = 1;
                DFS(e.other(v), num + 1, false);
            }
        }
        used[v.getIndex()] = 0;
    }
}
