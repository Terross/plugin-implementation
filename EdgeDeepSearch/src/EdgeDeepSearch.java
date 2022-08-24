import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу проверки правильности обхода графа в глубину*/
public class EdgeDeepSearch implements GraphProperty 
{	/** Множество вершин цепочки */			private Set<UUID> passed;
	/** Список смежности каждой вершины */	private Map<UUID, Set<UUID>> adjlist;
	/** Отсортированные по меткам рёбра */	private	LinkedList<Edge> sortedEdges;
	/** Сам граф */							private Graph graph;
    
	/**Создание списков смежности для вершин graphа*/
    private void makeList() 
   	{   adjlist = new HashMap<UUID, Set<UUID>>();
   		for (UUID tempver : graph.getVertices().keySet())
   			adjlist.put(tempver, new HashSet<UUID>());
   		if (graph.getDirectType()==GraphType.DIRECTED)
   			for (Edge edge : graph.getEdges()) //списки смежности
   				adjlist.get(edge.getFromV()).add(edge.getToV());
   		else
   			for (Edge edge : graph.getEdges()) //списки смежности
   			{	adjlist.get(edge.getFromV()).add(edge.getToV());
   				adjlist.get(edge.getToV()).add(edge.getFromV());
   			}
   	}	
    
    /** Проверяет правильность обхода в глубину
     * @param vertex вершина от которой идёт обход
     * @throws Exception в случае если мы покидаем какую-либо вершину, но из неё остались непройденные пути
     */
    private void deepSearch(UUID vertex) throws Exception
    {   passed.add(vertex);
    	while (sortedEdges.size()>0)
    		if (sortedEdges.peekFirst().getFromV().equals(vertex))
    			if (!passed.contains(sortedEdges.peekFirst().getToV()))
    				deepSearch(sortedEdges.pollFirst().getToV());
    			else
    				throw new Exception();
    		else 
    			if (sortedEdges.peekFirst().getToV().equals(vertex))
        			if (!passed.contains(sortedEdges.peekFirst().getFromV()))
        				deepSearch(sortedEdges.pollFirst().getFromV());
        			else
        				throw new Exception();
    			else
    				break;
    				
    	for (var tempver: adjlist.get(vertex))
	    	if (!passed.contains(tempver))
	    		throw new Exception();
    }
	/**Реализует задачу класса: <p>Проверяет правильность обхода графа в глубину рекурсивной функцией
	 * @param graph - сам граф
	 * @return ответ на вопрос правильна ли последовательность обхода
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	this.graph = graph;
			makeList();
			passed = new HashSet<UUID>();
			List<Edge> edges = graph.getEdges();
			sortedEdges = new LinkedList<Edge>();
			for (Edge edge: edges)
				if (!(edge.getLabel() == null || edge.getLabel().equals("null")))
					sortedEdges.add(edge);
			Collections.sort(sortedEdges, new Comparator<Edge>() {
				@Override public int compare(Edge o1, Edge o2) {
					if (Integer.valueOf(o1.getLabel()) - Integer.valueOf(o2.getLabel())==0)
						return 0/0;
					return Integer.valueOf(o1.getLabel()) - Integer.valueOf(o2.getLabel());
			   }
			});
			while(sortedEdges.size()>1)
				if (sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getToV()) || sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getFromV()))
					deepSearch(sortedEdges.peekFirst().getToV());
				else if (sortedEdges.get(0).getToV().equals(sortedEdges.get(1).getToV()) || sortedEdges.get(0).getToV().equals(sortedEdges.get(1).getFromV()))
					deepSearch(sortedEdges.peekFirst().getFromV());
				else 
					return false;
			return true;
		} catch (Exception e) {}
		return false;
	}
}