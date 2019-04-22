import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Values should be >=1");
        }

        double[] ratioArr = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            double openSiteCount = 0;
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    openSiteCount++;
                }
            }
            ratioArr[i] = openSiteCount/(n*n);
        }
        this.mean = StdStats.mean(ratioArr);
        if (trials == 1) {
            this.stddev = Double.NaN;
            this.confidenceLo = Double.NaN;
            this.confidenceHi = Double.NaN;
        }else{
            this.stddev = StdStats.stddev(ratioArr);
            this.confidenceLo = this.mean - 1.96 * Math.sqrt(stddev*stddev / trials);
            this.confidenceHi = this.mean + 1.96 * Math.sqrt(stddev*stddev / trials);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
       return this.stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {

        int n= StdIn.readInt();

        int trials = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(n,trials);

        StdOut.println("mean = " + percolationStats.mean());

        StdOut.println("stddev = " + percolationStats.stddev());

        StdOut.println("95% confidence interval = ["+ percolationStats.confidenceLo()
                +", "+percolationStats.confidenceHi()+"]");

        //StdOut.println("confidenceHigh() = " + percolationStats.confidenceHi());

    }
}