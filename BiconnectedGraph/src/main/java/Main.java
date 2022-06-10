import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Graph;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:/Users/bobyl/Desktop/univer/mlita/graphs/plugin-implementation/BiconnectedGraph/src/test/graph.txt");
        Graph graph = null;
        try {
            graph = GraphFactory.loadGraphFromFile(file);
        } catch (FileNotFoundException ignored) {}
        System.out.println(new BiconnectedGraph().execute(graph));
    }
}
