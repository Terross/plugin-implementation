import java.util.ArrayList;
import java.util.Comparator;
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

/**Реализует задачу проверки является ли один из графов рёберным графом другого*/
public class EdgeGraph implements GraphProperty 
{	/**Копия графа для работы внутри класса*/	private Graph graph;
	/** Список смежности вершин 2-ого графа*/	private Map<UUID, Set<Integer>> adjlist2;
	/** Список вершин 2-ого графа*/				private List<UUID> secondVertices;
	/** Список смежности вершин 1-ого графа */	private Map<UUID, Set<Integer>> adjlist1;
	/** Список рёбер 1-ого графа*/				private List<Edge> firstEdges;
	
	/**Создание списков смежности для вершин graphа*/
    private void makeList() 
   	{   adjlist1 = new HashMap<UUID, Set<Integer>>();
   		adjlist2 = new HashMap<UUID, Set<Integer>>();
   		secondVertices = new ArrayList<UUID>();
   		firstEdges = new ArrayList<Edge>();
   		var vertices = graph.getVertices();
   		for (Vertex tempver : vertices.values())
   			if (tempver.getWeight()==null)
   				adjlist1.put(tempver.getId(), new HashSet<Integer>());
   			else
   			{	var set = new HashSet<Integer>();
   				set.add(tempver.getWeight());
   				adjlist2.put(tempver.getId(), set);
   				secondVertices.add(tempver.getId());
   			}
   		for (Edge edge : graph.getEdges()) //списки смежности
   			if (edge.getWeight()==null)
   			{	adjlist2.get(edge.getFromV()).add(vertices.get(edge.getToV()).getWeight());
   				adjlist2.get(edge.getToV()).add(vertices.get(edge.getFromV()).getWeight());
   			}
   			else
   			{	adjlist1.get(edge.getFromV()).add(edge.getWeight());
				adjlist1.get(edge.getToV()).add(edge.getWeight());
				firstEdges.add(edge);
			}
   		secondVertices.sort(new Comparator<UUID>() {
				@Override public int compare(UUID o1, UUID o2) {
					return vertices.get(o1).getWeight()-vertices.get(o2).getWeight();
			   }
			});
   	}
    
	/**Реализует задачу класса: <p>Проверяет, является ли  граф с метками на вершинах рёберным графом графа с метками на рёбрах
	 * @param graph - неориентированный граф
	 * @return ответ на вопрос является ли второй граф рёберным первого
	 */
    @Override
    public boolean execute(Graph G)
	{   try
		{	graph = G;
			makeList();
	   		for (Edge edge : firstEdges) //списки смежности
	   		{	Set<Integer> passed = new HashSet<Integer>();
	   			for (Integer num: adjlist1.get(edge.getFromV()))
					if(adjlist2.get(secondVertices.get(edge.getWeight()-1)).contains(num))
						passed.add(num);
					else
						return false;
		   		for (Integer num: adjlist1.get(edge.getToV()))
					if(adjlist2.get(secondVertices.get(edge.getWeight()-1)).contains(num))
						passed.add(num);
					else
						return false;
		   		if (!passed.equals(adjlist2.get(secondVertices.get(edge.getWeight()-1))))
		   			return false;
	   		}
	        return true;
		} catch (Exception e) {}
		return false;
    }
}
