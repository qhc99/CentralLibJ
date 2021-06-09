package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class WeightEdge<V extends Vertex<?>> extends BasicEdge<V>{

  double weight;

  public WeightEdge(@NotNull V f, @NotNull V l, double weight){
    super(f, l);
    this.weight = weight;
  }

  double weight(){
    return weight;
  }
}
