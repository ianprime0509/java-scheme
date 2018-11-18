package com.ianprime0509.jscheme.types;

import java.util.Map;
import java.util.function.Function;

public interface ScmProcedure extends ScmValue {
  static ScmProcedure fromLambda(
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    return fromLambda(null, parameterList, body);
  }

  static ScmProcedure fromLambda(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    return new ScmJavaProcedure(lexicalEnvironment, parameterList, body);
  }

  ScmValue apply(Map<ScmSymbol, ScmValue> bindings);

  ScmParameterList getParameterList();
}
