import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Graph;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var file = new File("/home/dmitry/IdeaProjects/math-system/graph.txt");
        GraphFactory graphFactory = new GraphFactory();
        var directedGraph = GraphFactory.loadGraphFromFile(file);
        var hamiltonPath = new HamiltonPath();
        System.out.print(hamiltonPath.execute(directedGraph));
    }
}
