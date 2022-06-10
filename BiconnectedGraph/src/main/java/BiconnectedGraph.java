import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Math.min;


public class BiconnectedGraph implements GraphProperty {
    private int[] tIn; // Время захода в вершину
    private int[] fUp; // Функция времени поднятия вверх
    private int timer;
    private int vertexCount;
    private int bridgeCounter = 0;
    //если время поднятия вверх от u до корня > время захода в вершину v -->  (v,u) = мост

    @Override
    public boolean execute(Graph graph) {
        GraphAdjMatrix graphMatrix = new GraphAdjMatrix(graph); // Если есть хоть один мост - то граф не двусвязный
        vertexCount = graphMatrix.matrix.length;
        boolean[] used = new boolean[vertexCount];
        tIn = new int[vertexCount];
        fUp = new int[vertexCount];
        timer = 0;

        DFS(graphMatrix, 0, -1, used);


        for (boolean use :
                used) {
            if (!use) return false; //Несвязный граф
        }
        return bridgeCounter == 0; //Есть мост => не двусвязный
    }

    private void DFS(GraphAdjMatrix graphMatrix , int v, int parent, boolean[] used) {
        used[v] = true;
        tIn[v] = fUp[v] = timer++;
        for (int i = 0; i < vertexCount; i++) {
            int to = graphMatrix.matrix[v][i];
            if (to == parent) continue; // Если вернулись в родителя
            if (used[to]) // Если уже посещали to
                fUp[v] = min(fUp[v], tIn[to]); // Находим функцию подъема к корню
            else {
                DFS(graphMatrix, to, v, used);
                fUp[v] = min(fUp[v], fUp[to]); // проверяем не стало ли меньше время подъема благодаря to
                if (fUp[to] > tIn[v]) bridgeCounter++; //Найден мост
            }
        }
    }

    public static class GraphAdjMatrix {
        private final Integer[][] matrix;


        public GraphAdjMatrix(Graph graph) {
            int vertexCount = graph.getVertexCount();
            int edgeCount = graph.getEdgeCount();
            List<Edge> edges = graph.getEdges();
            Map<UUID, Vertex> vertexMap = graph.getVertices();
            List<UUID> vertexes = vertexMap.keySet().stream().toList(); // список из всех вершин
            matrix = new Integer[vertexCount][edgeCount];
            for (int row = 0; row < vertexCount; row++) {
                for (int col = 0; col < vertexCount; col++) {
                    matrix[row][col] = 0;
                }
            }
            for (int edgeIndex = 0; edgeIndex < edgeCount; ++edgeIndex) {
                Edge e = edges.get(edgeIndex);
                UUID from = e.getFromV();
                UUID to = e.getToV();
                int fromIndex = vertexes.indexOf(from);
                int toIndex = vertexes.indexOf(to);
                matrix[fromIndex][edgeIndex] = toIndex;
                matrix[toIndex][edgeIndex] = fromIndex;


            }
        }


    }
}
