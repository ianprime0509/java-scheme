package com.ianprime0509.jscheme.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ScmPair extends ScmValue {
  static ScmPair ofList(ScmValue... values) {
    return new ScmList(Arrays.asList(values));
  }

  static ScmPair ofList(List<ScmValue> values) {
    return new ScmList(new ArrayList<>(values));
  }

  ScmValue getCar();

  ScmValue getCdr();

  default boolean isList() {
    ScmPair pair = this;
    while (pair.getCdr() instanceof ScmPair) {
      pair = (ScmPair) pair.getCdr();
    }
    return pair.getCdr() instanceof ScmNil;
  }
}
