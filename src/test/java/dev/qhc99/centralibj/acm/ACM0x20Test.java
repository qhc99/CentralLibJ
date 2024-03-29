package dev.qhc99.centralibj.acm;

import dev.qhc99.centralibj.algsJ.graph.*;
import dev.qhc99.centralibj.algsJ.numeric.NumberTheory;
import dev.qhc99.centralibj.utils.ArrayUtils;
import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ACM0x20Test{
  static final int iteration = 5;

  LinkedGraph<BaseVert<Integer>, BaseEdge<BaseVert<Integer>>> tsCase;

  {
    List<BaseVert<Integer>> vs = new ArrayList<>(4);
    vs.add(new BaseVert<>(1));
    vs.add(new BaseVert<>(2));
    vs.add(new BaseVert<>(3));
    vs.add(new BaseVert<>(4));
    tsCase = new LinkedGraph<>(true, vs);
    tsCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(1)));
    tsCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(2)));
    tsCase.addEdge(new BaseEdge<>(vs.get(2), vs.get(3)));

    tsCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(2)));
    tsCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(3)));

    tsCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(3)));
  }


  @Test
  void topologicalSortTest(){
    var ans = ACM0x20.topologicalSort(tsCase);
    assertEquals(4, ans.size());
    var vs = tsCase.allVertices();
    assertEquals(vs, ans);

    tsCase.addEdge(new BaseEdge<>(vs.get(3), vs.get(0)));
    var ans1 = ACM0x20.topologicalSort(tsCase);
    assertNotEquals(4, ans1.size());
  }

  LinkedGraph<BaseVert<Integer>, BaseEdge<BaseVert<Integer>>> reCase;

  {
    List<BaseVert<Integer>> vs = new ArrayList<>(4);
    vs.add(new BaseVert<>(1));
    vs.add(new BaseVert<>(2));
    vs.add(new BaseVert<>(3));
    vs.add(new BaseVert<>(4));
    reCase = new LinkedGraph<>(true, vs);
    reCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(1)));
    reCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(2)));
    reCase.addEdge(new BaseEdge<>(vs.get(2), vs.get(3)));

    reCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(2)));
    reCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(3)));

    reCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(3)));
  }

  @Test
  void reachabilityCountTest(){
    var ans = ACM0x20.reachabilityCount(reCase);
    var vs = reCase.allVertices();
    assertEquals(4, ans.get(vs.get(0)));
    assertEquals(3, ans.get(vs.get(1)));
    assertEquals(2, ans.get(vs.get(2)));
    assertEquals(1, ans.get(vs.get(3)));
  }

  @Test
  void additionChainsTest(){
    var ans1 = ACM0x20.additionChains(12);
    assertEquals(12, ans1.get(ans1.size() - 1));
    assertEquals(5, ans1.size());

    var ans2 = ACM0x20.additionChains(13);
    assertEquals(13, ans2.get(ans2.size() - 1));
    assertEquals(6, ans2.size());

    var ans3 = ACM0x20.additionChains(14);
    assertEquals(14, ans3.get(ans3.size() - 1));
    assertEquals(6, ans3.size());

    var ans4 = ACM0x20.additionChains(15);
    assertEquals(15, ans4.get(ans4.size() - 1));
    assertEquals(6, ans4.size());

    var ans5 = ACM0x20.additionChains(16);
    assertEquals(16, ans5.get(ans5.size() - 1));
    assertEquals(5, ans5.size());
  }

  int[][] sgCases = new int[iteration][];
  int[] sgAnswers = new int[iteration];

  {
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < iteration; i++){
      sgCases[i] = ArrayUtils.randomIntArray(0, Integer.MAX_VALUE - 1, 10 + rand.nextInt( 45-10));
      Set<Integer> set = new HashSet<>(1);
      set.add(0);
      for(int j = 0; j < sgCases[i].length; j++){
        Set<Integer> next = new HashSet<>(set.size() * 2);
        next.addAll(set);
        for(var t : set){
          if(NumberTheory.addNotOverflow(t, sgCases[i][j])){
            next.add(t + sgCases[i][j]);
          }
        }
        set = next;
      }
      //noinspection OptionalGetWithoutIsPresent
      sgAnswers[i] = set.stream().max(Integer::compareTo).get();
    }
  }

  @Test
  void sendGiftTest(){
    for(int i = 0; i < iteration; i++){
      Assertions.assertEquals(sgAnswers[i], ACM0x20.sendGift(sgCases[i]));
    }
  }

  LinkedGraph<BFS.Vert<Integer>, WeightEdge<BFS.Vert<Integer>>> kmpCase;
  List<BFS.Vert<Integer>> kmpVs;
  LinkedGraph<BFS.Vert<Integer>, WeightEdge<BFS.Vert<Integer>>> kmpCaseReverse;

  {
    kmpVs = new ArrayList<>(4);
    kmpVs.add(new BFS.Vert<>(1));
    kmpVs.add(new BFS.Vert<>(2));
    kmpVs.add(new BFS.Vert<>(3));
    kmpVs.add(new BFS.Vert<>(4));
    kmpCase = new LinkedGraph<>(true, kmpVs);
    kmpCase.addEdge(new WeightEdge<>(kmpVs.get(0), kmpVs.get(1), 1));
    kmpCase.addEdge(new WeightEdge<>(kmpVs.get(1), kmpVs.get(0), 2));
    kmpCase.addEdge(new WeightEdge<>(kmpVs.get(1), kmpVs.get(2), 3));
    kmpCase.addEdge(new WeightEdge<>(kmpVs.get(2), kmpVs.get(1), 4));
    kmpCase.addEdge(new WeightEdge<>(kmpVs.get(2), kmpVs.get(3), 5));
    kmpCase.addEdge(new WeightEdge<>(kmpVs.get(3), kmpVs.get(2), 6));

    kmpCaseReverse = new LinkedGraph<>(true, kmpVs);
    kmpCaseReverse.addEdge(new WeightEdge<>(kmpVs.get(1), kmpVs.get(0), 1));
    kmpCaseReverse.addEdge(new WeightEdge<>(kmpVs.get(0), kmpVs.get(1), 2));
    kmpCaseReverse.addEdge(new WeightEdge<>(kmpVs.get(2), kmpVs.get(1), 3));
    kmpCaseReverse.addEdge(new WeightEdge<>(kmpVs.get(1), kmpVs.get(2), 4));
    kmpCaseReverse.addEdge(new WeightEdge<>(kmpVs.get(3), kmpVs.get(2), 5));
    kmpCaseReverse.addEdge(new WeightEdge<>(kmpVs.get(2), kmpVs.get(3), 6));

  }

  @Test
  void kthMinPathTest(){
    Assertions.assertEquals(9., ACM0x20.kthMinPath(kmpCase, kmpCaseReverse, kmpVs.get(0), kmpVs.get(3), 1));
    kmpVs.forEach(BFS.Vert::refresh);
    Assertions.assertEquals(12., ACM0x20.kthMinPath(kmpCase, kmpCaseReverse, kmpVs.get(0), kmpVs.get(3), 2));
    kmpVs.forEach(BFS.Vert::refresh);
    Assertions.assertEquals(15., ACM0x20.kthMinPath(kmpCase, kmpCaseReverse, kmpVs.get(0), kmpVs.get(3), 3));
    kmpVs.forEach(BFS.Vert::refresh);
    Assertions.assertEquals(16., ACM0x20.kthMinPath(kmpCase, kmpCaseReverse, kmpVs.get(0), kmpVs.get(3), 4));
  }

  

  private static boolean checkNPuzzle(ACM0x20.NPuzzle[] ans){
    for(int i = 0; i < ans.length - 1; i++){
      var a = ans[i];
      var b = ans[i + 1];
      var ap = a.getSpaceIndex();
      var bp = b.getSpaceIndex();
      int apr = ap.first();
      int apc = ap.second();
      int bpr = bp.first();
      int bpc = bp.second();

      if(apr == bpr && !(Math.abs(apc - bpc) == 1)){
        return false;
      }
      else if(apc == bpc && !(Math.abs(apr - bpr) == 1)){
        return false;
      }
    }
    return ans[ans.length - 1].solved();
  }

  @Test
  void nPuzzleTest(){
    var nPuzzleCase = new ACM0x20.NPuzzle(10);
    while(nPuzzleCase.solved()) {
      nPuzzleCase = new ACM0x20.NPuzzle(10);
    }
    var ans = ACM0x20.eight(nPuzzleCase);
    assertTrue(checkNPuzzle(ans));
  }

  static final int bookSortIteration = 5;
  int[][] bookSortCases = new int[bookSortIteration][];

  {
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < bookSortIteration; i++){
      var t = IntStream.range(0, rand.nextInt(15 - 7) + 7).toArray();
      while(isSorted(t)){
        for(int j = 0; j < 4; j++){
          int len = rand.nextInt(t.length - 1) + 1;
          int s1 = rand.nextInt(t.length - len);
          int s2;
          do{
            s2 = rand.nextInt(t.length - len + 1);
          }
          while(s1 >= s2);
          ACM0x20.backwardsExchangeBooks(t, s1, len, s2);
        }
      }
      bookSortCases[i] = t;
    }
  }

  static boolean isSorted(int[] res){
    boolean is_sorted = true;
    for(int i = 1; i < res.length; i++){
      if(res[i - 1] > res[i]){
        is_sorted = false;
        break;
      }
    }
    return is_sorted;
  }

  @Test
  void bookSortTest(){
    for(int i = 0; i < bookSortIteration; i++){
      var c = bookSortCases[i];
      var ans = ACM0x20.bookSort(c);
      assertTrue(isSorted(c));
      assertTrue(ans.size() <= 4);
      assertTrue(ans.size() != 0);
    }
  }
}
