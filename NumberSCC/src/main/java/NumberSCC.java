import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.directed.DirectedEdge;
import com.mathsystem.api.graph.oldmodel.directed.DirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class NumberSCC implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        DirectedGraph graph1 = new DirectedGraph(graph);
        List<List<Vertex>> SSC = findSSC(graph1);
        return SSC.toArray().length;
    }

    List<List<Vertex>> findSSC (DirectedGraph graph){
        List<Vertex> vertices = graph.getVertices();

        Stack<Vertex> stack = new Stack<>();
        boolean[] visited = new boolean[graph.getVertexCount()];
        Arrays.fill(visited, false);

        //Первый обход в глубину. Добавление вершин в стек времени выхода
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (!visited[i]) firstDFS(visited, stack, vertices.get(i));
        }

        transposeGraph(graph);

        //Второй обход в глубину по времени выхода и составление компонент
        Arrays.fill(visited, false);
        List<List<Vertex>> components = new ArrayList<>();
        while (!stack.empty()) {
            Vertex v = stack.pop();
            if (!visited[v.getIndex()]) {
                List<Vertex> component = new ArrayList<>();
                component.add(v);
                secondDFS(visited, component, v);
                components.add(component);
            }
        }
        return components;
    }

    void firstDFS ( boolean[] visited, Stack<Vertex > stack, Vertex v){
        visited[v.getIndex()] = true;
        List<AbstractEdge> edges = v.getEdgeList();
        for (AbstractEdge edge : edges) {
            Vertex AdjVertex = edge.other(v);
            if (!visited[AdjVertex.getIndex()]) firstDFS(visited, stack, AdjVertex);
        }
        stack.push(v);
    }

    void transposeGraph (DirectedGraph graph){
        List<Vertex> vertices = graph.getVertices();
        //Степени нужны, чтобы не обрабатывать ребро дважды, т.к. обрабатонное ребро ставится на верх списка ребер
        Integer[] degree = new Integer[graph.getVertexCount()];
        Arrays.fill(degree, 0);
        for (Vertex vert : vertices) degree[vert.getIndex()] = vert.getEdgeList().size();
        for (Vertex vert : vertices) {
            List<AbstractEdge> edges = vert.getEdgeList();
            List<AbstractEdge> toRemove = new ArrayList<>();
            for (int i = 0; i < degree[vert.getIndex()]; i++) {
                DirectedEdge edge = (DirectedEdge) edges.get(i);
                DirectedEdge newEdge = new DirectedEdge(edge.getW(), edge.getV(), edge.getWeight(), edge.getColor(),
                        edge.getLabel(), edge.getName());
                toRemove.add(edge);
                edge.to().getEdgeList().add(newEdge);
            }
            //Удаляем транспонированные ребра из списка смежности
            for (AbstractEdge edge : toRemove) edges.remove(edge);
        }
    }

    void secondDFS ( boolean[] visited, List<Vertex > component, Vertex v){
        visited[v.getIndex()] = true;
        List<AbstractEdge> edges = v.getEdgeList();
        for (AbstractEdge edge : edges) {
            Vertex AdjVertex = edge.other(v);
            if (!visited[AdjVertex.getIndex()]) {
                component.add(AdjVertex);
                secondDFS(visited, component, AdjVertex);
            }
        }
    }

}
