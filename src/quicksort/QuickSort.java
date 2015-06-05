/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quicksort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author igor
 */
public class QuickSort {

    static int length = 10000;
    static int a[] = new int[length];
    static int counter = 0;

    /**
     * @param args the command line arguments
     */
    public void Partition(int p, int r) {
        int changer = 0;

        int d = (p + r) / 2;
        int b[] = new int[3];
        b[0] = a[p];
        b[1] = a[r];
        b[2] = a[d];
        Arrays.sort(b);

        if (b[1] == a[d]) {
            changer = a[d];
            a[d] = a[r];
            a[r] = changer;
        }
        if (b[1] == a[p]) {
            changer = a[p];
            a[p] = a[r];
            a[r] = changer;
        }

        counter += r - p;
        int x = a[r];
        int i = p - 1;

        for (int j = p; j <= (r - 1); j++) {
            if (a[j] <= x) {
                i++;
                changer = a[i];
                a[i] = a[j];
                a[j] = changer;
            }
        }
        changer = a[i + 1];
        a[i + 1] = a[r];
        a[r] = changer;
        if ((i - p) > 0) {
            Partition(p, i);
        }
        if ((r - i - 2) > 0) {
            Partition(i + 2, r);
        }

    }

    public void Init() {
        File file = new File("input__" + length + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = -1;
            while ((line = br.readLine()) != null) {
                if (count >= 0) {
                    a[count] = Integer.parseInt(line);
                }
                count++;
            }
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        QuickSort test = new QuickSort();
        test.Init();
        test.Partition(0, length - 1);
        System.out.println(counter);
    }

}
