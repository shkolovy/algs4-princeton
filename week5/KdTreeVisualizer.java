import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Created by Superman Petrovich on 3/6/14.
 */
public class KdTreeVisualizer {
    public static void main(String[] args) {
        StdDraw.show(0);
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                StdDraw.clear();
                kdtree.draw();
            }
            StdDraw.show(50);
        }

    }
}
