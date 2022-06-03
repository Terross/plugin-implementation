import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.Arrays;

public class DegreeSequence implements GraphCharacteristic {
    public Integer execute(Graph graph)
    {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        int[] degrees = new int[graph1.getEdgeCount()+1];
        Arrays.fill(degrees, 0);
        for(Vertex v: graph1.getVertices())
        {
            degrees[v.getEdgeList().size()]++;
        }
        int max = 0;
        for(int d:degrees)
            if(d!=0) max=d;

        return max;//возвращает кол-во максимальных элементов в последовательности
    }
}
