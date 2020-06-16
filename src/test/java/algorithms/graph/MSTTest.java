package algorithms.graph;

import org.junit.jupiter.api.Test;
import tools.Graph;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MSTTest {
    static Graph<MST.KruskalVertex<String>> buildKruskalExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.KruskalVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i, new MST.KruskalVertex<>(names[i])); }
        Graph<MST.KruskalVertex<String>> res = new Graph<>(false);
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            res.setNeighbor(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]);
        }
        return res;
    }
    static Set<Graph<MST.KruskalVertex<String>>.Edge> buildKruskalAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.KruskalVertex<String>>(len);
        Graph<MST.KruskalVertex<String>> g = new Graph<>(false);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.KruskalVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
//        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        Set<Graph<MST.KruskalVertex<String>>.Edge> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for(var i : answers)
            res.add(g.new Edge(vertices.get(indexes1[i]), vertices.get(indexes2[i])));
        return res;
    }
    @Test
    public void algorithmOfKruskalTest(){
        var G = buildKruskalExample();
        var t = MST.algorithmOfKruskal(G);
        assertEquals(t, buildKruskalAnswer());
        int i = 0;
        for(var e : t){
            i += G.computeWeight(e);
        }
        assertEquals(i, 37);
    }


    static Graph<MST.PrimVertex<String>> buildPrimExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.PrimVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.PrimVertex<>(names[i])); }
        Graph<MST.PrimVertex<String>> res = new Graph<>(false);
        int[] indices1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indices2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indices1.length;
        for(int i = 0; i < len_; i++){
            res.setNeighbor(vertices.get(indices1[i]), vertices.get(indices2[i]), weights[i]);
        }
        return res;
    }
    static Set<Set<MST.PrimVertex<String>>> buildPrimAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.PrimVertex<String>>(len);
        Graph<MST.KruskalVertex<String>> g = new Graph<>(false);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.PrimVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        Set<Set<MST.PrimVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for (int answer : answers) {
            Set<MST.PrimVertex<String>> t = new HashSet<>();
            t.add(vertices.get(indexes1[answer]));
            t.add(vertices.get(indexes2[answer]));
            res.add(t);
        }
        return res;
    }
    @Test
    public void algorithmOfPrimTest(){
        var graph = MST.algorithmOfPrim(buildPrimExample(), new MST.PrimVertex<>("a"));
        var vertices = graph.getAllVertices();
        Set<Set<MST.PrimVertex<String>>> res = new HashSet<>();
        for(var vertex : vertices){
            if(vertex.parent != null){
                Set<MST.PrimVertex<String>> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertEquals(buildPrimAnswer(), res);
    }
}