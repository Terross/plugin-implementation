import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.HashSet;

public class OrientedTournament implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        var vertexList = graph1.getVertices();
        var vertexCount = graph1.getVertexCount();
        if(graph1.getEdgeCount() != vertexCount*(vertexCount-1)/2) return false;
        for(int i = 0; i < vertexCount; i++)
        {
            var tmpVertex = vertexList.get(i);
            var edgeList = tmpVertex.getEdgeList();
            var adjVertex = new HashSet<Integer>();
            for(int j = 0; j < edgeList.size(); j++){
                var finalVertex = edgeList.get(j).getV();
                if (edgeList.get(j).getV().getIndex() == tmpVertex.getIndex()) finalVertex = edgeList.get(j).getW();
                adjVertex.add(finalVertex.getIndex());
            }
            if (adjVertex.size() != vertexCount - 1) return false;
        }
        return true;
    }
}