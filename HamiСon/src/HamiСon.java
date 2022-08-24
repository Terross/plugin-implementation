
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
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу определения гамильтоново-связности графа путём перебора всех возможных гамильтоновых цепочек*/
public class HamiСon implements GraphProperty 
{   /** множество вершин цепочки */			private Set<UUID> passed;
	/** список смежности каждой вершины */	private Map<UUID, List<UUID>> vertices;
	/** индексы вершин для матрицы */		private Map<UUID, Integer> indexes;
	/** матрица досягаемости */				private boolean[][] matrixofbits;
	/** Индекс текущей вершины */			private int curindex, 
	/** Количество вершин графа */						vertexCount;
	/** Сам граф */							private Graph graph;
	
	/**Реализует задачу класса: <p>Пытается построить гамильтонову цепочку из каждой вершины в каждую. При успехе возвращает true
     * @param graph - неориентированный граф
     * @return ответ на вопрос является ли граф гамильтоново-связным
	 */
    @Override
    public boolean execute(Graph graph)
    {   this.graph=graph;
    	vertexCount=graph.getVertexCount();
        if  (vertexCount<2)
            return false;
        else
        {   makeList();
        	matrixofbits=new boolean[vertexCount][vertexCount];
            for (UUID vertex : vertices.keySet())
            {   passed=new LinkedHashSet<UUID>();
            	curindex = indexes.get(vertex);
                buildChainFrom(vertex);
                matrixofbits[curindex][curindex]=true;
                for (int j=0; j<vertexCount; j++)
                    if(!matrixofbits[curindex][j])
                        return false;
            }
            return true;
        }
    }
    /**Создание списков смежности нумерация вершин для graphа*/
    private void makeList() 
   	{   Map<UUID, Vertex> tempvers = graph.getVertices();
   		vertices = new HashMap<UUID, List<UUID>>();
   		indexes = new HashMap<UUID, Integer>();
   		int i=0;
   		for (UUID tempver : tempvers.keySet())//нумерация вершин
   			indexes.put(tempver, i++);
   		
   		for (UUID tempver : tempvers.keySet())
       		vertices.put(tempver, new LinkedList<UUID>());
   		for (Edge edge : graph.getEdges()) //списки смежности
   		{	vertices.get(edge.getToV()).add(edge.getFromV());
   			vertices.get(edge.getFromV()).add(edge.getToV());
   		}
   	}
    
    /**Рекурсивный метод перебора всех возможных цепочек из данной вершины<p>
     * Если из данной вершины цепочек уже не существует, проверяет, является ли получившаяся цепь гамильтоновой
     * @param vertex веришина, от которой строится цепочка
     */
    private void buildChainFrom(UUID vertex)
    {   passed.add(vertex);
        List<UUID> neighbors = vertices.get(vertex);
        boolean flag=true;
        for(UUID neighbour:neighbors)
        {   if (!passed.contains(neighbour))
            {   buildChainFrom(neighbour);
                flag=false;
            }
        }
        if (flag)
            for (UUID tempver: vertices.keySet())
                if (!passed.contains(tempver))
                {   flag = false;
                    break;
                }
        if (flag)
            matrixofbits[curindex][indexes.get(vertex)]=true;
        passed.remove(vertex);
    }
}