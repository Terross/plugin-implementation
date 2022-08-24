

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
 
/**��������� ������ ������ ���� ������������ ���������� ������ � ���� ����� ���������� �������� */
public class MinSpanTree implements  GraphCharacteristic{
	/**��������� ������ ������: <p>��������� ���� �� ����������� � ��������� ��������� �� � ������, ���� ��� ������������� �������, ���� �� ����� �� ������ ������, ������� �� ��������� ���
     * @param G - ����������������� ����
     * @return ��������� ��� ������������ ��������� ������
	 */
	public Integer execute(Graph graph)
	{	int needEdges = graph.getVertexCount()-1;
		int numEdges = 0;
		int curColor = 0;
		int weight = 0;
		HashMap<UUID, Integer> added = new HashMap<UUID, Integer>();
		List<Edge> edges = graph.getEdges();
		Collections.sort(edges, new Comparator<Edge>() { //���������� ���� �� ����
			public int compare(Edge o1, Edge o2) {
				return o1.getWeight()-o2.getWeight();
			}
		});
		for (Edge edge : edges) //�������� ��������
		{	if (!added.containsKey(edge.getFromV()) && !added.containsKey(edge.getToV()))
			{	added.put(edge.getFromV(), ++curColor);
				added.put(edge.getToV(), curColor);
			}
			else if (added.containsKey(edge.getFromV()) && added.containsKey(edge.getToV()))
			{	if (added.get(edge.getFromV()) == added.get(edge.getToV()))
					continue;
				int cur = added.get(edge.getFromV()), newCol = added.get(edge.getToV());
				for (UUID vertex : added.keySet())
					if (added.get(vertex) == cur)
						added.replace(vertex, newCol);				
			}
			else if (added.containsKey(edge.getFromV()))
				added.put(edge.getToV(), added.get(edge.getFromV()));
			else 
				added.put(edge.getFromV(), added.get(edge.getToV()));
		
			numEdges++;
			weight+=edge.getWeight();
			if (numEdges==needEdges)
				return weight;	
		}
		return 0;	
	}
}
