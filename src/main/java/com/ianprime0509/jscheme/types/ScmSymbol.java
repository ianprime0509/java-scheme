package com.ianprime0509.jscheme.types;

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

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other == null || getClass() != other.getClass()) {
      return false;
    } else {
      return name.equals(((ScmSymbol) other).name);
    }
  }

  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }
}
