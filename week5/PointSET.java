/**
 * Created by Superman Petrovich on 3/6/14.
 */
public class PointSET {
    private final SET<Point2D> collection;

    public PointSET(){
        collection = new SET<Point2D>();
    }

    public int size(){
        return collection.size();
    }

    public boolean isEmpty(){
        return collection.size() == 0;
    }

    public boolean contains(Point2D p){
        return collection.contains(p);
    }

    public void insert(Point2D p){
        collection.add(p);
    }

    public void draw(){
        for(Point2D p: collection){
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        if(isEmpty()){
            return null;
        }

        Stack<Point2D> r = new Stack<Point2D>();

        for(Point2D point: collection){
            if(rect.contains(point)){
                r.push(point);
            }
        }

        return r;
    }

    public Point2D nearest(Point2D p){
        if(isEmpty()){
            return null;
        }

        Point2D nearestPoint = null;

        for(Point2D np: collection){
            if(nearestPoint == null){
                nearestPoint = np;
            }
            else if(np.distanceTo(p) < nearestPoint.distanceTo(p)){
                nearestPoint = np;
            }
        }

        return nearestPoint;
    }
}
