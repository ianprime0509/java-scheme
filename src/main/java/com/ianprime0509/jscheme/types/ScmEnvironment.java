package com.ianprime0509.jscheme.types;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ScmEnvironment implements ScmValue {
  private final ScmEnvironment parent;

  private final Map<ScmSymbol, ScmValue> bindings;

  private ScmEnvironment(ScmEnvironment parent, Map<ScmSymbol, ScmValue> bindings) {
    if (bindings == null) {
      throw new IllegalArgumentException("bindings must not be null");
    }
    this.parent = parent;
    this.bindings = new HashMap<>(bindings);
  }

  public static ScmEnvironment empty() {
    return of(null, Collections.emptyMap());
  }

  public static ScmEnvironment of(Map<ScmSymbol, ScmValue> bindings) {
    return of(null, bindings);
  }

  public static ScmEnvironment of(ScmEnvironment parent, Map<ScmSymbol, ScmValue> bindings) {
    return new ScmEnvironment(parent, bindings);
  }

  public void define(ScmSymbol name, ScmValue value) {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null");
    }
    if (value == null) {
      throw new IllegalArgumentException("value must not be null");
    }
    bindings.put(name, value);
  }

  public Optional<ScmValue> get(ScmSymbol name) {
    ScmEnvironment current = this;
    ScmValue value = bindings.get(name);
    while (value == null) {
      if (current.parent == null) {
        return Optional.empty();
      }
      current = current.parent;
      value = current.bindings.get(name);
    }
    return Optional.of(value);
  }

  @Override
  public String toString() {
    return bindings.toString() + (parent != null ? " -> " + parent.toString() : "");
  }
}
