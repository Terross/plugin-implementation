import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;
import java.util.List;

public class FullBipartiteGraph implements GraphProperty {
    @Override

    //Чтобы проверить, является ли граф полным двудольным, нужно:
    //1) взять каждое ребро и раскрасить инцидентные ему вершины в разные цвета(2 цвета)
    //2) есть ли ребро, у которого инцидетные вершины имели бы одинаковый цвет?
    //3) умножить кол-во вершин из 1-й доли на кол-во вершин из 2-ой, результат должен совпасть с кол-вом ребер в графе
    //Если пункт 2 не вып. и 3 вып., то граф является полным двудольным
    public boolean execute(Graph graph)
    {
        UndirectedGraph graph1 = new UndirectedGraph(graph);
        List<Vertex> vertices = graph1.getVertices(); //список вершин графа
        boolean T1 = true, T2 = false;
        int j = 0;
        int P1 = 0, P2 = 0; //количество вершин в 1 и 2 доли
        int v_count = graph1.getVertexCount();//кол-во вершин в графе
        int[] color = new int[v_count];//цвета вершин
        for (int i = 0; i < v_count; i++)
            color[i] = 0;

        //возьмем каждое ребро и раскрасим инцидетные ему вершины в 2 разных цвета
        while(!every_colored(color, v_count))//пока все вершины не раскрашены
        {
            Vertex vertex = vertices.get(j);//берем j-тую вершину из списка вершин
            if(color[vertex.getIndex()] == 0)//если не покрашена
            {
                P1++;//считаем кол-во вершин, покрашенных в цвет 1
                color[vertex.getIndex()] = 1;//красим её
                //красим вершины инцидентные ребрам, которые инциденты этой вершине в другой цвет
                for (AbstractEdge abstractEdge : vertex.getEdgeList())//цикл по ребрам инцидентным вершине vertex
                {
                    //.getV() и .getW() возвращают конечную и начальную вершины инцидентные ребру,
                    //т.к. не понятно какая из них сама vertex, то проверяем обе
                    if (color[abstractEdge.other(vertex).getIndex()] == 0)
                    {
                        color[abstractEdge.other(vertex).getIndex()] = 2;
                        P2++;
                    }
                }
            }
            j++;
        }

        //проверка на то, есть ли ребро, у которого инцидетные вершины имели бы одинаковый цвет?
        for(int i = 0; i < v_count; ++i)
        {
            Vertex vertex = vertices.get(i);
            for (AbstractEdge abstractEdge : vertex.getEdgeList())
            {
                if(color[abstractEdge.getV().getIndex()] == color[abstractEdge.getW().getIndex()]) T1 = false;
            }
        }
        //умножаем кол-во вершин из 1-й доли на кол-во вершин из 2-ой  сравниваем с кол-вом ребер
        if(P1*P2 == graph1.getEdgeCount()) T2 = true;
        return T1 && T2;
    }

    //проверка на то, раскрашены ли все вершины
    public static boolean every_colored(int[] color, int n)
    {
        for(int i = 0; i < n; ++i)
        {
            if(color[i] == 0) return false;
        }
        return true;
    }
}
