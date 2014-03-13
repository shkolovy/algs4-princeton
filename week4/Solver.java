/**
 * Created by Superman Petrovich on 3/4/14.
 */
public class Solver {
    private final Board initialBoard;
    private Node searchedNode;
    private boolean isSolvable = true;

    public Solver(Board initial) {
        this.initialBoard = initial;
        solve();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
//        int m1 = solver.moves();
//        solver.isSolvable();
//        Iterable<Board> sol1 = solver.solution();
//        solver.isSolvable();
//        int m2 = solver.moves();
//        solver.isSolvable();
//        Iterable<Board> sol2 = solver.solution();

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    public Iterable<Board> solution() {
        if(!isSolvable){
            return null;
        }

        Stack<Board> result = new Stack<Board>();

        Node t = searchedNode;

        while (t != null) {
            result.push(t.board);
            t = t.parent;
        }

        return result;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        if(isSolvable){
            return searchedNode.moves;
        }

        return -1;
    }

    private void solve() {
        MinPQ<Node> pq = new MinPQ<Node>();
        MinPQ<Node> twinPq = new MinPQ<Node>();
        searchedNode = new Node(initialBoard, null);
        Node twinNode = new Node(initialBoard.twin(), null);

        while (true) {
            if(searchedNode.board.isGoal()){
                break;
            }

            searchedNode = process(searchedNode, pq);

            if(twinNode.board.isGoal()){
                isSolvable = false;
                break;
            }

            twinNode = process(twinNode, twinPq);
        }
    }

    private Node process(Node node, MinPQ<Node> pq)
    {
        Iterable<Board> n = node.board.neighbors();

        for (Board b : n) {
            if (node.parent == null || !node.parent.board.equals(b)) {
                pq.insert(new Node(b, node));
            }
        }

        return pq.delMin();
    }

    private class Node implements Comparable {
        private Board board;
        private Node parent;
        private int moves;
        private int priority;
        private int manhattan;

        private Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
            moves = parent == null ? 0 : parent.moves + 1;
            manhattan = board.manhattan();
            priority = manhattan + moves;
        }

        @Override
        public int compareTo(Object that) {
            if (this.priority > ((Node) that).priority) {
                return 1;
            }
            else if (this.priority < ((Node) that).priority) {
                return -1;
            }

            return 0;
        }
    }
}
