import java.util.Arrays;
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

/**��������� ������ ����������� ������� ����������� ����� ���� ��������� ��� ������� ��������������� ������*/
public class FullFree implements GraphProperty 
{	/**����� ����� ��� ������ ������ ������*/	 	private Graph graph;
	/**����� ������ � �����*/						private int vertexCount;
	/**������� ���������*/							private int[][] matrix;
	/**��������� ������ ������: <p>������ ������� ��������� � ���� � ��� ������ ���������� �����. ���� �� ����� 3 � ��� ������������� ������, �� �� ��������� ������ ����������
     * @param graph - ����������������� ����
     * @return ����� �� ������ �������� �� ���� ������ ����������
     * @warning !!!�� �������� ��� ������� ������ � �����!!!
	 */
	@Override
	public boolean execute(Graph graph) 
	{	this.graph = graph;
		vertexCount = graph.getVertexCount();
	   	if (vertexCount > 2) 
	   	{	makeMatrix();
	   		for (int i = 0; i < vertexCount; i++) 
	   			if (Arrays.stream(matrix[i]).sum()==0)
	   				return false;
	   		Set<Integer> classes = new HashSet<Integer>(); //������� ��� ��������� ��������� ������� ��������������� ������
	   		classes.add(0);
	   		boolean flag = true;
	   		for (int i = 1; i < vertexCount; i++, flag = true) 
	   		{	for (Integer j:classes)
	   				if (Arrays.equals(matrix[j], matrix[i]))
	   				{	flag = false;
	   					break;
	   				}
	   			if (flag)
					classes.add(i);
	   		}
	   		return classes.size()==3;
	   	}
	   	return false;
	}
   	/**������� ������� ��������� ��� graph�*/
   	void makeMatrix() 
   	{   matrix = new int[vertexCount][vertexCount];
   		Map<UUID, Vertex> tempvers = graph.getVertices();
   		Map<UUID, Integer> vertices = new HashMap<UUID, Integer>();
   		int i=0;
   		for (UUID tempver : tempvers.keySet())
       		vertices.put(tempver, i++);
   		List<Edge> edges = graph.getEdges();
   		for (Edge edge : edges)
   		{	matrix[vertices.get(edge.getToV())][vertices.get(edge.getFromV())] = 1;
   			matrix[vertices.get(edge.getFromV())][vertices.get(edge.getToV())] = 1;
   		}
   	}
}
