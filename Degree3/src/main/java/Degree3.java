import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.*;

public class Degree3 implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        Map<UUID, Integer> vertexesDegree = new HashMap<>();
        int result = 0;
        var edges = graph.getEdges();
        List<Edge> newEdges = new ArrayList<>();

        for (Edge edge: edges) {
            newEdges.add(new Edge(edge.getToV(), edge.getFromV(), edge.getColor(), edge.getWeight(), edge.getLabel()));
        }
        edges.addAll(newEdges);

        for (Edge edge : edges) {
            UUID vertex = edge.getFromV();
            Integer degree = vertexesDegree.get(vertex);
            if (degree != null) {
                vertexesDegree.put(vertex, ++degree);
            } else {
                vertexesDegree.put(vertex, 1);
            }
        }

        for (Integer degree: vertexesDegree.values()) {
            if (degree == 3) {
                result++;
            }
        }
        return result;
    }
}
