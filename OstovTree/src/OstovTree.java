import java.util.ArrayList;
import java.util.List;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.api.graph.oldmodel.undirected.UndirectedGraph;
import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

public class OstovTree implements GraphProperty{
    @Override
    public boolean execute(Graph graph)
    {   UndirectedGraph abstractGraph = new UndirectedGraph(graph);
    	List<Vertex> vertices = abstractGraph.getVertices();
        List<AbstractEdge> ArrOfRedEdges = new ArrayList<>();
        for (int i = 0; i < abstractGraph.getVertexCount(); i++)
        {   Vertex vertex = vertices.get(i);
            for (AbstractEdge abstractEdge: vertex.getEdgeList())
            {
                if (abstractEdge.getColor() == Color.red)
                    ArrOfRedEdges.add(abstractEdge);
            }
        }

        boolean tV, tW;
        if(abstractGraph.getVertexCount() - ArrOfRedEdges.size()/2 == 1)
        {
            List<Vertex> VerticesOfRedEdges = new ArrayList<>();
            for(int i = 0; i < ArrOfRedEdges.size(); i++)
            {
                tV = true;
                tW = true;
                for(int j = 0; j < VerticesOfRedEdges.size(); j++)
                {
                    if(ArrOfRedEdges.get(i).getV() == VerticesOfRedEdges.get(j))
                        tV = false;
                    if(ArrOfRedEdges.get(i).getW() == VerticesOfRedEdges.get(j))
                        tW = false;
                }
                if(tV) VerticesOfRedEdges.add(ArrOfRedEdges.get(i).getV());
                if(tW) VerticesOfRedEdges.add(ArrOfRedEdges.get(i).getW());
            }

            if(VerticesOfRedEdges.size() == abstractGraph.getVertexCount())
                return true;
            else return false;
        }
        else return false;
    }
}
