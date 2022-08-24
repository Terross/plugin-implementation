import static java.lang.Math.min;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу проверки графа на односторонность*/
public class OneSided implements GraphProperty 
{	/**Копия графа для работы внутри класса*/	private Graph graph;
	/**Число вершин в графе*/					private int vertexCount;
	/**Матрица смежности*/						private int[][] matrix;
	/**Бесконечность*/							private final int INF = Integer.MAX_VALUE / 2;
	
	/**Создание матрицы смежности для graphа*/
    void makeMatrix() 
    {   vertexCount = graph.getVertexCount();
    	matrix = new int[vertexCount][vertexCount];
        for (int[] row : matrix) 
        	Arrays.fill(row, INF);
        var vertices = graph.getVertices();
        var indexOf = new HashMap<UUID, Integer>();
        int i=0;
        for (UUID tempver : vertices.keySet())
        	indexOf.put(tempver, i++);
        for (Edge edge : graph.getEdges())
        	matrix[indexOf.get(edge.getFromV())][indexOf.get(edge.getToV())] = 1;
        for (i=0; i<vertexCount; i++)
        	matrix[i][i]=0;
    }
    
	/**Реализует задачу класса: <p>Проверяет граф на односторонность по матрице смежности. Если хотя бы в одной симметричной паре ячеек стоит бесконечность, возвращает false
	 * @param graph - ориентированный граф
	 * @return ответ на вопрос является ли граф односторонним
	 */
    @Override
    public boolean execute(Graph G)
	{   graph = G;
		makeMatrix();
		for (int k = 0; k < vertexCount; k++)//Алгоритм Флойда-Уоршелла
            for (int i = 0; i < vertexCount; i++)
                for (int j = 0; j < vertexCount; j++)
               	 	matrix[i][j] = min(matrix[i][j], matrix[i][k] + matrix[k][j]);
        for (int i = 0; i < vertexCount; i++) 
            for (int j = 0; j < vertexCount; j++) 
                if (matrix[i][j] == INF && matrix[j][i] == INF) 
                	return false;
        return true;
    }
}
