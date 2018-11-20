package com.ianprime0509.jscheme.types;

/**
 * A Scheme symbol.
 *
 * <p>TODO: complete this documentation with a description of what a symbol is and why it's useful.
 */
public final class ScmSymbol implements ScmValue {
  private final String name;

  private ScmSymbol(String name) {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null");
    }
    this.name = name;
  }

  public static ScmSymbol of(String name) {
    return new ScmSymbol(name);
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other == null || getClass() != other.getClass()) {
      return false;
    } else {
      return name.equals(((ScmSymbol) other).name);
    }
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }
}
