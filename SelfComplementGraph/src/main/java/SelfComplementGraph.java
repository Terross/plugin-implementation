import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class SelfComplementGraph implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        GraphMatrix graphMatrix = new GraphMatrix(graph);
        GraphMatrix complementGraphMatrix = graphMatrix.getComplementGraph();
        return graphMatrix.isIsomorphic(complementGraphMatrix);
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

        public GraphMatrix getComplementGraph() {
            Integer[][] complementMatrix = new Integer[matrix.length][matrix.length];
            for (int row = 0; row < matrix.length; row++) {
                System.arraycopy(matrix[row], 0, complementMatrix[row], 0, matrix.length);
            }
            this.matrix.clone();
            for (int row = 0; row < complementMatrix.length; row++) {
                for (int col = 0; col < complementMatrix.length; col++) {
                    if (complementMatrix[row][col] == 0 && row != col) complementMatrix[row][col] = 1; // Если 0 не на диагонали, то 1
                    else if (complementMatrix[row][col] > 0) complementMatrix[row][col] = 0; // Если 1 или больше (есть ребро), то меняем на ноль
                }
            }
            return new GraphMatrix(complementMatrix);
        }


        public boolean isIsomorphic(GraphMatrix graph) {
            for (int colSwap = 0; colSwap < graph.matrix.length; colSwap++) {
                List<List<Integer>> rows = GraphMatrix.toDoubleList(graph.matrix);
                List<List<Integer>> myRows = GraphMatrix.toDoubleList(matrix, colSwap);
                boolean[] foundPair = new boolean[rows.size()]; // флаги найденных пар


                for (List<Integer> row :
                        rows) {

                    for (List<Integer> myRow :
                            myRows) {
                        if (row.equals(myRow) && !(foundPair[rows.indexOf(row)])) {
                            foundPair[rows.indexOf(row)] = true; // найдена пара
                            myRows.remove(myRow);
                            break;
                        }
                    }
                    if (!foundPair[rows.indexOf(row)]) break;
                }
                if (myRows.isEmpty()) return true;
            }
            return false;
        }



    }
}
