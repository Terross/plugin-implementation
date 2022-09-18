import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**��������� ������ �������� ������������ ������ � ������� �� ��������*/
public class NewCosaraju implements GraphProperty 
{	/** ��������� ������ ������� */			private Set<UUID> passed;
	/** ������ ��������� ������ ������� */	private Map<UUID, Set<UUID>> adjlist;
	/** ��������������� �� ������ �������*/	private LinkedList<UUID> sortedVertices;
	/** ������ ������ �����				*/	Map<UUID, Vertex> vertices;
	/** ��� ���� */							private Graph graph;
    
		/**�������� ������� ��������� ��� ������ graph�*/
    private void makeList() 
   	{   passed = new HashSet<UUID>();
   		adjlist = new HashMap<UUID, Set<UUID>>();
   		for (UUID tempver : graph.getVertices().keySet())
   			adjlist.put(tempver, new HashSet<UUID>());
   		for (Edge edge : graph.getEdges()) //������ ���������
   			adjlist.get(edge.getToV()).add(edge.getFromV());
   	}	
    
    /** ��������� ������������ ������ � �������
     * @param vertex ������� �� ������� ��� �����
     * @throws Exception � ������ ���� �� �������� �����-���� �������, �� �� �� �������� ������������ ����
     */
    private void deepSearch(UUID vertex) throws Exception
    {   passed.add(vertex);
    	while (sortedVertices.size()>0 && adjlist.get(vertex).contains(sortedVertices.peekFirst()))
    		deepSearch(sortedVertices.pollFirst());
    	for (var tempver: adjlist.get(vertex))
	    	if (!passed.contains(tempver))
	    		throw new Exception();
    }
    
    
	/**��������� ������ ������: <p>��������� ������������ ������ � ������� ����������� ��������
	 * @param graph - ��������������� ����
	 * @return ����� �� ������ ��������� �� ������������������ ������
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	this.graph = graph;
			makeList();
			sortedVertices = new LinkedList<UUID>(graph.getVertices().keySet());
			Collections.sort(sortedVertices, new Comparator<UUID>() {
				   @Override public int compare(UUID o1, UUID o2) {
					   if (vertices.get(o1).getWeight()==vertices.get(o2).getWeight())
						return 0/0;
					return vertices.get(o2).getWeight()-vertices.get(o1).getWeight();
				   }
			});
			while(sortedVertices.size()>0)
				deepSearch(sortedVertices.pollFirst());
			return true;
		} catch (Exception e) {}
		return false;
	}
}