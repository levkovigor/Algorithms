/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_dfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author igor
 */
public class GraphDFS {

    /**
     * @param args the command line arguments
     */
    ArrayList<Tuple> graph = new ArrayList<>();
    ArrayList<Tuple> graph_trans = new ArrayList<>();
    ArrayList<Vector> graph_n = new ArrayList<>();
    ArrayList<Vector> graph_t_n = new ArrayList<>();
    ArrayList<Integer> p = new ArrayList<>();
    ArrayList<Integer> p2 = new ArrayList<>();
    ArrayList<Integer> points = new ArrayList<>();
    ArrayList<Integer> result = new ArrayList<>();
    int max = 0;
    int t = 0;

    public void Build(ArrayList<Tuple> graph, ArrayList<Vector> graph_n) {
        for (Tuple v : graph) {
            if (graph_n.get(v.getX()) == null) {
                Vector vec = new Vector();
                vec.setY(v.getY());
                graph_n.set(v.getX(), vec);
            } else {
                graph_n.get(v.getX()).setY(v.getY());
            }
        }
    }

    public class Vector {

        ArrayList<Integer> y = new ArrayList<>();

        public ArrayList<Integer> getY() {
            return this.y;
        }

        public void setY(int y) {
            this.y.add(y);
        }

        public String toString() {
            return "(" + this.y + ")";
        }
    }

    public class Tuple {

        int x, y;

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }

    }

    public void Transponse() {

        for (Tuple tuple : graph) {
            Tuple tuple_new = new Tuple();
            tuple_new.setX(tuple.getY());
            tuple_new.setY(tuple.getX());
            graph_trans.add(tuple_new);
        }
    }

    public void Init() {
        File file = new File("data_08\\input_08" + ".txt");
        //File file = new File("test_08\\test_08_4"+".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String m[] = line.split(" ");
                Tuple tuple = new Tuple();
                tuple.setX(Integer.parseInt(m[0]));
                tuple.setY(Integer.parseInt(m[1]));
                graph.add(tuple);
                tuple = new Tuple();
                tuple.setX(Integer.parseInt(m[1]));
                tuple.setY(Integer.parseInt(m[0]));
                graph_trans.add(tuple);
                if (max < tuple.getX()) {
                    max = tuple.getX();
                }
                if (max < tuple.getY()) {
                    max = tuple.getY();
                }
            }
           for (int i = 0; i <= max; i++) {
            graph_n.add(null);
            graph_t_n.add(null);
            p.add(0);
            p2.add(0);
        }
        } catch (IOException ex) {
        }
    }

    public void DFS() {
        for (int i = 1; i < graph_n.size(); i++) {
            if (p.get(i) == 0) {
                DFSR_cycle(i);
            }
        }
    }

    public void DFS_Reverse() {
        t = 0;
        Collections.reverse(points);
        for (int i : points) {
            if (p2.get(i) == 0) {
                DFSR_Reverse_cycle(i);
                result.add(t);
                t = 0;
            }
        }
    }

    public void DFSR(int i) {
        p.set(i, 1);
        if (graph_n.get(i) != null) {
            Vector v = graph_n.get(i);
            for (int k : v.getY()) {
                if (p.get(k) != 1) {
                    DFSR(k);
                }
            }
        }
        points.add(i);
    }

    public void DFSR_Reverse(int i) {
        t++;
        p2.set(i, 1);
        if (graph_t_n.get(i) != null) {
            Vector v = graph_t_n.get(i);
            for (int k : v.getY()) {
                if (p2.get(k) == 0) {
                    DFSR_Reverse(k);
                }
            }
        }
    }

    public void DFSR_cycle(int i) {
        ArrayList<Integer> st = new ArrayList<>();
        st.add(i);
        while (st.size() > 0) {
            i = st.get(st.size() - 1);
            p.set(i, 1);
            if (graph_n.get(i) != null) {
                Vector v = graph_n.get(i);
                boolean a = false;
                for (int k : v.getY()) {
                    if (p.get(k) != 1) {
                        a = true;
                        st.add(k);
                        p.set(i, 1);
                        break;
                    }
                }
                if (a == false) {
                    points.add(i);
                    st.remove(st.size() - 1);
                }
            } else {
                points.add(i);
                st.remove(st.size() - 1);
            }
        }
    }

    public void DFSR_Reverse_cycle(int i) {

        ArrayList<Integer> st = new ArrayList<>();
        st.add(i);
        while (st.size() > 0) {
            i = st.get(st.size() - 1);
            p2.set(i, 1);
            if (graph_t_n.get(i) != null) {
                Vector v = graph_t_n.get(i);
                boolean a = false;
                for (int k : v.getY()) {
                    if (p2.get(k) != 1) {
                        a = true;
                        st.add(k);
                        p2.set(i, 1);
                        break;
                    }
                }
                if (a == false) {
                    t++;
                    st.remove(st.size() - 1);
                }
            } else {
                t++;
                st.remove(st.size() - 1);
            }
        }
    }

    public ArrayList<Integer> StrongConnectedComponent() {
        Init();
        Build(graph, graph_n);
        Build(graph_trans, graph_t_n);
        DFS();
        DFS_Reverse();
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        GraphDFS test = new GraphDFS();
        System.out.println(test.StrongConnectedComponent());
    }

}
