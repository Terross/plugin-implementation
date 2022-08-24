
import java.util.Arrays;
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

import static java.lang.Math.min;

/**��������� ������ ��������, �������� �� ��������� ������� ������ �������*/
public class CenterOfGraph implements GraphProperty
{  /**����� ����� ��� ������ ������ ������*/	private Graph graph;	/**����� ������ � �����*/					private int vertexCount;	/**������� ���������*/						private int[][] matrix;
	Map<UUID, Vertex> vertices;
	Map<UUID, Integer> indexOf;
	
    /**������� ������� ��������� ��� graph�*/
    void makeMatrix() 
    {   vertexCount = graph.getVertexCount();
    	matrix = new int[vertexCount][vertexCount];
        for (int[] row : matrix) 
        	Arrays.fill(row, Integer.MAX_VALUE/2);
        vertices = graph.getVertices();
        indexOf = new HashMap<UUID, Integer>();
        int i=0;
        for (UUID tempver : vertices.keySet())
        	indexOf.put(tempver, i++);
        for (Edge edge : graph.getEdges())
        {	matrix[indexOf.get(edge.getToV())][indexOf.get(edge.getFromV())] = 1;
        	matrix[indexOf.get(edge.getFromV())][indexOf.get(edge.getToV())] = 1;
        }
        for (i=0; i<vertexCount; i++)
        	matrix[i][i]=0;
    }
    
	/**��������� ������ ������: <p>��������� ����������� �� ��������� ������� ������. ���� ��� �����-�� �� ��� ��� ������ �������, ��� ��� �����-�� ����� ����� ������� - ���������� false
	 * @param graph - ��������������� ����
	 * @return ����� �� ������ �������� �� ��������� ������� ������ �������
	 */
    @Override
    public boolean execute(Graph G)
	{   try
		{	graph = G;
			makeMatrix();
			for (int k = 0; k < vertexCount; k++)//�������� ������-��������
	            for (int i = 0; i < vertexCount; i++)
	                for (int j = 0; j < vertexCount; j++)
	               	 	matrix[i][j] = min(matrix[i][j], matrix[i][k] + matrix[k][j]);
			Set<Integer> indexes = new HashSet<Integer>();
	    	int radius = Integer.MAX_VALUE/2;
	        for(int i = 0;i<vertexCount;i++)
	        {	int localmin = Arrays.stream(matrix[i]).max().getAsInt();
	        	if (localmin<radius)
	        	{	indexes.clear();
	        		indexes.add(i);
	        		radius = localmin;
	        	}
	        	else if (radius==localmin)
	        		indexes.add(i);
	        }
	        int i=0;
	        for (Vertex ver: vertices.values())
	        	if (ver.getColor().equals(Color.red))
	        	{	i++;
	        		if (!indexes.contains(indexOf.get(ver.getId())))
	        			return false;
	        	}
	        return indexes.size()==i;
		}catch (Exception e) {}
		return false;
    }
}
