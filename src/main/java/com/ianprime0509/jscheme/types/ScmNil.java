package com.ianprime0509.jscheme.types;

public final class ScmNil implements ScmValue {
  private static final ScmNil instance = new ScmNil();

  private ScmNil() {}

  public static ScmNil get() {
    return instance;
  }

  @Override
  public String toString() {
    return "()";
  }
}
