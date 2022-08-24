import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу проверки указанного обхода рёбер на гамильтоновость*/
public class EdgeHamiPath implements GraphProperty 
{	/**Реализует задачу класса: <p>Сортирует отмеченные метками рёбра и проверяет, образуют ли они гамильтонов путь
	 * @param graph - неориентированный граф
	 * @return ответ на вопрос образует ли указанный порядок обхода вершин гамильтоновым путём
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	List<Edge> edges = graph.getEdges();
			ArrayList<Edge> sortedEdges = new ArrayList<Edge>();
			for (Edge edge: edges)
				if (!edge.getLabel().equals("null"))
					sortedEdges.add(edge);
			if (sortedEdges.size()+1!=graph.getVertexCount())
				return false; //если их не n-1, это не гамильтонов путь
			Collections.sort(sortedEdges, new Comparator<Edge>() {
				   public int compare(Edge o1, Edge o2) {
				       return Integer.valueOf(o1.getLabel()) - Integer.valueOf(o2.getLabel());
				   }
				});
			List<UUID> passed = new ArrayList<UUID>();
			if (sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getToV()) || sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getFromV()))
			{	passed.add(sortedEdges.get(0).getToV());
				passed.add(sortedEdges.get(0).getFromV());
			}
			else if (sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getToV()) || sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getFromV()))
			{	passed.add(sortedEdges.get(0).getFromV());
				passed.add(sortedEdges.get(0).getToV());
			}
			for (int i = 1; i<sortedEdges.size(); i++)
			{	if (sortedEdges.get(i).getFromV().equals(passed.get(i)))
					passed.add(sortedEdges.get(i).getToV());
				else if (sortedEdges.get(i).getToV().equals(passed.get(i)))
					passed.add(sortedEdges.get(i).getFromV());
				else return false;
			}
			return passed.size()==graph.getVertexCount();
		} catch (Exception e) {}
		
		return false;
	}
    public static void main(String[] args) throws FileNotFoundException {
        Graph Graph = GraphFactory.loadGraphFromFile(
                new File("graph.txt"));
        var trans = new EdgeHamiPath();
        System.out.println(trans.execute(Graph));
    }
}