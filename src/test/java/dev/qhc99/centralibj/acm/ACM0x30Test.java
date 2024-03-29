package dev.qhc99.centralibj.acm;

import dev.qhc99.centralibj.algsJ.numeric.NumberTheory;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import org.junit.jupiter.api.Test;
import dev.qhc99.centralibj.utils.tuples.Tuple;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ACM0x30Test{
  static final int iteration = 30;

  List<Tuple<Integer, Integer>> pdCases;
  IntList primes;
  int[] pdAnswers = new int[iteration];

  {
    primes = NumberTheory.primesGenEulerSieve((int) Math.pow(10, 4));
    pdCases = new ArrayList<>(iteration);
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < iteration; i++){
      var s = rand.nextInt(primes.size() / 2);
      var e = rand.nextInt(primes.size() - primes.size() / 2) + primes.size() / 2;
      pdCases.add(new Tuple<>(s, e));
      int max = -1;
      for(int j = s; j < e; j++){
        var a = primes.getInt(j);
        var b = primes.getInt(j + 1);
        max = Math.max(max, b - a);
      }
      pdAnswers[i] = max;
    }
  }


  @Test
  void primeDistanceTest(){
    for(int i = 0; i < iteration; i++){
      var t = pdCases.get(i);
      var a = t.first();
      var b = t.second();
      var ans = ACM0x30.primeDistance(primes.getInt(a), primes.getInt(b));
      assertEquals(pdAnswers[i], ans);
    }
  }

  int[] fpfCases = new int[iteration];
  List<Map<Integer, Integer>> fpfAnswers = new ArrayList<>(iteration);
  {
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < iteration; i++){
      fpfCases[i] = rand.nextInt(100 - 5) + 5;
      fpfAnswers.add(fpfSolve(fpfCases[i]));
    }
  }

  static Map<Integer, Integer> fpfSolve(int N){
    var primes = NumberTheory.primesGenEulerSieve(N);
    Map<Integer, Integer> ans = new HashMap<>(primes.size());
    for(int i = 2; i <= N; i++){
      var f = NumberTheory.primeFactorizationPollardsRho(i);
      for(var p : f){
        ans.put(p, ans.getOrDefault(p, 0) + 1);
      }
    }

    return ans;
  }

  @Test
  void factorialPrimeFactorizationTest(){
    for(int i = 0; i < iteration; i++){
      var ans = ACM0x30.factorialPrimeFactorization(fpfCases[i]);
      assertEquals(fpfAnswers.get(i), ans);
    }
  }
}