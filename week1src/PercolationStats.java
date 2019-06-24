import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double[] ratio;
    private int n, trials;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.n = n;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Both n and trials should be positive integers");
        ratio = new double[trials];
        for (int i = 0; i < trials; ++i) {
            ratio[i] = percolationThreshold();
        }

    }

    private double percolationThreshold() {
        Percolation perc = new Percolation(n);
        int count = 0;
        int i, j;
        while (!perc.percolates()) {
            do {
                i = StdRandom.uniform(1, n+1);
                j = StdRandom.uniform(1, n+1);
            }
            while (perc.isOpen(i, j));

            perc.open(i, j);
            count += 1;
        }
        return (double) count/(Math.pow(n,2));

    }

    public double mean() {
        return StdStats.mean(ratio);
    }

    public double stddev() {
        return StdStats.stddev(ratio);
    }

    public double confidenceLo() {
        return (mean() - (1.96*stddev())/Math.sqrt(trials));
    }

    public double confidenceHi() {
        return (mean() + (1.96*stddev())/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        PercolationStats stats = new PercolationStats(200, 1000);
        StdOut.println(timer.elapsedTime());
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());

    }
}
