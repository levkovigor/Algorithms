/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radixsort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author igor
 */
public class RadixSort {

    /**
     * @param args the command line arguments
     */
    static final String letters = "abcdefghijklmnopqrstuvwxyz";
    static char key[] = letters.toCharArray();
    static final int length = 1000;
    static String a[] = new String[length];

    public void Init() {
        File file = new File("anagrams.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                a[count] = (line);
                count++;
            }
        } catch (IOException ex) {

        }
    }

    public String[] CountingSort(String[] a_old, int i) {

        ArrayList<Character> keys = new ArrayList<>();
        String a_new[] = new String[length];
        int C[] = new int[key.length];
        int C2[] = new int[key.length];
        for (int j = 0; j < key.length; j++) {
            C[j] = 0;
            C2[j] = 0;
            keys.add(key[j]);
        }
        for (String b : a_old) {
            char c[] = b.toCharArray();
            C[keys.indexOf(c[i])]++;
        }
        for (int j = 0; j < key.length; j++) {
            int sum = 0;
            for (int k = 0; k <= j; k++) {
                sum += C[k];
            }
            C2[j] = sum;
        }
        for (int j = a_old.length - 1; j >= 0; j--) {
            char c[] = a_old[j].toCharArray();
            a_new[C2[keys.indexOf(c[i])] - 1] = a_old[j];
            C2[keys.indexOf(c[i])]--;
        }
        return a_new;
    }

    public char CountMax() {
        ArrayList<Character> keys = new ArrayList<>();
        int C[] = new int[key.length];
        for (int j = 0; j < key.length; j++) {
            C[j] = 0;
            keys.add(key[j]);
        }
        for (String a1 : a) {
            char[] c = a1.toCharArray();
            for (char k : c) {
                C[keys.indexOf(k)]++;
            }
        }
        int max = 0;
        int ind = 0;
        for (int i = 0; i < C.length; i++) {
            if (C[i] > max) {
                max = C[i];
                ind = i;
            }
        }
        return key[ind];
    }

    public static void main(String[] args) {
        RadixSort test = new RadixSort();
        test.Init();
        String out[] = (test.CountingSort(test.CountingSort(test.CountingSort(a, 2), 1), 0));
        System.out.println(out[0] + test.CountMax() + out[length - 1]);

    }

}
