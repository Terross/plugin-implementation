import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.ArrayList;

public class HamiltonPath implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {
        UndirectedGraph abstractGraph = new UndirectedGraph(graph);
        ArrayList<Vertex> sortedVertecies = new ArrayList<>();

        for(Vertex v : abstractGraph.getVertices()){
            if(isNumeric(v.getLabel())){
                sortedVertecies.add(v);
            }
            else{
                return false;
            }
        }
        sortedVertecies.sort((Vertex v1, Vertex v2)
                -> Integer.compare(Integer.valueOf(v1.getLabel()), Integer.valueOf(v2.getLabel())));

        Vertex previousVertex = null;
        for(Vertex v : sortedVertecies){
            if(previousVertex != null){
                if(!edgeExists(previousVertex, v)) {
                    return false;
                }
            }
            previousVertex = v;
        }

        return true;
    }
    static private boolean edgeExists(Vertex v1, Vertex v2){
        for(AbstractEdge e : v1.getEdgeList()){
            if(e.getW() == v2)
                return true;
        }
        return false;
    }
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}


