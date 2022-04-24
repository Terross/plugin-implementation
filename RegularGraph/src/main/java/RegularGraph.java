import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegularGraph implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        Map<UUID, Integer> vertexesDegree = new HashMap<>();

        for (Edge edge : graph.getEdges()) {
            UUID vertex = edge.getFromV();
            Integer degree = vertexesDegree.get(vertex);
            if (degree != null) {
                vertexesDegree.put(vertex, ++degree);
            } else {
                vertexesDegree.put(vertex, 1);
            }
        }
        Integer vertexDegree = vertexesDegree.get(graph.getEdges().get(0).getFromV());
        for (Integer degree: vertexesDegree.values()) {
            if (!degree.equals(vertexDegree)) {
                return false;
            }
        }

        return true;
    }
}
