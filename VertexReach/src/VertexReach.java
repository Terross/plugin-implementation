import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу проверки окрашенности всех достижимых вершин*/
public class VertexReach implements GraphProperty 
{	/** множество вершин цепочки */			private Set<UUID> passed;
	/** Карта всех вершин графа */		 	private	Map<UUID, Vertex> vertices;
	/** список смежности каждой вершины */	private Map<UUID, Set<UUID>> adjlist;
	/** Сам граф */							private Graph graph;
    /**Создание списков смежности для вершин graphа*/
    private void makeList() 
   	{   passed = new HashSet<UUID>();
    	vertices = graph.getVertices();
   		adjlist = new HashMap<UUID, Set<UUID>>();
   		for (UUID tempver : vertices.keySet())
   			adjlist.put(tempver, new HashSet<UUID>());
   		for (Edge edge : graph.getEdges()) //списки смежности
   			adjlist.get(edge.getFromV()).add(edge.getToV());
   	}	
    /** Выполняет поиск в глубину и проверяет цвет посещённых вершин
     * @param vertex вершина от которой идёт поиск
     * @throws Exception в случае если цвет какой-либо достигнутой вершины не красный
     */
    private void deepSearch(UUID vertex) throws Exception
    {   if (!vertices.get(vertex).getColor().equals(Color.red))
    		throw new Exception();
    	passed.add(vertex);
    	for (UUID neighbour : adjlist.get(vertex))
    		if (!passed.contains(neighbour))
    			deepSearch(neighbour);
    }
	/**Реализует задачу класса: <p>Проверяет достижимость вершин из вершины с меткой S и цвет этих вершин. Если достижимая вершина не красная или недостижимая - красная - возвращает false
	 * @param graph - ориентированный граф
	 * @return ответ на вопрос отмечены ли красным все достижимые вершины
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	this.graph = graph;
			makeList();
			UUID startVer = null;
			for (Vertex ver : vertices.values())
				if (ver.getLabel()!=null && ver.getLabel().equals("S"))
					startVer = ver.getId();
			deepSearch(startVer);
			for (Vertex ver : vertices.values())
				if (!passed.contains(ver.getId()) && ver.getColor().equals(Color.red))
					return false;
			return true;
		} catch (Exception e) {}
		return false;
	}
}
