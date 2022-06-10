import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Graph;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:/Users/bobyl/Desktop/univer/mlita/graphs/plugin-implementation/SelfComplementGraph/src/test/graph.txt");
        Graph graph = GraphFactory.loadGraphFromFile(file);
        System.out.println(new SelfComplementGraph().execute(graph));
    }
}
