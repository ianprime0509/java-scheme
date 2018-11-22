package com.ianprime0509.jscheme.types;

/**
 * An extension of {@link ScmPair} that provides mutators (that is, methods to set the elements in
 * the pair after the pair has been constructed).
 */
public interface ScmMutablePair extends ScmPair {
  /**
   * Sets the first element of the pair to the given value.
   *
   * @param value the value to which to set the first element. Must not be {@code null}.
   */
  void setCar(ScmValue value);

  /**
   * Sets the second element of the pair to the given value.
   *
   * @param value the value to which to set the second element. Must not be {@code null}.
   */
  void setCdr(ScmValue value);
}
