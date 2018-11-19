package com.ianprime0509.jscheme.types;

import java.util.function.Function;

/**
 * An implementation of {@link ScmProcedure} that derives its functionality from a native Java
 * method (namely, an implementation of {@code Function<ScmEnvironment, ScmValue>}).
 */
class ScmJavaProcedure extends ScmAbstractProcedure {
  private final Function<ScmEnvironment, ScmValue> body;

  ScmJavaProcedure(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    super(lexicalEnvironment, parameterList);
    if (body == null) {
      throw new IllegalArgumentException("body must not be null");
    }
    this.body = body;
  }

  @Override
  public ScmValue apply(final ScmEnvironment executionEnvironment) {
    return body.apply(executionEnvironment);
  }
}
