import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.Arrays;


public class MinLengthCycle implements GraphCharacteristic {
    private int minLength = 0;
    private boolean[] marked;

    private void dfs(Vertex v, Vertex endV, int length) {
        marked[v.getIndex()] = true;
        int c = 0;
        for (AbstractEdge e : v.getEdgeList()) {
            Vertex w = e.other(v);
            if (w == endV){
                c++;
                if (length>2 || c == 2) {
                    if ((minLength > length) || (minLength == 0)) minLength = length;
                }
            }
            if (!marked[w.getIndex()]) {
                dfs(w, endV, length+1);
                marked[w.getIndex()] = false;
            }
        }
    }

    @Override
    public Integer execute(Graph graph)
    {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        marked = new boolean[graph1.getVertexCount()];

        for ( Vertex v: graph1.getVertices()) {
            Arrays.fill(marked, false);
            dfs(v, v, 1);
        }
        return minLength;
    }
}
