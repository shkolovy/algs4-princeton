/**
 * Created by Superman Petrovich on 3/6/14.
 */
public class KdTree {
    private int N;
    private Node root;

    public KdTree() {
        N = 0;
        root = null;
    }

    public int size(){
        return N;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public boolean contains(Point2D p){
        return get(root, p, true) != null;
    }

    public Point2D nearest(Point2D p){
        if(isEmpty()){
            return null;
        }

        return findNearest(root, root, p, true).p;
    }

    public Iterable<Point2D> range(RectHV rect){
        Stack<Point2D> result = new Stack<Point2D>();

        findRange(root, rect, result, true);

        return result;
    }

    public void insert(Point2D p){
        if(root == null){
            root = new Node();
            root.p = p;
            root.rect = new RectHV(0, 0, 1, 1);
            N++;
        }
        else{
            boolean isOdd = true;
            Node n = root;

            while (true){

                if(n.p.equals(p)){
                    return;
                }

                if(isOdd){
                    if(p.x() < n.p.x()){
                        if(n.lb == null){
                            n.lb = new Node();
                            n.lb.p = p;
                            n.lb.rect = new RectHV(n.rect.xmin(), n.rect.ymin(),
                                    n.p.x(), n.rect.ymax());

                            N++;
                            break;
                        }
                        else{
                            isOdd = isOdd ? false : true;
                            n = n.lb;
                        }
                    }
                    else{
                        if(n.rt == null){
                            n.rt = new Node();
                            n.rt.p = p;
                            n.rt.rect = new RectHV(n.p.x(), n.rect.ymin(),
                                    n.rect.xmax(), n.rect.ymax());

                            N++;
                            break;
                        }
                        else{
                            isOdd = isOdd ? false : true;
                            n = n.rt;
                        }
                    }
                }
                else{
                    if(p.y() < n.p.y()){
                        if(n.lb == null){
                            n.lb = new Node();
                            n.lb.p = p;
                            n.lb.rect = new RectHV(n.rect.xmin(), n.rect.ymin(),
                                    n.rect.xmax(), n.p.y());

                            N++;
                            break;
                        }
                        else{
                            isOdd = isOdd ? false : true;
                            n = n.lb;
                        }
                    }
                    else {
                        if(n.rt == null){
                            n.rt = new Node();
                            n.rt.p = p;
                            n.rt.rect = new RectHV(n.rect.xmin(), n.p.y(),
                                    n.rect.xmax(), n.rect.ymax());

                            N++;
                            break;
                        }
                        else{
                            isOdd = isOdd ? false : true;
                            n = n.rt;
                        }
                    }
                }
            }
        }
    }

    public void draw(){
        StdDraw.setPenColor(StdDraw.BLACK);
        new RectHV(0,0,1,1).draw();

        draw(root, true);
    }

    private void findRange(Node n, RectHV rect, Stack<Point2D> points, boolean isVertical){
        if(n == null){
            return;
        }

        if(rect.contains(n.p)){
            points.push(n.p);

            findRange(n.lb, rect, points, !isVertical);
            findRange(n.rt, rect, points, !isVertical);
        }
        else if(n.rect.intersects(rect)){
            if(isVertical){
                if(n.p.x() - rect.xmin() <= 0){
                    findRange(n.rt, rect, points, false);
                }
                else if(n.p.x() - rect.xmax() <= 0){
                    findRange(n.lb, rect, points, false);
                    findRange(n.rt, rect, points, false);
                }
                else{
                    findRange(n.lb, rect, points, false);
                }
            }
            else{
                if(n.p.y() - rect.ymin() <= 0){
                    findRange(n.rt, rect, points, true);
                }
                else if(n.p.y() - rect.ymax() <= 0)
                {
                    findRange(n.lb, rect, points, true);
                    findRange(n.rt, rect, points, true);
                }
                else{
                    findRange(n.lb, rect, points, true);
                }
            }
        }
    }

    private Node findNearest(Node n, Node nearest, Point2D p, boolean isVertical){
        if(n == null){
            return nearest;
        }

        if(n.p.distanceSquaredTo(p) < nearest.p.distanceSquaredTo(p)){
            nearest = n;
        }

        if(nearest.p.distanceSquaredTo(p) > n.rect.distanceSquaredTo(p)){
            Node firstToFind, secondToFind;

            if(isVertical){
                if(p.x() < n.p.x()){
                    firstToFind = n.lb;
                    secondToFind = n.rt;
                }
                else{
                    firstToFind = n.rt;
                    secondToFind = n.lb;
                }
            }
            else{
                if(p.y() < n.p.y()){
                    firstToFind = n.lb;
                    secondToFind = n.rt;
                }
                else {
                    firstToFind = n.rt;
                    secondToFind = n.lb;
                }
            }

            nearest = findNearest(firstToFind, nearest, p, !isVertical);
            nearest = findNearest(secondToFind, nearest, p, !isVertical);
        }

        return nearest;
    }

    private void draw(Node n, boolean isVertical){
        if(n != null){
            drawPointWithLine(n, isVertical);
            draw(n.lb, !isVertical);
            draw(n.rt, !isVertical);
        }
    }

    private Node get(Node n, Point2D p, boolean isVertical){
        if(n == null){
            return null;
        }

        if(n.p.equals(p)){
            return n;
        }

        if(isVertical){
            if(p.x()< n.p.x()){
                return get(n.lb, p, !isVertical);
            }
            else{
                return get(n.rt, p, !isVertical);
            }
        }
        else{
            if(p.y()< n.p.y()){
                return get(n.lb, p, !isVertical);
            }
            else{
                return get(n.rt, p, !isVertical);
            }
        }
    }

    private void drawPointWithLine(Node n, boolean isVertical){
        //line
        StdDraw.setPenRadius();
        if(isVertical){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymax(), n.p.x(), n.rect.ymin());
        }
        else{
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        n.p.draw();
    }

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }
}