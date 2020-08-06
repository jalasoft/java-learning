package cz.jalasoft.dsa.search;

import java.util.Arrays;

/**
 * @author Jan "Honzales" Lastovicka
 */
public class BinarySearch {

    public static int searchRecursively(int key, int[] a) {
        return searchRecursively(key, a, 0, a.length-1);
    }

    private static int searchRecursively(int key, int[] a, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        if (a[mid] == key) {
            return mid;
        }

        if (key > a[mid]) {
            return searchRecursively(key, a, mid + 1, high);
        }

        return searchRecursively(key, a, low, mid - 1);
    }

    public static int searchIteratively(int key, int[] a) {

        int low = 0;
        int high = a.length-1;

        while(low <= high) {
            int mid = low + (high - low) / 2;

            if (key < a[mid]) {
                high = mid - 1;
            } else if (key > a[mid]) {
                low= mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] array = {3, 6, 1, 5, 9, 12, 2, 2, 7, 4};
        int key = 5;

        Arrays.sort(array);

        //int index = searchIteratively(8, array);
        int index = searchRecursively(key, array);

        System.out.println(Arrays.toString(array));
        System.out.println("Index prvku " + key + ": " + index);
    }
}
