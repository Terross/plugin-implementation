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

/**��������� ������ �������� ������������ ���� ���������� ������*/
public class VertexReach implements GraphProperty 
{	/** ��������� ������ ������� */			private Set<UUID> passed;
	/** ����� ���� ������ ����� */		 	private	Map<UUID, Vertex> vertices;
	/** ������ ��������� ������ ������� */	private Map<UUID, Set<UUID>> adjlist;
	/** ��� ���� */							private Graph graph;
    /**�������� ������� ��������� ��� ������ graph�*/
    private void makeList() 
   	{   passed = new HashSet<UUID>();
    	vertices = graph.getVertices();
   		adjlist = new HashMap<UUID, Set<UUID>>();
   		for (UUID tempver : vertices.keySet())
   			adjlist.put(tempver, new HashSet<UUID>());
   		for (Edge edge : graph.getEdges()) //������ ���������
   			adjlist.get(edge.getFromV()).add(edge.getToV());
   	}	
    /** ��������� ����� � ������� � ��������� ���� ���������� ������
     * @param vertex ������� �� ������� ��� �����
     * @throws Exception � ������ ���� ���� �����-���� ����������� ������� �� �������
     */
    private void deepSearch(UUID vertex) throws Exception
    {   if (!vertices.get(vertex).getColor().equals(Color.red))
    		throw new Exception();
    	passed.add(vertex);
    	for (UUID neighbour : adjlist.get(vertex))
    		if (!passed.contains(neighbour))
    			deepSearch(neighbour);
    }
	/**��������� ������ ������: <p>��������� ������������ ������ �� ������� � ������ S � ���� ���� ������. ���� ���������� ������� �� ������� ��� ������������ - ������� - ���������� false
	 * @param graph - ��������������� ����
	 * @return ����� �� ������ �������� �� ������� ��� ���������� �������
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
