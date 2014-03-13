/**
 * Created by Superman Petrovich on 2/13/14.
 */

public class Subset {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }

        for (int i = 0; i < k; i++) {
            StdOut.printf("%s\n", rq.dequeue());
        }
    }
}