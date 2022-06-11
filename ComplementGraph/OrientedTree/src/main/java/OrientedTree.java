import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.*;

public class OrientedTree implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        // Получаем орграф
        // Орграф - ориентированное дерево, если только один корень (степень входа = 0), а у остальных степень входа = 1
        Map<UUID, Vertex> vertexes = graph.getVertices();
        List<Edge> edges = graph.getEdges();
        Map<UUID, Integer> degrees = new HashMap<>();
        vertexes.forEach(((vertexId, vertex) -> degrees.put(vertexId, 0))); // инициализация пусто
        for (Edge edge :
                edges) {
            UUID to = edge.getToV();
            degrees.computeIfPresent(to, (vertexId, degree) -> degree++); // Для каждого входа увеличиваем степень
        }
        int rootsCount = 0;
        for (Integer degree :
                degrees.values()) { // проверяем все степени входа
            if (degree == 0) rootsCount++; // ничего не входит - корень
            else if (degree > 1) return false; // входит больше двух - неориентированное дерево
        }
        if (rootsCount != 1) return false;

        return isConnected(graph); // если связен - то дерево, иначе нет

    }

    private static boolean isConnected(Graph graph) { // Проверка на связность
        GraphMatrix graphMatrix = new GraphMatrix(graph);
        boolean[] used = new boolean[graphMatrix.matrix.length];

        DFS(graphMatrix, 0, used);

        for (boolean use :
                used) {
            if (!use) return false;
        }

        return true;
    }

    private static void DFS(GraphMatrix graph, int v, boolean[] used) {
        used[v] = true;
        for (int u = 0; u < graph.matrix.length; ++u) {
            if(graph.matrix[v][u] > 0 && !used[u]) DFS(graph, u, used);
        }
    }

    public static class GraphMatrix {
        private final Integer[][] matrix;


        public GraphMatrix(Graph graph) {
            int vertexCount = graph.getVertexCount();
            List<Edge> edges = graph.getEdges();
            Map<UUID, Vertex> vertexMap = graph.getVertices();
            List<UUID> vertexes = vertexMap.keySet().stream().toList(); // список из всех вершин
            matrix = new Integer[vertexCount][vertexCount];
            for (int row = 0; row < vertexCount; row++) {
                for (int col = 0; col < vertexCount; col++) {
                    matrix[row][col] = 0;
                }
            }
            for (Edge e :
                    edges) {
                UUID from = e.getFromV();
                UUID to = e.getToV();
                int fromIndex = vertexes.indexOf(from);
                int toIndex = vertexes.indexOf(to);
                matrix[fromIndex][toIndex]++;
                matrix[toIndex][fromIndex]++;
            }
        }

        public GraphMatrix(Integer[][] matrix) {
            this.matrix = matrix;
        }

        private static <T> List<List<T>> toDoubleList(T[][] arr) {
            return toDoubleList(arr, 0);
        }

        private static <T> List<List<T>> toDoubleList(T[][] arr, int colSwap) {
            List<List<T>> res = new LinkedList<>();
            for (T[] row :
                    arr) {
                List<T> rowList = new LinkedList<>();
                for (int col = 0; col < arr.length; col++) {
                    T toAdd = row[(col + colSwap) % arr.length];
                    rowList.add(toAdd);
                }
                res.add(rowList);
            }
            return res;
        }
    }


}
