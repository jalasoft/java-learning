package cz.jalasoft.dsa;

/**
 * @author Jan "Honzales" Lastovicka
 */
public class Matrices {

    public static void main(String[] args) {

        var m1 = new int[][] {
                {1, 2, 3, 4, 5, 6},
                {3, 5, 6, 7, 8, 3},
                {8, 8, 4, 2, 4, 1},
                {2, 5, 3, 1, 8, 4},
                {0, 5, 3, 7, 2, 6},
                {8, 4, 3, 8, 4, 1}
        };

        print(m1);
        var r = rotateLeft(m1);
        System.out.println();
        print(r);
    }

    private static int[][] rotateRight(int[][] m) {

        var result = initMatrix(m);

        for(int row=0;row<m.length;row++) {
            for(int col=0;col<m[row].length;col++) {
                var value = m[row][col];
                result[col][m.length-row-1] = value;
            }
        }
        return result;
    }

    private static int[][] initMatrix(int[][] m) {
        int [][] result = new int[m.length][];
        for (int row=0;row<m.length;row++) {
            result[row] = new int[m[row].length];
        }
        return result;
    }

    private static int[][] rotateLeft(int[][] m) {

        var result = initMatrix(m);

        for(var row=0;row<m.length;row++) {
            for(var col=0;col<m[row].length;col++) {
                result[m.length-1-col][row] = m[row][col];
            }
        }
        return result;
    }

    private static void print(int[][] m) {
        for(int row=0;row<m.length;row++) {
            for(int col=0; col<m[row].length; col++) {
                System.out.print("" + m[row][col] + " ");
            }
            System.out.println("");
        }
    }
}
