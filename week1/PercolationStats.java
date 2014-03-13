/**
 * Created by Superman Petrovich on 2/20/14.
 */
public class PercolationStats {
    private double results[];
    private double mean = -1;
    private double stddev = -1;

    public PercolationStats(int N, int T){
        if(N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }

        results = new double[T];

        while (T > 0){
            int counter = 0;
            Percolation p = new Percolation(N);

            while (!p.percolates()){
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);

                if(!p.isOpen(i,j)){
                    p.open(i,j);
                    counter++;
                }
            }

            results[--T] = counter / Math.pow(N, 2);
        }
    }

    public double mean(){
        if(mean == -1){
            mean = StdStats.mean(results);
        }

        return mean;
    }

    public double stddev(){
        if(stddev == -1){
            stddev = StdStats.stddev(results);
        }

        return stddev;
    }

    public double confidenceLo(){
        return mean() - ((1.96 * stddev()) / Math.sqrt(results.length));
    }

    public double confidenceHi(){
        return mean() + ((1.96 * stddev()) / Math.sqrt(results.length));
    }

    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats perStat = new PercolationStats(N, T);
        double mean = perStat.mean();
        double std = perStat.stddev();
        double lo = perStat.confidenceLo();
        double hi = perStat.confidenceHi();

        StdOut.printf("mean = %s\n", mean);
        StdOut.printf("stddev = %s\n", std);
        StdOut.printf("95 confidence interval = %s, %s", lo, hi);
    }
}
