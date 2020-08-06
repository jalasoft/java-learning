package cz.jalasoft.dsa.sort;

/**
 * @author Jan "Honzales" Lastovicka
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] array = {3, 56, 23, 78, 67, 45, 12, 45,3};

        print(array);
        sort(array);
        print(array);
    }

    public static void sort(int[] a) {

        for(int i=a.length-1; i >= 0; i--) {
            //najdu index nejvetsiho prvku
            int max = a[i];
            int maxIndex = i;
            for(int j=0;j<=i;j++) {
                if (a[j] > max) {
                    maxIndex = j;
                    max = a[j];
                }
            }

            //prohodim
            int acc = a[i];
            a[i] = a[maxIndex];
            a[maxIndex] = acc;
        }
    }

    private static void print(int[] a) {
        for(var p: a) {
            System.out.print("" + p + " ");
        }
        System.out.println();
    }
}
