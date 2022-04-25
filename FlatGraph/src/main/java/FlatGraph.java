import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FlatGraph implements GraphProperty {
    @Override
    public boolean execute(Graph graph) {

        List<Segment> segments = new ArrayList<>();
        Map<UUID, Vertex> vertexes = graph.getVertices();

        for (Edge edge: graph.getEdges()) {
            Vertex fromV = vertexes.get(edge.getFromV());
            Vertex toV = vertexes.get(edge.getToV());

            segments.add(new Segment(
                    new Point(fromV.getXCoordinate(), fromV.getYCoordinate()),
                    new Point(toV.getXCoordinate(), toV.getYCoordinate())
            ));
        }

        for (Segment segment1: segments) {
            for (Segment segment2 : segments) {
                if (segment1 != segment2) {
                    if (intersect(segment1.a, segment1.b, segment2.a, segment2.b) && !checkCommonPoint(segment1, segment2)) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    static boolean checkCommonPoint(Segment segment1, Segment segment2) {
        Point a = segment1.a;
        Point b = segment1.b;
        Point c = segment2.a;
        Point d = segment2.b;

        return ((a.x == c.x) && (a.y == c.y)) || ((a.x == d.x) && (a.y == d.y)) ||
                ((b.x == c.x) && (b.y == c.y)) || ((b.x == d.x) && (b.y == d.y));
    }
    static int area (Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    static boolean intersect_1 (int a, int b, int c, int d) {
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (c > d) {
            int tmp = c;
            c = d;
            d = tmp;
        }
        return max(a,c) <= min(b,d);
    }

    static boolean intersect (Point a, Point b, Point c, Point d) {
        return intersect_1 (a.x, b.x, c.x, d.x)
                && intersect_1 (a.y, b.y, c.y, d.y)
                && area(a,b,c) * area(a,b,d) <= 0
                && area(c,d,a) * area(c,d,b) <= 0;
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Segment {
        Point a;
        Point b;

        public Segment(Point a, Point b) {
            this.a = a;
            this.b = b;
        }
    }

}
