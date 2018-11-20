package com.ianprime0509.jscheme.types;

/**
 * The empty list, also known as <em>nil</em>.
 *
 * <p>Unlike other Scheme types, this class is a <em>singleton</em>; in other words, there can never
 * be more than one instance of this class at any time. A reference to this unique instance may be
 * obtained through the {@link ScmNil#get()} method.
 */
public final class ScmNil implements ScmValue {
  private static final ScmNil instance = new ScmNil();

  private ScmNil() {}

  /**
   * Returns a reference to nil.
   *
   * @return a reference to the singleton nil object. Since nil is a singleton, multiple calls to
   *     this method are guaranteed to return the exact same object.
   */
  public static ScmNil get() {
    return instance;
  }

  @Override
  public String toString() {
    return "()";
  }
}
