import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

public class VertexesDegreesSum implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        return graph.getEdgeCount() * 2;
    }
}
