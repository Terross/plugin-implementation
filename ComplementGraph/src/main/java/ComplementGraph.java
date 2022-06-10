import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.*;

public class ComplementGraph implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        Map<UUID, Vertex> vertices = graph.getVertices();
        Map<UUID, Vertex> origVertices = new HashMap<>();
        Map<UUID, Vertex> mayBeComplVertices = new HashMap<>();

        List<Edge> edges = graph.getEdges();
        List<Edge> origEdges = new ArrayList<>();
        List<Edge> mayBeComplEdges = new ArrayList<>();

        edges.forEach(edge -> {
            if (
                    vertices
                    .get(edge.getFromV())
                    .getColor().equals(Color.red)
            ) origEdges.add(edge);
            else mayBeComplEdges.add(edge);
        });


        vertices.forEach((key, value) -> {
            if (value.getColor().equals(Color.red)) origVertices.put(key, value);
            else mayBeComplVertices.put(key, value);
        });

        Graph original = new Graph(
                graph.getDirectType(),
                origVertices.size(),
                origEdges.size(),
                origVertices,
                origEdges
        );

        Graph mayBeComplement = new Graph(
                graph.getDirectType(),
                mayBeComplVertices.size(),
                mayBeComplEdges.size(),
                mayBeComplVertices,
                mayBeComplEdges
        );

        return execute(original, mayBeComplement);
    }

    public boolean execute(Graph original, Graph graph) {
        GraphMatrix originalGM = new GraphMatrix(original);
        GraphMatrix complementGM = originalGM.getComplementGraph();
        GraphMatrix mayBeComplGM = new GraphMatrix(graph);

        return complementGM.isIsomorphic(mayBeComplGM);
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
            Integer[][] complementMatrix = clone(matrix);

            for (int row = 0; row < complementMatrix.length; row++) {
                for (int col = 0; col < complementMatrix.length; col++) {
                    if (complementMatrix[row][col] == 0 && row != col)
                        complementMatrix[row][col] = 1; // Если 0 не на диагонали, то 1
                    else if (complementMatrix[row][col] > 0)
                        complementMatrix[row][col] = 0; // Если 1 или больше (есть ребро), то меняем на ноль
                }
            }
            return new GraphMatrix(complementMatrix);
        }


        public boolean isIsomorphic(GraphMatrix graph) {
            List<Integer[][]> matrixShuffles = getMatrixShuffles(matrix);
            for (Integer[][] matrix : matrixShuffles) { //Цикл по перестановкам столбцов
                List<List<Integer>> rows = GraphMatrix.toDoubleList(graph.matrix);
                List<List<Integer>> myRows = GraphMatrix.toDoubleList(matrix);
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

        private static Integer[][] clone(Integer[][] matrix) {
            Integer[][] matrixClone = new Integer[matrix.length][matrix[0].length];
            for (int row = 0; row < matrix.length; row++) {
                System.arraycopy(matrix[row], 0, matrixClone[row], 0, matrix[0].length);
            }
            return matrixClone;
        }


        private List<Integer[][]> getMatrixShuffles(Integer[][] matrix) {
            List<Integer[][]> matrixShuffles = new ArrayList<>();

            for (int toSwap1 = 0; toSwap1 < matrix.length; toSwap1++) {
                for (int toSwap2 = 0; toSwap2 < matrix.length; toSwap2++) {
                    if (toSwap1 > toSwap2) continue;
                    Integer[][] curMatrix = clone(matrix);
                    for (int row = 0; row < matrix.length; ++row) {
                        curMatrix[row] = swap(curMatrix[row], toSwap1, toSwap2);
                    }
                    matrixShuffles.add(curMatrix);
                }
            }
            
            
            return matrixShuffles;
        }

        private <T> T[] swap(T[] row, int toSwap1, int toSwap2) {
            T tmp = row[toSwap1];
            row[toSwap1] = row[toSwap2];
            row[toSwap2] = tmp;
            return row;
        }
    }
}
