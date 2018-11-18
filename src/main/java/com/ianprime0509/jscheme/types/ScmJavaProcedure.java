package com.ianprime0509.jscheme.types;

import java.util.Map;
import java.util.function.Function;

class ScmJavaProcedure implements ScmProcedure {
  private final ScmEnvironment lexicalEnvironment;

  private final ScmParameterList parameterList;

  private final Function<ScmEnvironment, ScmValue> body;

  ScmJavaProcedure(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    if (parameterList == null) {
      throw new IllegalArgumentException("parameterList must not be null");
    }
    if (body == null) {
      throw new IllegalArgumentException("body must not be null");
    }
    this.lexicalEnvironment = lexicalEnvironment;
    this.parameterList = parameterList;
    this.body = body;
  }

  @Override
  public ScmValue apply(Map<ScmSymbol, ScmValue> bindings) {
    final ScmEnvironment executionEnvironment = ScmEnvironment.of(lexicalEnvironment, bindings);
    return body.apply(executionEnvironment);
  }

  @Override
  public ScmParameterList getParameterList() {
    return parameterList;
  }
}
