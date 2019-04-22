import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int size;
    private WeightedQuickUnionUF wquf;
    private int numberOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("Grid size should be at least 1x1");
        grid = new int[n + 2][n + 2];
        wquf = new WeightedQuickUnionUF(n * n + 2);
        this.size = n;
    }

    private enum JoinType {
        LEFT, UP, RIGHT, DOWN
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkIndices(i, j);
        if (!isOpen(i, j)) {
            grid[i][j] = 1;
            numberOfOpenSites++;
            checkAndJoinIndex(JoinType.LEFT, i, j);
            checkAndJoinIndex(JoinType.UP, i, j);
            checkAndJoinIndex(JoinType.RIGHT, i, j);
            checkAndJoinIndex(JoinType.DOWN, i, j);
            if(i== 1 && i ==size){
                wquf.union(transformTo1D(i,j), 0);
                wquf.union(transformTo1D(i,j), size * size + 1);
            }
            else if (i == 1) {
                wquf.union(transformTo1D(i,j), 0);
            } else if (i == size) {
                wquf.union(transformTo1D(i,j), size * size + 1);
            }
        }
    }

    private void checkIndices(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IllegalArgumentException("Site indices should be between 1 and N");
        }
    }

    private void checkAndJoinIndex(JoinType joinType, int x, int y) {
        try {
            int currentIndex = transformTo1D(x, y);
            switch (joinType) {
                case UP:
                    if (isOpen(x - 1, y)) {
                        wquf.union(transformTo1D(x-1,y), currentIndex);
                    }
                    break;
                case LEFT:
                    if (isOpen(x, y - 1)) {
                        wquf.union(transformTo1D(x,y-1), currentIndex);
                    }
                    break;
                case DOWN:
                    if (isOpen(x + 1, y)) {
                        wquf.union(transformTo1D(x+1,y), currentIndex);
                    }
                    break;
                case RIGHT:
                    if (isOpen(x, y + 1)) {
                        wquf.union(transformTo1D(x,y+1), currentIndex);
                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            //skip
        }
    }

    private int transformTo1D(int x, int y) {
        return (x - 1) * size + y;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkIndices(i, j);
        if (this.grid[i][j] == 1) {
            return true;
        } else {
            return false;
        }
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkIndices(i, j);
        if (wquf.connected(0, (i - 1) * size + j)) {
            return true;
        } else {
            return false;
        }

    }

    // does the system percolate?
    public boolean percolates() {
        if (wquf.connected(0, size * size + 1)) {
            return true;
        } else {
            return false;
        }
    }

    public int numberOfOpenSites(){
        return this.numberOfOpenSites;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);

    }

}
