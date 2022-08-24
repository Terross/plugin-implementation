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

/**Реализует задачу проверки, является ли множество красных вершин базой*/
public class BaseOfGraph implements GraphProperty 
{	/** множество достижимых вершин */		private Set<UUID> passed;
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
     * @throws Exception в случае если цвет какой-либо достигнутой вершины красный
     */
    private void deepSearch(UUID vertex) throws Exception
    {   passed.add(vertex);
    	for (UUID neighbour : adjlist.get(vertex))
    		if (!passed.contains(neighbour))
    			if (vertices.get(neighbour).getColor().equals(Color.red))
    	    		throw new Exception();
    			else
    				deepSearch(neighbour);
    }
	/**Реализует задачу класса: <p>Проверяет достижимость вершин из вершин красного цвета. Если достижимая вершина красная или не все вершины достижимы - возвращает false
	 * @param graph - ориентированный граф
	 * @return ответ на вопрос является ли множество красных вершин базой
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	this.graph = graph;
			makeList();
			for (Vertex ver : vertices.values())
				if (ver.getLabel()!=null && ver.getColor().equals(Color.red))
					deepSearch(ver.getId());
			return passed.size()==graph.getVertexCount();
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}
}
