package com.ianprime0509.jscheme.types;

public interface ScmMutablePair extends ScmPair {
  void setCar(ScmValue value);

  void setCdr(ScmValue value);
}
