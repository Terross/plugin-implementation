import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

/**Реализует задачу проверки указанного обхода рёбер на эйлеровость*/
public class EdgeEilerUndirCircle implements GraphProperty 
{	/**Реализует задачу класса: <p>Сортирует отмеченные метками рёбра и проверяет, образуют ли они эйлеров цикл
	 * @param graph - неориентированный граф
	 * @return ответ на вопрос образует ли указанный порядок обхода рёбер эйлеров цикл
	 */
	@Override
	public boolean execute(Graph graph) 
	{	try
		{	List<Edge> sortedEdges = graph.getEdges();
			Collections.sort(sortedEdges, new Comparator<Edge>() {
				@Override public int compare(Edge o1, Edge o2) {
					if (Integer.valueOf(o1.getLabel()) - Integer.valueOf(o2.getLabel())==0)
						return 0/0;
					return Integer.valueOf(o1.getLabel()) - Integer.valueOf(o2.getLabel());
			   }
			});
			UUID first = null;
			UUID last = null;
			if (sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getToV()) || sortedEdges.get(0).getFromV().equals(sortedEdges.get(1).getFromV()))
			{	last = sortedEdges.get(0).getFromV();
				first = sortedEdges.get(0).getToV();
			}
			else if (sortedEdges.get(0).getToV().equals(sortedEdges.get(1).getToV()) || sortedEdges.get(0).getToV().equals(sortedEdges.get(1).getFromV()))
			{	first = sortedEdges.get(0).getFromV();
				last = sortedEdges.get(0).getToV();
			}
			for (int i = 1; i<sortedEdges.size(); i++)
			{	if (sortedEdges.get(i).getFromV().equals(last))
					last = sortedEdges.get(i).getToV();
				else if (sortedEdges.get(i).getToV().equals(last))
					last = sortedEdges.get(i).getFromV();
				else return false;
			}
			return last.equals(first);
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}
}