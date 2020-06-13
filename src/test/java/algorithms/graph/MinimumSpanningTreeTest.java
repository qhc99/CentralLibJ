package algorithms.graph;

import org.junit.jupiter.api.Test;
import tools.Graph;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MinimumSpanningTreeTest {
    static Graph<MinimumSpanningTree.KruskalVertex<String>> buildKruskalExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MinimumSpanningTree.KruskalVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i, new MinimumSpanningTree.KruskalVertex<>(names[i])); }
        Graph<MinimumSpanningTree.KruskalVertex<String>> res = new Graph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            res.putNeighborPair(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]);
        }
        return res;
    }
    static Set<Graph<MinimumSpanningTree.KruskalVertex<String>>.Edge> buildKruskalAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MinimumSpanningTree.KruskalVertex<String>>(len);
        Graph<MinimumSpanningTree.KruskalVertex<String>> g = new Graph<>();
        for(int i = 0; i < len; i++){ vertices.add(i,new MinimumSpanningTree.KruskalVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        Set<Graph<MinimumSpanningTree.KruskalVertex<String>>.Edge> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for(var i : answers)
            res.add(g.new Edge(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]));
        return res;
    }
    @Test
    public void algorithmOfKruskalTest(){
        var t = MinimumSpanningTree.algorithmOfKruskal(buildKruskalExample());
        assertEquals(t, buildKruskalAnswer());
        int i = 0;
        for(var e : t){
            i += e.getWeight();
        }
        assertEquals(i, 37);
    }


    static Graph<MinimumSpanningTree.PrimVertex<String>> buildPrimExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MinimumSpanningTree.PrimVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MinimumSpanningTree.PrimVertex<>(names[i])); }
        Graph<MinimumSpanningTree.PrimVertex<String>> res = new Graph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            res.putNeighborPair(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]);
        }
        return res;
    }
    static Set<Set<MinimumSpanningTree.PrimVertex<String>>> buildPrimAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MinimumSpanningTree.PrimVertex<String>>(len);
        Graph<MinimumSpanningTree.KruskalVertex<String>> g = new Graph<>();
        for(int i = 0; i < len; i++){ vertices.add(i,new MinimumSpanningTree.PrimVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        Set<Set<MinimumSpanningTree.PrimVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for (int answer : answers) {
            Set<MinimumSpanningTree.PrimVertex<String>> t = new HashSet<>();
            t.add(vertices.get(indexes1[answer]));
            t.add(vertices.get(indexes2[answer]));
            res.add(t);
        }
        return res;
    }
    @Test
    public void algorithmOfPrimTest(){
        var graph = MinimumSpanningTree.algorithmOfPrim(buildPrimExample(), new MinimumSpanningTree.PrimVertex<>("a"));
        var vertices = graph.getAllVertices();
        Set<Set<MinimumSpanningTree.PrimVertex<String>>> res = new HashSet<>();
        for(var vertex : vertices){
            if(vertex.parent != null){
                Set<MinimumSpanningTree.PrimVertex<String>> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertEquals(buildPrimAnswer(), res);
    }
}