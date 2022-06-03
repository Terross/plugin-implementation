import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.*;
import static java.util.Collections.max;
import static java.util.Collections.swap;

public class ChromaticIndex implements GraphCharacteristic {
    private int [] colors;
    private Stack<AbstractEdge> edges = new Stack<>();
    @Override
    public Integer execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        if(graph1.getVertexCount() % 2 == 0 && graph1.getEdgeCount() == graph1.getVertexCount()*(graph1.getVertexCount()-1)/2) return graph1.getVertexCount()-1;
        if(graph1.getVertexCount() == 1) return 0;

        colors = new int[graph1.getEdgeCount()];

        for(var v : graph1.getVertices())
        {
            var v_edges = v.getEdgeList();
            for(var edge : v_edges)
            {
                if(!edges.contains(edge))
                    edges.add(edge);
            }
        }

        Random rand = new Random();


        int chromatic_number = 1000;

        for(int j = 0; j < edges.size(); j++) {
            for (var start_edge : edges) {
                Arrays.fill(colors, -1);
                colors[edges.indexOf(start_edge)] = 0;
                for (var edge : edges) {
                    if (!edge.equals(start_edge))
                        colors[edges.indexOf(edge)] = same_color(edge);
                }
                chromatic_number = Math.min(chromatic_number, maximum(colors) + 1);
                for (int i = 0; i < edges.size(); i++) {
                    int randi = rand.nextInt(edges.size());
                    var temp = edges.get(i);
                    edges.set(i, edges.get(randi));
                    edges.set(randi, temp);
                }
            }
        }
        return chromatic_number;
    }

    public int maximum(int[] arr)
    {
        int max = -1;
        for(int a : arr) if(a > max) max = a;
        return max;
    }

    public int same_color(AbstractEdge edge)
    {
        Vertex v1 = edge.getV();
        Vertex v2 = edge.getW();
        Stack<Integer> other_colors = new Stack<>();
        check(other_colors, v1);
        check(other_colors, v2);

        for(int color = 0; color < max(other_colors) + 2; color++)
        {
            if(!other_colors.contains(color)) {
                return color;
            }
        }
        return 0;
    }

    public void check(List<Integer> arr, Vertex v)
    {
        for(var edge : v.getEdgeList())
        {
            if(!arr.contains(colors[edges.indexOf(edge)]))
                arr.add(colors[edges.indexOf(edge)]);
        }
    }
}
