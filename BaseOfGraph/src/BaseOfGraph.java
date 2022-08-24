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

/**��������� ������ ��������, �������� �� ��������� ������� ������ �����*/
public class BaseOfGraph implements GraphProperty 
{	/** ��������� ���������� ������ */		private Set<UUID> passed;
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
     * @throws Exception � ������ ���� ���� �����-���� ����������� ������� �������
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
	/**��������� ������ ������: <p>��������� ������������ ������ �� ������ �������� �����. ���� ���������� ������� ������� ��� �� ��� ������� ��������� - ���������� false
	 * @param graph - ��������������� ����
	 * @return ����� �� ������ �������� �� ��������� ������� ������ �����
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
