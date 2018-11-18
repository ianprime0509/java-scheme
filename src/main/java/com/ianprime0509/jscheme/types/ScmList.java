package com.ianprime0509.jscheme.types;

import java.util.List;

class ScmList implements ScmPair {
  private final List<ScmValue> values;

  ScmList(final List<ScmValue> values) {
    if (values.size() < 1) {
      throw new IllegalArgumentException("list must contain at least one element");
    }
    this.values = values;
  }

  @Override
  public ScmValue getCar() {
    return values.get(0);
  }

  @Override
  public ScmValue getCdr() {
    return values.size() == 1 ? ScmNil.get() : new ScmList(values.subList(1, values.size()));
  }
}
