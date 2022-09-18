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

/**��������� ������ �������� �������� �� ���� �� ������ ������� ������ �������*/
public class EdgeGraph implements GraphProperty 
{	/**����� ����� ��� ������ ������ ������*/	private Graph graph;
	/** ������ ��������� ������ 2-��� �����*/	private Map<UUID, Set<Integer>> adjlist2;
	/** ������ ������ 2-��� �����*/				private List<UUID> secondVertices;
	/** ������ ��������� ������ 1-��� ����� */	private Map<UUID, Set<Integer>> adjlist1;
	/** ������ ���� 1-��� �����*/				private List<Edge> firstEdges;
	
	/**�������� ������� ��������� ��� ������ graph�*/
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
   		for (Edge edge : graph.getEdges()) //������ ���������
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
    
	/**��������� ������ ������: <p>���������, �������� ��  ���� � ������� �� �������� ������� ������ ����� � ������� �� �����
	 * @param graph - ����������������� ����
	 * @return ����� �� ������ �������� �� ������ ���� ������� �������
	 */
    @Override
    public boolean execute(Graph G)
	{   try
		{	graph = G;
			makeList();
	   		for (Edge edge : firstEdges) //������ ���������
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
