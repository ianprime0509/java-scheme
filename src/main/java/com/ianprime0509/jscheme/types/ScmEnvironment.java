package com.ianprime0509.jscheme.types;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A Scheme environment.
 *
 * <p>An <em>environment</em> consists of two parts: a set of bindings, associating certain symbols
 * (identifiers) with values, and a reference to a parent environment (if one exists). Attempts to
 * retrieve the value associated with a symbol involve first looking for a binding in the current
 * environment; if that fails, the parent environment's bindings are searched, and so on with each
 * successive parent until a value is found (or the symbol is determined to be unbound).
 *
 * <p>Environments in Scheme are mutable, because new bindings can be introduced using {@code
 * define}.
 */
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

  /** Returns an empty environment, having no bindings and no parent. */
  public static ScmEnvironment empty() {
    return of(null, Collections.emptyMap());
  }

  /**
   * Returns an environment with the given bindings but no parent.
   *
   * @param bindings the bindings to define in the environment. Must not be {@code null}.
   * @see #of(ScmEnvironment, Map)
   */
  public static ScmEnvironment of(Map<ScmSymbol, ScmValue> bindings) {
    return of(null, bindings);
  }

  /**
   * Returns an environment with the given bindings and parent.
   *
   * @param parent the environment to use as the new environment's parent. Can be {@code null} to
   *     indicate that the returned environment should have no parent.
   * @param bindings the bindings to define in the environment. Must not be {@code null}.
   */
  public static ScmEnvironment of(ScmEnvironment parent, Map<ScmSymbol, ScmValue> bindings) {
    return new ScmEnvironment(parent, bindings);
  }

  /**
   * Defines a new binding with the given name and value.
   *
   * @param name the name of the new binding. Must not be {@code null}.
   * @param value the value of the new binding. Must not be {@code null}.
   */
  public void define(ScmSymbol name, ScmValue value) {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null");
    }
    if (value == null) {
      throw new IllegalArgumentException("value must not be null");
    }
    bindings.put(name, value);
  }

  /**
   * Gets the value corresponding to the given name.
   *
   * @param name the name of the binding whose value to retrieve. Must not be {@code null}.
   * @return the value corresponding to the given name (empty if there is no definition for the
   *     given name)
   */
  public Optional<ScmValue> get(ScmSymbol name) {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null");
    }
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
