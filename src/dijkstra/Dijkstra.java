/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

/**
 *
 * @author igor
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Vertex implements Comparable<Vertex> {

    public final Integer name;
    public ArrayList<Edge> adjacencies = new ArrayList<>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public ArrayList<Integer> prev = new ArrayList<>();

    public Vertex(Integer argName) {
        name = argName;
    }

    public String toString() {
        return name.toString();
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

}

class Tuple implements Comparable<Tuple> {

    int x, y;

    public Tuple() {

    }

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

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

    public int compareTo(Tuple compareTuple) {
        int compare = compareTuple.getY();
        return this.y - compare;
    }
}

class Edge {

    public final Vertex target;
    public final int weight;

    public Edge(Vertex argTarget, int argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}

public class Dijkstra {

    ArrayList<Vertex> list = new ArrayList<>();
    Map<String, Integer> hash = new HashMap<>();
    int max;

    public void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                int weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public int getMinPathLength(int start, int end) {
        computePaths(list.get(start));
        Vertex v = list.get(end);
        return (int) v.minDistance;
    }

    public void computeAllMinPaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                int weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU == v.minDistance) {
                    vertexQueue.remove(v);
                    try {
                        v.prev.add(v.previous.name);
                    } catch (Exception e2) {
                    }
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
        for (int i = 1; i <= max; i++) {
            Vertex v = list.get(i);
            try {
                v.prev.add(v.previous.name);
            } catch (Exception e) {
            }
        }
    }

    public int getAllMinPaths(int start, int end) {
        computeAllMinPaths(list.get(start));
        ArrayList<Integer> st = new ArrayList<>();
        Map<Integer, Integer> p = new HashMap<>();
        st.add(list.get(end).name);
        p.put(list.get(end).name, 0);
        Map<String, Integer> ways = new HashMap<>();
        Map<String, Integer> st2 = new HashMap<>();
        while (st.size() > 0) {
            int i = st.get(st.size() - 1);
            boolean a = false;
            for (Integer j : list.get(i).prev) {
                if (j == start) {
                    st.add(j);
                    int length = 0;
                    int l = 0;
                    while (l + 1 < st.size()) {
                        length += hash.get(new Tuple(st.get(l + 1), st.get(l)).toString());
                        l++;
                    }
                    ways.put(st.toString(), length);
                    st.remove(st.size() - 1);
                } else {
                    if (p.get(j) == null) {
                        st.add(j);
                        if (st2.get(st.toString()) != null) {
                            st.remove(st.size() - 1);
                        } else {
                            p.put(j, 0);
                            a = true;
                            break;
                        }
                    }
                }
            }
            if (a == false) {
                st2.put(st.toString(), 0);
                p.put(st.get(st.size() - 1), null);
                st.remove(st.size() - 1);
            }
        }
        int count = 0;
        for (Map.Entry<String, Integer> k : ways.entrySet()) {
            count++;
        }
        return count;
    }

    public List<Vertex> getShortestPathTo(Vertex start, Vertex end) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = end; vertex != start; vertex = vertex.previous) {
            path.add(vertex);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    public void init(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            GraphViz gv = new GraphViz();
            gv.addln(gv.start_digraph());

            while ((line = br.readLine()) != null) {
                String m[] = line.split(" ");
                if (count == 0) {
                    max = Integer.parseInt(m[0]);
                    list.add(null);
                    for (Integer i = 1; i <= max; i++) {
                        list.add(new Vertex(i));
                    }
                } else {
                    gv.addln(m[0] + " -> " + m[1] + " [color=red,label=\"" + m[2] + "\"];");
                    hash.put(new Tuple(Integer.parseInt(m[0]), Integer.parseInt(m[1])).toString(), Integer.parseInt(m[2]));
                    list.get(Integer.parseInt(m[0])).adjacencies.add(new Edge(list.get(Integer.parseInt(m[1])), Integer.parseInt(m[2])));
                }
                count++;
            }
            gv.addln(gv.end_graph());
            gv.getDotSource();

            //String type = "gif";
            //String type = "dot";
            //String type = "fig";    // open with xfig
            String type = "pdf";
            //String type = "ps";
            //String type = "svg";    // open with inkscape
            //String type = "png";
            //String type = "plain";
            //File out = new File("out." + type);   // Linux
            File out = new File("c:/out." + type);    // Windows
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);

        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        Dijkstra test = new Dijkstra();
        /*File file = new File("USA-FLA.txt");
         int start = 100562;
         int end = 1070345;*/
        int start = 1;
        int end = 7;
        File file = new File("test_09\\input_9_10" + ".txt");

        test.init(file);
        System.out.println("Length: " + test.getMinPathLength(start, end));
        System.out.println("Ways: " + test.getAllMinPaths(start, end));
    }
}
