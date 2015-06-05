/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author igor
 */
public class HashSearch {

    Hashtable<Long, Long> data = new Hashtable<>();

    /**
     * @param args the command line arguments
     */
    public void Init() {
        File file = new File("data_06\\input_06.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.put(Long.parseLong(line), Long.parseLong(line));
            }
        } catch (IOException ex) {
        }
    }

    public void Do() {
        int count = 0;
        for (int i = -1000; i <= 1000; i++) {
            for (Long key : data.keySet()) {
                if (data.containsKey(i - key)) {
                    count++;
                    break;
                }
            }
            //System.out.println(i);
        }
        System.out.println(count);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashSearch test = new HashSearch();
        test.Init();
        test.Do();

    }

}
