import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.ArrayList;
import java.util.LinkedList;

public class ThreeVertexConnected implements GraphProperty {

    //          Функция добавляет рёбра, из которых состоит найденный путь, в список исключённых
    //  start - дочерняя вершина. Находим её родителя(вершину, из которой пришли в неё при обходе в ширину) и добавляем связывающее их ребро
    //  в список исключённых. Далее рекурсивно вызываем эту функцию, рассматривая родителя как дочернюю вершину другой вершины
    //  повторяем, пока не дойдём до стартовой вершины(её родитель null)
    private void BuildPath(Vertex start, ArrayList<Vertex> parents, ArrayList<Vertex> children, ArrayList<AbstractEdge> excluded){
        var other = parents.get(children.indexOf(start));
        if(other != null) {
            for(var edge: other.getEdgeList())
                if(edge.other(other) == start){
                    excluded.add(edge);
                    break;
                }
            BuildPath(other, parents, children, excluded);
        }
    }

    // Обходом в ширину проверяем, существует ли путь между двумя вершинами
    // Если путь существует, запоминаем его рёбра, чтобы не ходить по ним при очередном поиске пути
    // по исключённым рёбрам не ходим
    private boolean PathExist(Vertex start, Vertex end, ArrayList<AbstractEdge> excluded)
    {
        var queue = new LinkedList<Vertex>();
        var viewed = new ArrayList<Vertex>();
        var viewedParents = new ArrayList<Vertex>();
        queue.addLast(start);
        viewed.add(start);
        viewedParents.add(null);

        while(!queue.isEmpty()) {
            var firstVertex = queue.pop();

            for(var edge: firstVertex.getEdgeList())
                if(!excluded.contains(edge))
                    if(edge.other(firstVertex) == end) {
                        excluded.add(edge);
                        BuildPath(firstVertex, viewedParents, viewed, excluded);
                        return true;
                    }
                    else{
                        if(!viewed.contains(edge.other(firstVertex))){
                            viewedParents.add(firstVertex);
                            viewed.add(edge.other(firstVertex));
                            queue.add(edge.other(firstVertex));
                        }
                    }
        }

        return false;
    }

    // Для всех возможных пар вершин проверяем, существует ли между ними три непересекающихся пути
    // excluded содержит рёбра найденных ранее путей
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph abstractGraph = new UndirectedGraph(graph);
        var vertexList = abstractGraph.getVertices();
        var vertexCount = abstractGraph.getVertexCount();
        var excluded = new ArrayList<AbstractEdge>();

        for(int i = 0; i < vertexCount; i++)
            for(int j = i+1; j < vertexCount; j++) {
                excluded.clear();
                for(int k = 0; k < 3; k++)
                    if(!PathExist(vertexList.get(i), vertexList.get(j), excluded))
                        return false;
            }
        return true;
    }
}