import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main_prog {

    public static void main(String... args) {
        // For Testing part 1
//        int n = 10;
//        int M = 100;
//        int[] efforts = {23,23,28,28,13,5,21,25,31,5};                      // part 1
        System.out.println("Provide number of dishes n ?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println("Provide total efforts in minute M ?");
        int M = scanner.nextInt();
        List<Integer> mlist = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Provide effort for dish " + (i+1) + "?");
            mlist.add(scanner.nextInt());
        }
        int[] efforts = mlist.stream().mapToInt(i -> i).toArray();        //  conversion of list to array

        System.out.println("Unsorted efforts = " + Arrays.toString(efforts));
        main_prog mainprog = new main_prog();
        mainprog.solveGreedy(efforts);
        System.out.println("Sorted efforts = " + Arrays.toString(efforts));

        //Adding efforts upto given M
        int sumeffort = 0;
        int pointbreak = 0;
        for (int i = 0; i < n; i++) {
            sumeffort += efforts[i];
            if (sumeffort > M) {
                sumeffort -= efforts[i];
                pointbreak = i - 1;
                break;
            }
        }
        System.out.print("Minimum effort for part 1 = " + sumeffort + " = ");
        for (int i = 0; i < pointbreak; i++) {
            System.out.print(efforts[i] + " ");
        }
        System.out.println();
        System.out.println("----- Part 1 completed -----");


        // For Testing part 2
//        int[] points = new int[]{8, 2, 7, 4, 5, 3, 8, 2, 2, 10};                    // part 2
//        int[] efforts2 = new int[]{15, 9, 22, 25, 25, 30, 26, 4, 29, 39};                  // part 2
        System.out.println("----- Part 2 inputs ----");
        Scanner scanner2 = new Scanner(System.in);
        List<Integer> plist = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Provide point for dish " + (i+1) + "?");
            plist.add(scanner2.nextInt());
        }
        List<Integer> mlist2 = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Provide effort for dish " + (i+1) + "?");
            mlist2.add(scanner2.nextInt());
        }
        int[] points = plist.stream().mapToInt(i -> i).toArray();            // conversion of list to array
        int[] efforts2 = mlist2.stream().mapToInt(i -> i).toArray();

        mainprog.solveDP(n, M, points, efforts2);
    }


    // heap sort implementation
    public void solveGreedy(int[] efforts) {
        // build maxheap
        for (int i = efforts.length / 2 - 1; i >= 0; i--)
            heap(efforts, efforts.length, i);

        // extract elements sequentially
        for (int i = efforts.length - 1; i > 0; i--) {
            swap(efforts, i, 0);
            heap(efforts, i, 0);
        }
    }

    void heap(int[] efforts, int n, int i) {
        int highest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // left child is larger than root and left child index not out of heap index
        if (left < n && efforts[left] > efforts[highest])
            highest = left;

        // right child is larger than highest and right child index not out of heap index
        if (right < n && efforts[right] > efforts[highest])
            highest = right;

        // highest is not root
        if (highest != i) {
            swap(efforts, i, highest);
            heap(efforts, n, highest);          // recursive
        }
    }

    public void swap(int[] efforts, int i, int j) {
        int swap = efforts[i];
        efforts[i] = efforts[j];
        efforts[j] = swap;
    }

    // maximum value of knapsack of capacity timeAllocated
    public void solveDP(int n, int M, int[] points, int[] efforts) {
        int totalEffort;
        int P = 0;
        for (int i = 0; i < n; i++) {
            P += points[i];
        }
        int[][] T = new int[n + 1][P + 1];

        for (int i = 0; i <= n; i++) {
            for (int p = 0; p <= P; p++) {
                if (i == 0 && p == 0)
                    T[i][p] = 0;
                else if (p == 0)
                    T[i][p] = 0;
                else if (i == 0)
                    T[i][p] = 99999;
                else
                    T[i][p] = 99999;                    //  init value
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int p = 1; p <= P; p++) {
                if (p < points[i - 1]) {
                    T[i][p] = T[i - 1][p];
                } else {
                    int m0 = T[i - 1][p];
                    int m1 = efforts[i - 1] + T[i - 1][p - points[i - 1]];
                    if (m1 > M)
                        T[i][p] = m0;
                    else
                        T[i][p] = Math.min(m0, m1);
                }
            }
        }

        System.out.print("T[n][p] array = ");
        for (int[] row : T)
            System.out.println(Arrays.toString(row));

        boolean breakflag = false;
        for (int i = n; i >= 0; i--) {
            for (int p = P; p >= 0; p--) {
                if (T[i][p] != 99999) {
                    System.out.println(T[i][p] + " " + i + " " + p);
                    System.out.println("Maximum points found = " + p);
                    System.out.println("Minimum effort found = " + T[i][p]);
                    breakflag = true;
                    break;
                }
            }
            if (breakflag)
                break;
        }

//        // part 2 extended
//        for (int i = n; i >= 0; i--) {
//            for (int p = P; p >= 0; p--) {
//                if (T[i][p] != 99999) {
//                    int effort = T[i][p];
//                    System.out.println("Minimum effort found = " + T[i][p]);
//                    breakflag = true;
//                    break;
//                }
//            }
//            if (breakflag)
//                break;
//        }
    }
}
