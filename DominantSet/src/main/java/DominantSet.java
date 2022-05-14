import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.Arrays;
import java.util.List;

public class DominantSet implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        List<Vertex> vertexList = graph1.getVertices();
        Boolean[] boolFlag = new Boolean[graph1.getVertexCount()];
        Arrays.fill(boolFlag, Boolean.FALSE);

        for (Vertex vertex : vertexList) {
            if (vertex.getColor() != Color.red) {
                List<AbstractEdge> edgeList = vertex.getEdgeList();
                for (AbstractEdge edge : edgeList) {
                    if (edge.other(vertex).getColor() == Color.red)
                        boolFlag[vertex.getIndex()] = true;
                }
                if (!boolFlag[vertex.getIndex()])
                    break;
            }
        }

        for (int i = 0; i < boolFlag.length; i++) {
            if (vertexList.get(i).getColor() == Color.gray && !boolFlag[vertexList.get(i).getIndex()])
                    return false;
        }
        return true;
    }
}