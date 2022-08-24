
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;


/**Реализует задачу поиска максимальной длины пути в ориентированном графе*/
public class LongPath implements GraphCharacteristic 
{	/** Множество вершин цепочки */			private Set<UUID> passed;
	/** Список смежности каждой вершины */	private Map<UUID, List<Edge>> vertices;
	/** Максимальная длина пути*/			private int maxlen, 
	/** Текущая длина пути */						curlen;
	/** Сам граф */							private Graph graph;
	
	/**Реализует задачу класса: <p>Ищет максимальную длину пути путём перебора всех возможных путей из всех возможных вершин
     * @param graph - ориентированный граф
     * @return максимальная длина пути в графе
	 */
	public Integer execute(Graph graph) 
	{	this.graph = graph;
		makeList();
		maxlen = 0;
		for (UUID vertex : vertices.keySet())
		{	passed=new LinkedHashSet<UUID>();
			curlen = 0;
			buildChainFrom(vertex);
		}
		return maxlen;
	}
	
    /**Рекурсивный метод перебора всех возможных путей из данной вершины<p>
     * Если из данной вершины путей уже не существует, проверяет, обновляет максимальную длину
     * @param vertex вершина, от которой строится цепочка
     */
    private void buildChainFrom(UUID vertex)
    {   passed.add(vertex);
        List<Edge> edges = vertices.get(vertex);
        boolean flag=true;
        for(Edge edge:edges)
        {   if (!passed.contains(edge.getToV()))
            {   curlen+=edge.getWeight();
        		buildChainFrom(edge.getToV());
        		curlen-=edge.getWeight();
                flag=false;
            }
        }
        if (flag)
        	if (curlen>maxlen)
        		maxlen = curlen;
        passed.remove(vertex);
    }
	
    /**Создание списков смежности для graphа*/
    private void makeList() 
   	{   Map<UUID, Vertex> tempvers = graph.getVertices();
   		vertices = new HashMap<UUID, List<Edge>>();
   		for (UUID tempver : tempvers.keySet())
       		vertices.put(tempver, new LinkedList<Edge>());
   		for (Edge edge : graph.getEdges()) //списки смежности
   			vertices.get(edge.getFromV()).add(edge);
   	}
}

