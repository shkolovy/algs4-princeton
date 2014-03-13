import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Created by Superman Petrovich on 2/22/14.
 */
public class Main {
    public static void main(String[] args) {

        KdTree kdTree = getKdTree(args);
       drawRangeKdTreePoints(kdTree);
       //drawNearestKdTreePoints(kdTree);
        //drawKdTreePoints(kdTree);



//        KdTree kdTree = new KdTree();
//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.5, 0.4));
//        kdTree.insert(new Point2D(0.2, 0.3));
//        kdTree.insert(new Point2D(0.4, 0.7));
//        kdTree.insert(new Point2D(0.9, 0.6));
//
//        int size = kdTree.size();
//
//        kdTree.insert(new Point2D(0.9, 0.6));
//
//        int size1 = kdTree.size();

//
//        Point2D nearest = kdTree.nearest(new Point2D(0.5, 0.8));
//
//        kdTree.draw();
//
//        boolean con = kdTree.contains(new Point2D(0.7, 0.4));

        //PointSET brute = getBrute(args);
        //drawBrutePoints(brute);
        //drawNearestBrutePoints(brute);
        //drawRangeBrutePoints(brute);
    }

    //region kdTree test
    private static KdTree getKdTree(String[] args){
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        KdTree kdTree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
        }

        return kdTree;
    }

    private static void drawKdTreePoints(KdTree kdTree){
        kdTree.draw();
    }

    private static void drawNearestKdTreePoints(KdTree kdTree){
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            kdTree.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            kdTree.nearest(query).draw();
            StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }

    private static void drawRangeKdTreePoints(KdTree kdTree){
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        kdTree.draw();

        while (true) {
            StdDraw.show(40);

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            kdTree.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : kdTree.range(rect))
                p.draw();

            StdDraw.show(40);
        }
    }
    //endregion


    //region Brute test
    private static PointSET getBrute(String[] args){
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        return brute;
    }

    private static void drawBrutePoints(PointSET brute){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        brute.draw();
    }

    private static void drawRangeBrutePoints(PointSET brute){
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        brute.draw();

        while (true) {
            StdDraw.show(40);

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            StdDraw.show(40);
        }
    }

    private static void drawNearestBrutePoints(PointSET brute){
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
    //endregion
}
