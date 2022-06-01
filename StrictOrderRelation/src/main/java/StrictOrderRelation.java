
import com.mathsystem.api.graph.model.Graph;

import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.directed.DirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.List;

public class StrictOrderRelation implements GraphProperty {

    @Override
    public boolean execute(Graph graph) {
        DirectedGraph directedGraph = new DirectedGraph(graph);

        boolean res = true;

        List<Vertex> vertexes = directedGraph.getVertices();

        for (Vertex v : vertexes) {
            List<AbstractEdge> vEdgeList = v.getEdgeList();
            for (int j = 0; j < vEdgeList.size(); j++) {
                Vertex u = vEdgeList.get(j).other(v);
                List<AbstractEdge> uEdgeList = u.getEdgeList();
                for (AbstractEdge uEdge : uEdgeList) { // СОСЕДИ U
                    Vertex w = uEdge.other(u);
                    if (v.equals(w)) return false;
                    boolean trans = false;
                    for (AbstractEdge abstractEdge : vEdgeList) {
                        Vertex maybeW = abstractEdge.other(v);
                        if (maybeW.equals(w)) trans = true;
                    }
                    if (!trans) return false;
                }
            }
        }

        return res;


    }
}
