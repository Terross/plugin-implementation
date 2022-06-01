
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractGraph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.directed.DirectedGraph;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.LinkedList;
import java.util.List;

public class GraphRadius implements GraphCharacteristic {


    private int findLen(Vertex u, Vertex v, AbstractGraph G) {
        int len = 0;

        LinkedList<Vertex> Queue = new LinkedList<>();
        List<Vertex> verteces = G.getVertices();
        boolean[] used = new boolean[G.getVertexCount()];


        Queue.add(u);




        while (!Queue.isEmpty()) { // очередь не пуста
            int qSize = Queue.size();
            for (int k = 0; k < qSize; k++) { // обходим весь уровень очереди

                Vertex cur = Queue.remove();

                used[verteces.indexOf(cur)] = true; // добавляем в список осмотренных

                if (cur.equals(v)) return len; // если найден путь, вернем расстояние
                else {
                    int curEdgeCount = cur.getEdgeList().size();

                    for (int i = 0; i < curEdgeCount; i++) { // проходимся по всем соседям cur

                        Vertex curNeighbour = cur.getEdgeList().get(i).other(cur); // соседняя вершина

                        if (!used[verteces.indexOf(curNeighbour)]) Queue.add(curNeighbour); // Добавляем неотсмотренную вершину
                    }
                }
            }
            len++;
        }



        return -1;
    }


    @Override
    public Integer execute(Graph graph) {

        AbstractGraph abstractGraph;
        if (graph.getDirectType().equals(GraphType.DIRECTED)) {
            abstractGraph = new DirectedGraph(graph);
        } else {
            abstractGraph = new UndirectedGraph(graph);
        }

        int radius = Integer.MAX_VALUE;

        List<Vertex> verteces = abstractGraph.getVertices();

        int curLen, maxLen;



        Vertex u, v;
        for (int i = 0; i < abstractGraph.getVertexCount(); ++i) {
            u = verteces.get(i);
            maxLen = 0;
            for (int j = 0; j < abstractGraph.getVertexCount(); ++j) {
                v = verteces.get(j);
                curLen = findLen(u, v, abstractGraph);

                if (curLen != 0 && maxLen < curLen) {
                    maxLen = curLen;
                }
            }
            if (radius > maxLen) radius = maxLen;
        }

        return radius;
    }
}