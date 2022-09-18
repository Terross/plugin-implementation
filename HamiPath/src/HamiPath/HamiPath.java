package HamiPath;
import java.util.ArrayList;
import java.util.Collections;
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
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**��������� ������ �������� ���������� ������ ������ �� ���������������*/
public class HamiPath implements GraphProperty 
{	/** ����� ���� ������ ����� */		 	private	Map<UUID, Vertex> vertices;
	/** ������ ��������� ������ ������� */	private Map<UUID, Set<UUID>> adjlist;
	/** ���������� ������ ����� */			private int vertexCount;
	/** ��� ���� */							private Graph graph;
	
	/**��������� ������ ������: <p>��������� ������� ���� ����� ���������, � ������� ����� ����������� �� 1
	 * @param graph - ����������������� ����
	 * @return ����� �� ������ �������� �� ��������� ������� ������ ������ ������������� ����
	 */
	@Override
	public boolean execute(Graph graph) 
	{	this.graph=graph;
		makeList();	
		List<Map.Entry<UUID, Vertex>> sortedVers = new ArrayList<Map.Entry<UUID, Vertex>>(vertices.entrySet());
		Collections.sort(sortedVers, new Comparator<Map.Entry<UUID, Vertex>>() {
			public int compare(Map.Entry<UUID, Vertex> o1, Map.Entry<UUID, Vertex> o2) {
				if (Integer.valueOf(o1.getValue().getLabel()) - Integer.valueOf(o2.getValue().getLabel())==0)
					return 0/0;
				return Integer.valueOf(o1.getValue().getLabel()) - Integer.valueOf(o2.getValue().getLabel());
			   }
			});
		for (int i = 1; i<vertexCount; i++)
			if (!adjlist.get(sortedVers.get(i-1).getKey()).contains(sortedVers.get(i).getKey()))
				return false;
		return true;
	}
    /**�������� ������� ��������� ��� ������ graph�*/
    private void makeList() 
   	{   vertexCount = graph.getVertexCount();
    	vertices = graph.getVertices();
   		adjlist = new HashMap<UUID, Set<UUID>>();
   		for (UUID tempver : graph.getVertices().keySet())
   			adjlist.put(tempver, new HashSet<UUID>());
   		if (graph.getDirectType()==GraphType.DIRECTED)
   			for (Edge edge : graph.getEdges()) //������ ���������
   				adjlist.get(edge.getFromV()).add(edge.getToV());
   		else
   			for (Edge edge : graph.getEdges()) //������ ���������
   			{	adjlist.get(edge.getFromV()).add(edge.getToV());
   				adjlist.get(edge.getToV()).add(edge.getFromV());
   			}
   	}	
}


