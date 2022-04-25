import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.*;

public class RegularGraph implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        if (graph.getVertexCount() == 0 || graph.getEdgeCount() == 0) {
            return false;
        }

        Map<UUID, Integer> vertexesDegree = new HashMap<>();

        for (Vertex vertex: graph.getVertices().values()) {
            vertexesDegree.put(vertex.getId(), 0);
        }

        var edges = graph.getEdges();
        List<Edge> newEdges = new ArrayList<>();

        for (Edge edge: edges) {
            newEdges.add(new Edge(edge.getToV(), edge.getFromV(), edge.getColor(), edge.getWeight(), edge.getLabel()));
        }
        edges.addAll(newEdges);

        for (Edge edge : edges) {
            UUID vertex = edge.getFromV();
            Integer degree = vertexesDegree.get(vertex);
            vertexesDegree.put(vertex, ++degree);
        }
        Integer vertexDegree = vertexesDegree.get(edges.get(0).getFromV());
        for (Integer degree: vertexesDegree.values()) {
            if (!degree.equals(vertexDegree)) {
                return false;
            }
        }

        return true;
    }
}
