import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;

import java.io.File;
import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        var file = new File("C:/Users/Владислав/Downloads/graph (13).txt");
        GraphFactory graphFactory = new GraphFactory();
        var undirGraph = GraphFactory.loadGraphFromFile(file);
        var vec = new OrientedTournament();
        System.out.print(vec.execute(undirGraph));
    }
}
