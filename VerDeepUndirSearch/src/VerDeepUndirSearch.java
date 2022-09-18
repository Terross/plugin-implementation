import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**��������� ������ �������� ������������ ������ ������������������ ����� � ������� �� ��������*/
public class VerDeepUndirSearch implements GraphProperty 
{	/** ��������� ������ ������� */			private Set<UUID> passed;
	/** ������ ��������� ������ ������� */	private Map<UUID, Set<UUID>> adjlist;
	/** ��������������� �� ������ �������*/	private LinkedList<Map.Entry<UUID, Vertex>> sortedVertices;
	/** ��� ���� */							private Graph graph;
    
	/**�������� ������� ��������� ��� ������ graph�*/
    private void makeList() 
   	{   passed = new HashSet<UUID>();
   		adjlist = new HashMap<UUID, Set<UUID>>();
   		for (UUID tempver : graph.getVertices().keySet())
   			adjlist.put(tempver, new HashSet<UUID>());
   		for (Edge edge : graph.getEdges()) //������ ���������
   		{	adjlist.get(edge.getFromV()).add(edge.getToV());
   			adjlist.get(edge.getToV()).add(edge.getFromV());
   		}
   	}	
    
    /** ��������� ������������ ������ � �������
     * @param vertex ������� �� ������� ��� �����
     * @throws Exception � ������ ���� �� �������� �����-���� �������, �� �� �� �������� ������������ ����
     */
    private void deepSearch(UUID vertex) throws Exception
    {   passed.add(vertex);
    	while (sortedVertices.size()>0 && adjlist.get(vertex).contains(sortedVertices.peekFirst().getKey()))
    		deepSearch(sortedVertices.pollFirst().getKey());
    	for (var tempver: adjlist.get(vertex))
	    	if (!passed.contains(tempver))
	    		throw new Exception();
    }
    
    
	/**��������� ������ ������: <p>��������� ������������ ������ ������������������ ����� � ������� ����������� ��������
	 * @param graph - ����������������� ����
	 * @return ����� �� ������ ��������� �� ������������������ ������
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	this.graph = graph;
			makeList();
			sortedVertices = new LinkedList<Map.Entry<UUID, Vertex>>(graph.getVertices().entrySet());
			Collections.sort(sortedVertices, new Comparator<Map.Entry<UUID, Vertex>>() {
				   public int compare(Map.Entry<UUID, Vertex> o1, Map.Entry<UUID, Vertex> o2) {
					   if (Integer.valueOf(o1.getValue().getLabel()) - Integer.valueOf(o2.getValue().getLabel())==0)
						   return 0/0;
				       return Integer.valueOf(o1.getValue().getLabel()) - Integer.valueOf(o2.getValue().getLabel());
				   }
			});
			while(sortedVertices.size()>0)
				deepSearch(sortedVertices.pollFirst().getKey());
			return true;
		} catch (Exception e) {}
		return false;
	}
    public static void main(String[] args) throws FileNotFoundException {
        Graph Graph = GraphFactory.loadGraphFromFile(
                new File("graph.txt"));
        var trans = new VerDeepUndirSearch();
        System.out.println(trans.execute(Graph));
    }
}