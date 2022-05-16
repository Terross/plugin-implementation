import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

public class GraphWheel implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        if(graph1.getVertexCount() > 3) {
            boolean CenterAlreadyIs = false;
            // Проход по всем вершинам
            for (Vertex v : graph1.getVertices()) {
                int Neighbors = (v.getEdgeList()).size(); // Кол-во соседей
                if ((Neighbors != 3) && (Neighbors != graph1.getVertexCount() - 1)) // У вершины каждого колеса 3 соседа, либо это центр
                    return false;

                if ((graph1.getVertexCount() != 4) && (Neighbors == graph1.getVertexCount() - 1)) { // Если это центральная вершина колеса и это не минимальный граф колесо
                    if (CenterAlreadyIs) // Если центральная вершина уже была
                        return false;
                     else CenterAlreadyIs = true;
                }
                if ((graph1.getVertexCount() == 4) && (Neighbors != 3))  // В минимальном колесе каждая вершина имеет 3х соседей
                    return false;
            }
            return true;
        }
        return false;
    }
}
