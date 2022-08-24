import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу определения полного трёхдольного графа путём выделения трёх классов эквивалентности вершин*/
public class FullFree implements GraphProperty 
{	/**Копия графа для работы внутри класса*/	 	private Graph graph;
	/**Число вершин в графе*/						private int vertexCount;
	/**Матрица смежности*/							private int[][] matrix;
	/**Реализует задачу класса: <p>Создаёт матрицу смежности и ищет в ней группы одинаковых строк. Если их ровно 3 и нет изолированных вершин, то он считается полным трёхдольным
     * @param graph - неориентированный граф
     * @return ответ на вопрос является ли граф полным трёхдольным
     * @warning !!!Не работает при наличии петель в графе!!!
	 */
	@Override
	public boolean execute(Graph graph) 
	{	this.graph = graph;
		vertexCount = graph.getVertexCount();
	   	if (vertexCount > 2) 
	   	{	makeMatrix();
	   		for (int i = 0; i < vertexCount; i++) 
	   			if (Arrays.stream(matrix[i]).sum()==0)
	   				return false;
	   		Set<Integer> classes = new HashSet<Integer>(); //индексы для заглавных элементов классов эквивалентности вершин
	   		classes.add(0);
	   		boolean flag = true;
	   		for (int i = 1; i < vertexCount; i++, flag = true) 
	   		{	for (Integer j:classes)
	   				if (Arrays.equals(matrix[j], matrix[i]))
	   				{	flag = false;
	   					break;
	   				}
	   			if (flag)
					classes.add(i);
	   		}
	   		return classes.size()==3;
	   	}
	   	return false;
	}
   	/**Создние матрицы смежности для graphа*/
   	void makeMatrix() 
   	{   matrix = new int[vertexCount][vertexCount];
   		Map<UUID, Vertex> tempvers = graph.getVertices();
   		Map<UUID, Integer> vertices = new HashMap<UUID, Integer>();
   		int i=0;
   		for (UUID tempver : tempvers.keySet())
       		vertices.put(tempver, i++);
   		List<Edge> edges = graph.getEdges();
   		for (Edge edge : edges)
   		{	matrix[vertices.get(edge.getToV())][vertices.get(edge.getFromV())] = 1;
   			matrix[vertices.get(edge.getFromV())][vertices.get(edge.getToV())] = 1;
   		}
   	}
}
