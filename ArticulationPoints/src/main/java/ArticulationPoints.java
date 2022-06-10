import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.AbstractGraph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import java.util.ArrayList;
import java.util.List;

public class ArticulationPoints implements GraphCharacteristic {
    private int count;
    private int time;
    private boolean[] visited;
    private int[] up;
    private int[] tin;
    private List<ArrayList<Integer>> obj;
    private ArrayList<Integer> res;

    @Override
    public Integer execute(Graph graph) {
        AbstractGraph abstractGraph = new UndirectedGraph(graph);
        count = 0;
        int vertexCount = abstractGraph.getVertexCount();
        visited = new boolean[vertexCount];
        up = new int[vertexCount];
        tin = new int[vertexCount];

        res = new ArrayList<>();
        adjList(abstractGraph);
        for (Vertex v: abstractGraph.getVertices()){
            if (!visited[v.getIndex()]){
                dfs(v.getIndex(), -1);
            }
        }
        return count;
    }
    private void dfs(int v, int p){
        tin[v] = up[v] = ++time;
        visited[v] = true;
        int k = 0;
        for (Integer i : obj.get(v)){
            if (i == p) continue;
            if (visited[i]) up[v] = Math.min(up[v], tin[i]);
            else{
                dfs(i, v);
                k++;
                up[v] = Math.min(up[v], up[i]);
                if (p != -1 && up[i] >= tin[v]) {
                    if (!res.contains(v)){
                        count++;
                        res.add(v);
                    }
                }
            }
        }
        if (p == -1 && k >= 2) {
            if (!res.contains(v)){
                count++;
                res.add(v);
            }
        }
    }

    private void adjList(AbstractGraph abstractGraph){
        int vertexCount = abstractGraph.getVertexCount();
        obj = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            obj.add(new ArrayList<>());
        }
        for (Vertex v: abstractGraph.getVertices()){
            for (AbstractEdge e: v.getEdgeList()){
                Vertex cur = e.other(v);
                int curIndex = cur.getIndex();
                int ver = v.getIndex();
                ArrayList<Integer> v1 = obj.get(ver);
                ArrayList<Integer> v2 = obj.get(curIndex);
                v1.add(curIndex);
                v2.add(ver);
            }
        }
    }
}