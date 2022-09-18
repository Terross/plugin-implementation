
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


/**��������� ������ ������ ������������ ����� ���� � ��������������� �����*/
public class LongPath implements GraphCharacteristic 
{	/** ��������� ������ ������� */			private Set<UUID> passed;
	/** ������ ��������� ������ ������� */	private Map<UUID, List<Edge>> vertices;
	/** ������������ ����� ����*/			private int maxlen, 
	/** ������� ����� ���� */						curlen;
	/** ��� ���� */							private Graph graph;
	
	/**��������� ������ ������: <p>���� ������������ ����� ���� ���� �������� ���� ��������� ����� �� ���� ��������� ������
     * @param graph - ��������������� ����
     * @return ������������ ����� ���� � �����
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
	
    /**����������� ����� �������� ���� ��������� ����� �� ������ �������<p>
     * ���� �� ������ ������� ����� ��� �� ����������, ���������, ��������� ������������ �����
     * @param vertex �������, �� ������� �������� �������
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
	
    /**�������� ������� ��������� ��� graph�*/
    private void makeList() 
   	{   Map<UUID, Vertex> tempvers = graph.getVertices();
   		vertices = new HashMap<UUID, List<Edge>>();
   		for (UUID tempver : tempvers.keySet())
       		vertices.put(tempver, new LinkedList<Edge>());
   		for (Edge edge : graph.getEdges()) //������ ���������
   			vertices.get(edge.getFromV()).add(edge);
   	}
}

