package tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DPMatrice<V> {//matrice for DP problem
    private Object[][] matrice = null;

    public DPMatrice(int size) {
        matrice = new Object[size][];
        for(int i = 0; i < size; i ++)
            matrice[i] = new Object[size-i];
    }

    @SuppressWarnings("unchecked")
    public V getAt(int r, int c){ return (V)matrice[r][c-r]; }

    public void setAt(V v, int r, int c) { matrice[r][c-r] = v; }
}