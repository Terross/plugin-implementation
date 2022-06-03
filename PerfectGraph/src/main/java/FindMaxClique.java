import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;

import java.util.Stack;

import static java.util.Collections.max;

public class FindMaxClique {
    public static int Find(Graph graph1)
    {
        UndirectedGraph graph = new UndirectedGraph(graph1);
        int v_count = graph.getVertexCount();
        Stack<Integer> weight = new Stack<Integer>();
        for(Vertex v : graph.getVertices()) weight.add(v.getEdgeList().size());
        int min = findMin(weight);
        while (min != v_count - 1)
        {
            Vertex minV = graph.getVertices().get(weight.indexOf(min));
            weight.set(minV.getIndex(), -1);
            var edges = minV.getEdgeList();
            for(var edge : edges)
            {
                weight.set(edge.other(minV).getIndex(), weight.get(edge.other(minV).getIndex())-1);
            }
            for(int w : weight) System.out.printf(w + " ");
            System.out.println(" ");
            min = findMin(weight);
            v_count--;
        }
        System.out.println("clique = " +  (max(weight) + 1));
        return max(weight) + 1;
    }

    public static int findMin(Stack<Integer> l)
    {
        int min = 1000;
        for(int w : l)
        {
            if(min > w && w > -1) min = w;
        }
        return min;
    }
}
