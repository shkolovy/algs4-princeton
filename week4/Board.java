/**
 * Created by Superman Petrovich on 2/28/14.
 */
public class Board {
    private final int N;
    private final int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = cloneBlocks(blocks);
        this.N = this.blocks.length;
    }

    public int dimension() {
        return this.N;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public int hamming() {
        int k = 1, r = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != k) {
                    r++;
                }
                k++;
            }
        }

        return r;
    }

    public int manhattan() {
        int r = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0) {
                    int expi = (blocks[i][j] - 1) / N;
                    int expj = (blocks[i][j] - 1) % N;
                    int disi = i - expi;
                    int disj = j - expj;

                    r += Math.abs(disi) + Math.abs(disj);
                }
            }
        }

        return r;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> result = new Stack<Board>();
        int zeroi = 0, zeroj = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                    break;
                }
            }
        }

        //find left
        if (zeroi - 1 >= 0) {
            int[][] a = cloneBlocks(blocks);
            swap(a, zeroi, zeroj, zeroi - 1, zeroj);
            result.push(new Board(a));
        }

        //find right
        if (zeroi + 1 < blocks.length) {
            int[][] a = cloneBlocks(blocks);
            swap(a, zeroi, zeroj, zeroi + 1, zeroj);
            result.push(new Board(a));
        }

        //find top
        if (zeroj - 1 >= 0) {
            int[][] a = cloneBlocks(blocks);
            swap(a, zeroi, zeroj, zeroi, zeroj - 1);
            result.push(new Board(a));
        }

        //find bottom
        if (zeroj + 1 < blocks.length) {
            int[][] a = cloneBlocks(blocks);
            swap(a, zeroi, zeroj, zeroi, zeroj + 1);
            result.push(new Board(a));
        }

        return result;
    }

    public Board twin(){
        int[][] tb = cloneBlocks(blocks);

        int i = 0, j = 0;

        if (tb[i][j] != 0 && tb[i][j+1] != 0){
            swap(tb, i, j, i, j + 1);
        }
        else{
            swap(tb, i + 1, j, i + 1, j+1);
        }

        return new Board(tb);
    }

    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (that.getClass() != this.getClass()) {
            return false;
        }

        Board thatBoard = (Board) that;

        if(thatBoard.N != this.N){
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (thatBoard.blocks[i][j] != this.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    private void swap(int[][] blocks, int i1, int j1, int i2, int j2) {
        int t = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = t;
    }

    private int[][] cloneBlocks(int[][] blocks) {
        int[][] result = new int[blocks.length][blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                result[i][j] = blocks[i][j];
            }
        }

        return result;
    }
}
