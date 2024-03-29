package dev.qhc99.centralibj.algsJ.numeric;

import it.unimi.dsi.fastutil.ints.IntArrayList;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

public class NumberTheory{
  /**
   * @param x x
   * @param y y
   * @return addition not overflow
   * @see Math#addExact(int, int)
   */
  public static boolean addNotOverflow(int x, int y){
    int r = x + y;
    // HD 2-12 Overflow iff both arguments have the opposite sign of the result
    return !(((x ^ r) & (y ^ r)) < 0);
  }

  /**
   *
   * @param x x
   * @param y y
   * @return multiply not overflow
   * @see Math#multiplyExact(int, int)
   */
  public static boolean multiplyNotOverflow(int x, int y){
    long r = (long) x * (long) y;
    return (int) r == r;
  }

  /**
   * @param limit inclusive
   * @return list of primes
   */
  public static IntArrayList primesGenEulerSieve(int limit){
    IntArrayList primes = new IntArrayList((int) (limit / Math.log(limit)));
    boolean[] visit = new boolean[limit + 1];
    for(int i = 2; i <= limit; i++){
      if(!visit[i]){ primes.add(i); }
      var len = primes.size();
      for(int j = 0; j < len && multiplyNotOverflow(i, primes.getInt(j)) && i * primes.getInt(j) <= limit; j++){
        visit[i * primes.getInt(j)] = true;
        if(i % primes.getInt(j) == 0)//关键
        { break; }
      }
    }
    return primes;
  }

  private final static BigInteger ZERO = new BigInteger("0");
  private final static BigInteger ONE = new BigInteger("1");
  private final static BigInteger TWO = new BigInteger("2");
  private final static SecureRandom random = new SecureRandom();
  private final static SplittableRandom sRandom = new SplittableRandom();

  private static BigInteger rho(BigInteger N){
    BigInteger divisor;
    BigInteger c = new BigInteger(N.bitLength(), random);
    BigInteger x = new BigInteger(N.bitLength(), random);
    BigInteger xx = x;

    if(N.mod(TWO).compareTo(ZERO) == 0){ return TWO; }

    do{
      x = x.multiply(x).mod(N).add(c).mod(N);
      xx = xx.multiply(xx).mod(N).add(c).mod(N);
      xx = xx.multiply(xx).mod(N).add(c).mod(N);
      divisor = x.subtract(xx).gcd(N);
    }
    while((divisor.compareTo(ONE)) == 0);

    return divisor;
  }

  private static int bitLength(int i){
    var t = i < 0 ? -i : i + 1;
    return (int) Math.ceil(Math.log(t) / Math.log(2));
  }

  public static int gcd(int a, int b){
    return b != 0 ? gcd(b, a % b) : a;
  }

  public static int lcm(int a, int b){
    return a * b / gcd(a,b);
  }

  /**
   * euler function
   * @param n number
   * @return count of integer which prime with n
   */
  public static int phi(int n){
    var primes = primeFactorizationPollardsRho(n);
    int ans = n;
    for(var p : primes){
      ans /= p * (p - 1);
    }
    return ans;
  }

  public static int rho(int N){
    int divisor;
    int bLen = (int) Math.pow(2, bitLength(N));
    int c = sRandom.nextInt(bLen);
    int x = sRandom.nextInt(bLen);
    int xx = x;

    if(N % 2 == 0){ return 2; }

    do{
      x = (((x * x) % N) + c) % N;
      xx = (((xx * xx) % N) + c) % N;
      xx = (((xx * xx) % N) + c) % N;
      divisor = gcd(x - xx, N);
    }
    while(divisor == 1);

    return Math.abs(divisor);
  }

  public static List<BigInteger> primeFactorizationPollardsRho(BigInteger N){
    if(N.compareTo(ZERO) <= 0){
      throw new IllegalArgumentException();
    }
    List<BigInteger> ans = new ArrayList<>();
    var funcFactor = new Object(){
      void apply(BigInteger N){
        if(N.compareTo(ONE) == 0){ return; }
        if(N.isProbablePrime(20)){
          ans.add(N);
          return;
        }
        BigInteger divisor = rho(N);
        apply(divisor);
        apply(N.divide(divisor));
      }
    };
    funcFactor.apply(N);
    return ans;
  }

  public static int powerMod(int a, int b, int m){
    int ans = 1 % m;
    while(b != 0) {
      if((b & 1) == 1){
        ans = (int) ((long) a * ans % m);
      }
      a = (int) ((long) a * a % m);
      b = b >>> 1;
    }
    return ans;
  }

  public static boolean primeTestMillerRabin(int n, int k){
    var funcTest = new Object(){
      boolean apply(int d, int n){
        int a = 2 + (int) (sRandom.nextDouble() % (n - 4));
        int x = powerMod(a, d, n);

        if(x == 1 || x == n - 1){ return true; }

        while(d != n - 1) {
          x = (x * x) % n;
          d *= 2;

          if(x == 1){ return false; }
          if(x == n - 1){ return true; }
        }

        return false;
      }
    };

    if(n <= 1 || n == 4){ return false; }
    if(n <= 3){ return true; }
    int d = n - 1;

    while(d % 2 == 0) { d /= 2; }

    for(int i = 0; i < k; i++){
      if(!funcTest.apply(d, n)){ return false; }
    }

    return true;

  }

  public static IntArrayList primeFactorizationPollardsRho(int N){
    if(N <= 0){
      throw new IllegalArgumentException();
    }
    var ans = new IntArrayList();
    var funcFactor = new Object(){
      void apply(int N){
        if(N == 1){ return; }
        if(primeTestMillerRabin(N, 20)){
          ans.add(N);
          return;
        }
        int divisor = rho(N);
        apply(divisor);
        apply(N / divisor);
      }
    };
    funcFactor.apply(N);
    return ans;
  }

  /**
   * <pre>
   * example:28 -> 1,2,4,7,14,28
   * </pre>
   *
   * @param num number
   * @return all divisors of number
   */
  public static  List<Integer> allDivisorsOf(int num){
    List<Integer> primeFactors = primeFactorizationPollardsRho(num);
    List<Integer> l = new ArrayList<>(16);
    l.add(1);

    l.addAll(primeFactors.stream().distinct().toList());
    for(int len = 2; len < primeFactors.size(); len++){
      for(int idx = 0; idx < primeFactors.size() - len + 1; idx++){
        recursiveGetDivisors(idx + 1, primeFactors.get(idx), len - 1, l, primeFactors);
      }
    }
    l.add(num);
    return l.stream().distinct().collect(Collectors.toList());
  }

  private static void recursiveGetDivisors(int idx, int mul, int len, List<Integer> l, List<Integer> primeFactors){
    if(len == 1){
      for(; idx < primeFactors.size(); idx++){
        l.add(mul * primeFactors.get(idx));
      }
    }
    else{
      for(; idx < primeFactors.size() - len + 1; idx++){
        recursiveGetDivisors(idx + 1, primeFactors.get(idx) * mul, len - 1, l, primeFactors);
      }
    }
  }

}
