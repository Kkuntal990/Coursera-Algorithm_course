/* *****************************************************************************
 *  Name:Kuntal
 *  Date:22/07/2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] open;
    private int n, size;
    private int count = 0;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than 0");
        this.n = n;
        size = n * n;
        open = new int[size];
        for (int i = 0; i < size; ++i) {
            open[i] = 0;
        }
        uf = new WeightedQuickUnionUF(size + 2);


    }

    public void open(int row, int col) {
        check(row, col);
        if (!isOpen(row, col)) {
            int num = id(row, col);
            open[num] = 1;
            count += 1;
            connectToNeighbours(row, col);
        }

    }

    public boolean isOpen(int row, int col) {
        check(row, col);
        return (open[id(row, col)] == 1);
    }

    public boolean isFull(int row, int col) {
        check(row, col);
        return uf.connected(size, id(row, col));
    }

    public boolean percolates() {
        return uf.connected(size, size + 1);

    }

    public int numberOfOpenSites() {
        return count;
    }

    private void tryUnion(int i, int j, int presentSite) {
        if (isOpen(i, j)) uf.union(id(i, j), presentSite);

    }

    private void connectToNeighbours(int i, int j) {
        int index = id(i, j);
        if (j < n) tryUnion(i, j + 1, index);
        if (j > 1) tryUnion(i, j - 1, index);


        if (i < n) {
            tryUnion(i + 1, j, index);
        }
        else {
            uf.union(size+1, index);
        }

        if (i > 1) {
            tryUnion(i -1, j, index);
        }
        else {
            uf.union(size, index);
        }


    }

    private int id(int i, int j) {
        return n * (i - 1) + j - 1;
    }

    private void check(int i, int j) {
        if (i < 1 || i > n) throw new IllegalArgumentException("row index i out of bound");
        if (j < 1 || j > n) throw new IllegalArgumentException("row index j out of bound");


    }


    public static void main(String[] args) {

        Percolation perc = new Percolation(2);
        perc.open(1,1);
        perc.open(2,2);
        perc.open(1,2);

        int a = perc.numberOfOpenSites();
        boolean c = perc.isOpen(2,1);

        StdOut.println(c);
        StdOut.println(a);

    }
}
