import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static java.util.Collections.max;

public class ChromaticNumber{
    private static int [] colors;

    public static Integer getIndex(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        if(graph1.getVertexCount() == 1) return 1;

        colors = new int[graph1.getVertexCount()];

        int chromatic_number = 1000;
        for(Vertex start_v : graph1.getVertices())
        {
            Arrays.fill(colors, -1);
            colors[start_v.getIndex()] = 0;
            for(Vertex v : graph1.getVertices())
            {
                if(!v.equals(start_v))
                {
                    colors[v.getIndex()] = same_color(v);
                }
            }
            chromatic_number = Math.min(chromatic_number, maximum(colors) + 1);
        }
        System.out.println(chromatic_number);
        return chromatic_number;
    }

    public static int maximum(int[] arr)
    {
        int max = -1;
        for(int a : arr) if(a > max) max = a;
        return max;
    }

    public static int same_color(Vertex v)
    {
        var edges = v.getEdgeList();
        Stack<Integer> other_colors = new Stack<>();
        check(other_colors, edges, v);

        for(int color = 0; color < max(other_colors) + 2; color++)
        {
            if(!other_colors.contains(color)) {
                return color;
            }
        }
        return 0;
    }

    public static void check(List<Integer> arr, List<AbstractEdge> edges, Vertex v)
    {
        for(var edge : edges)
        {
            arr.add(colors[edge.other(v).getIndex()]);
        }
    }
}
