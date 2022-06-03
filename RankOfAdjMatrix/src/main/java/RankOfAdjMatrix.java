import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.Arrays;
import java.util.List;
import static java.lang.Math.abs;

public class RankOfAdjMatrix implements GraphCharacteristic {
    private int totalVertex;
    private double[][] adjMatrix;

    @Override
    public Integer execute(Graph graph) {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        totalVertex = graph1.getVertexCount();
        matrix(graph1);
        if(graph1.getEdgeCount() == 3*graph1.getVertexCount() - 6) System.out.println("YES");
        else System.out.println("NO");
        return matrixRank(adjMatrix);
    }

    private void matrix(UndirectedGraph G) {
        adjMatrix = new double[totalVertex][totalVertex];
        List<Vertex> vertices = G.getVertices();
        for (double[] row : adjMatrix) Arrays.fill(row, 0);
        for (Vertex v: vertices) {
            List<AbstractEdge> edges = v.getEdgeList();
            for(var e:edges)
                adjMatrix[v.getIndex()][e.other(v).getIndex()] = 1;
        }
    }

    private int matrixRank(double[][] matrix){
        double EPS = 1E-9;
        int rank = totalVertex;
        boolean[] usedLines = new boolean[totalVertex];
        for (int col = 0; col < totalVertex; col++){
            int row;
            for (row = 0; row < totalVertex; row++){
               if (!usedLines[row] && abs(matrix[row][col]) > EPS) break;
            }
            if (row == totalVertex) rank -= 1;
            else{
                usedLines[row] = true;
                for (int p = col+1; p < totalVertex; p++)
                    matrix[row][p] /= matrix[row][col];
                for (int k = 0; k < totalVertex; k++){
                    if (k != row && abs(matrix[k][col]) > EPS){
                        for(int p = col+1; p < totalVertex; p++){
                            matrix[k][p] -= matrix[row][p]*matrix[k][col];
                        }
                    }
                }
            }
        }
        return rank;
    }
}