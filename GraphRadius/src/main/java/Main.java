import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Graph;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        File file1 = new File("C:/Users/bobyl/Desktop/univer/mlita/graphs/plugin-implementation/GraphRadius/src/test/test1.txt");
        try {
            Graph graph1 = GraphFactory.loadGraphFromFile(file1);
            System.out.println(new GraphRadius().execute(graph1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
