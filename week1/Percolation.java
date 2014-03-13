/**
 * Created by Superman Petrovich on 2/20/14.
 */

public class Percolation {
    private int n;
    private int top, bottom;

    private boolean[] gridStatus;
    private WeightedQuickUnionUF union; //has 2 extra items 0 and n (if n connected to 0 the system is percolated)
    private WeightedQuickUnionUF union1;

    public Percolation(int N) {
        n = N;
        gridStatus = new boolean[N*N];

        int unionLength = N * N + 2;
        union = new WeightedQuickUnionUF(unionLength);
        union1 = new WeightedQuickUnionUF(unionLength);

        top = 0;
        bottom = unionLength - 1;

        for (int i = 0; i < N * N; i++) {
            gridStatus[i] = false;
        }
    }

    public void open(int i, int j) {
        validateParams(i, j);

        if (!gridStatus[getIndex(i, j)]) {
            gridStatus[getIndex(i, j)] = true;

            int item = getIndex(i, j);

            //union with left
            if (j > 1) {
                if (gridStatus[getIndex(i, j - 1)]) {
                    union.union(item, getIndex(i, j - 1));
                    union1.union(item, getIndex(i, j - 1));
                }
            }

            //union with right
            if (j < n) {
                if (gridStatus[getIndex(i, j + 1)]) {
                    union.union(item, getIndex(i, j + 1));
                    union1.union(item, getIndex(i, j + 1));
                }
            }

            //union with top
            if (i > 1) {
                if (gridStatus[getIndex(i - 1, j)]) {
                    union.union(item, getIndex(i - 1, j));
                    union1.union(item, getIndex(i - 1, j));
                }
            }
            else {
                union.union(top,item);
                union1.union(top,item);
            }

            //union with bottom
            if (i < n) {
                if (gridStatus[getIndex(i + 1, j)]) {
                    union.union(item, getIndex(i + 1, j));
                    union1.union(item, getIndex(i + 1, j));
                }
            }
            else {
                union.union(bottom, item);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        validateParams(i, j);

        return gridStatus[getIndex(i, j)];
    }

    //opened and connected to top (root) item
    public boolean isFull(int i, int j) {
        return isOpen(i, j) && union1.connected(getIndex(i,j), top);
    }

    //is system percolated
    public boolean percolates() {
        return union.connected(top, bottom);
    }

    private void validateParams(int i, int j) {
        if (i <= 0 || i > n || j <= 0 || j > n) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int getIndex(int i, int j){
        return n * (i - 1) + (j - 1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        p.open(2, 2);
        p.open(3, 3);
        p.open(3, 2);
        p.open(2, 1);
        p.open(3, 1);

        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 3);
        p.open(2, 1);
        p.open(2, 2);
        p.open(2, 3);
        p.open(3, 1);
        p.open(3, 2);
        p.open(3, 3);
    }
}