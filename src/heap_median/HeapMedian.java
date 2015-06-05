/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap_median;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author igor
 */
public class HeapMedian {

    static final int length = 10000;
    static Integer a[] = new Integer[length];
    static int counter = 0;
    static ArrayList<Integer> heap_low, heap_high;

    /**
     * @param args the command line arguments
     */
    public void Init() {
        File file = new File("data_examples_05\\input_16_" + length + ".txt");
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

    public void MaxHeapify(int x) {
        int l = 2 * x;
        int r = 2 * x + 1;
        int lar = x;
        if (l < heap_high.size()) {
            if (heap_high.get(l) > heap_high.get(x)) {
                lar = l;
            }
            if (r < heap_high.size()) {
                if (heap_high.get(r) > heap_high.get(lar)) {
                    lar = r;
                }
            }
        }
        if (lar != x) {
            Integer i = heap_high.get(x);
            heap_high.set(x, heap_high.get(lar));
            heap_high.set(lar, i);
            MaxHeapify(lar);
        }

    }

    public void MinHeapify(int x) {

        int l = 2 * x;
        int r = 2 * x + 1;
        int lar = x;
        if (l < heap_low.size()) {
            if (heap_low.get(l) < heap_low.get(x)) {
                lar = l;
            }
            if (r < heap_low.size()) {
                if (heap_low.get(r) < heap_low.get(lar)) {
                    lar = r;
                }
            }
        }
        if (lar != x) {
            Integer i = heap_low.get(x);
            heap_low.set(x, heap_low.get(lar));
            heap_low.set(lar, i);
            MinHeapify(lar);
        }

    }

    public void BuildMaxHeap() {
        for (int i = ((heap_high.size() - 1) / 2); i > 0; i--) {
            MaxHeapify(i);
        }
    }

    public void BuildMinHeap() {
        for (int i = ((heap_low.size() - 1) / 2); i > 0; i--) {
            MinHeapify(i);
        }
    }

    public void HeapInsertMin() {
        int i = heap_low.size() - 1;
        while ((i != 1) && (heap_low.get(i) < heap_low.get(i / 2))) {
            Integer k = heap_low.get(i);
            heap_low.set(i, heap_low.get(i / 2));
            heap_low.set(i / 2, k);
            i = i / 2;

        }
    }

    public void HeapInsertMax() {
        int i = heap_high.size() - 1;
        while ((i != 1) && (heap_high.get(i) > heap_high.get(i / 2))) {
            Integer k = heap_high.get(i);
            heap_high.set(i, heap_high.get(i / 2));
            heap_high.set(i / 2, k);
            i = i / 2;
        }
    }

    public void CreateHeaps() {
        int count = 1;
        heap_high = new ArrayList<>();
        heap_low = new ArrayList<>();
        heap_low.add(0);
        heap_high.add(0);
        heap_high.add(a[0]);
        for (int i = 1; i < length; i++) {
            count++;
            if (a[i] < heap_high.get(1)) {
                heap_high.add(a[i]);
                HeapInsertMax();
                if ((heap_high.size() - heap_low.size()) == 2) {
                    heap_low.add(heap_high.get(1));
                    heap_high.set(1, heap_high.get(heap_high.size() - 1));
                    heap_high.remove(heap_high.size() - 1);
                    MaxHeapify(1);
                    HeapInsertMin();
                }
            } else {
                heap_low.add(a[i]);
                HeapInsertMin();
                if ((heap_low.size() - heap_high.size()) == 2) {
                    heap_high.add(heap_low.get(1));
                    heap_low.set(1, heap_low.get(heap_low.size() - 1));
                    heap_low.remove(heap_low.size() - 1);
                    MinHeapify(1);
                    HeapInsertMax();
                }
            }
            if (count == 9876) {
                System.out.println(heap_high.get(1) + " " + heap_high.get(2) + " " + heap_high.get(3) + " " + heap_high.get(4) + " " + heap_high.get(5));
                System.out.println(heap_low.get(1) + " " + heap_low.get(2) + " " + heap_low.get(3) + " " + heap_low.get(4) + " " + heap_low.get(5));
            }
            if (count == 2015) {
                System.out.println(heap_high.get(1) + " " + heap_high.get(2) + " " + heap_high.get(3) + " " + heap_high.get(4) + " " + heap_high.get(5));
                System.out.println(heap_low.get(1) + " " + heap_low.get(2) + " " + heap_low.get(3) + " " + heap_low.get(4) + " " + heap_low.get(5));
            }
            /*  System.out.println(heap_high.toString());
             System.out.println(heap_low.toString());
             System.out.println("");*/
        }

    }

    public static void main(String[] args) {

        HeapMedian test = new HeapMedian();
        test.Init();
        test.CreateHeaps();
    }

}
