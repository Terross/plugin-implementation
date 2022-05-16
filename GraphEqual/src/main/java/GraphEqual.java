

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.directed.DirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.List;

public class GraphEqual implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        DirectedGraph graph1 = new DirectedGraph(graph);
        if (graph1.getEdgeCount() % 2 != 0) return false;
         /*Число рёбер, с учётом того, что по умолчанию петли не учитываем(они есть всегда)
        , должно быть чётно из-за симметричности*/

        boolean key = true;
        List<Vertex> vertices = graph1.getVertices();
        int rep = graph1.getVertexCount();
        for (int i = 0; key && i < rep; i++)
        {
            Vertex vertex = vertices.get(i);
            List<AbstractEdge> edges = vertex.getEdgeList();
            for (int j = 0; key && j < edges.size(); j++)
            {
                Vertex vertex1 = edges.get(j).other(vertex);
                List<AbstractEdge> edges1 = vertex1.getEdgeList();
                for (AbstractEdge abstractEdge : edges1) {
                    key = (abstractEdge.other(vertex1) == vertex);
                    if (key) break;
                }
            }
        }
        Trans trans = new Trans();
        return (key && trans.execute(graph));
    }


    public class Trans implements GraphProperty {
        @Override
        public boolean execute(Graph graph) {
            DirectedGraph graph1 = new DirectedGraph(graph);
            boolean k;
            List<Vertex> vertices = graph1.getVertices();
            for (Vertex ver1 : vertices)                                  //Для каждой вершины графа:
            {
                List<AbstractEdge> edgeList1 = ver1.getEdgeList();        //Получаем список её ребёр.
                for (AbstractEdge edge1 : edgeList1)                      //Для каждого ребра графа:
                {
                    if (edge1.getV().equals(ver1))                             //Если оно исходит из графа,
                    {
                        Vertex ver2 = edge1.getW();                       //получаем вторую вершину
                        List<AbstractEdge> edgeList2 = ver2.getEdgeList();//и список смежности для неё.
                        for (AbstractEdge edge2 : edgeList2)              //Проходя по списку смежности,
                        {
                            if (edge2.getV().equals(ver2)) {
                                Vertex ver3 = edge2.getW();               //выбираем третью вершину
                                if (!ver1.equals(ver3)) {
                                    k = true;
                                    for (AbstractEdge edge0 : edgeList1)      //и ищем в списке смежности 1-ой ребро между 1 и 3.
                                        if (edge0.getV().equals(ver1) && edge0.getW().equals(ver3)) {
                                            k = false;
                                            break;
                                        }
                                    if (k)                                  //Если не находим, возвращаем 0
                                        return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;   //Если для всех всё найдено, возвращаем 1
        }
    }
}
