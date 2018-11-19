package com.ianprime0509.jscheme.types;

import java.util.List;
import java.util.Queue;
import java.util.function.Function;

public interface ScmProcedure extends ScmValue {
  static ScmProcedure fromLambda(
      final ScmParameterList parameterList, final Function<ScmEnvironment, ScmValue> body) {
    return fromLambda(null, parameterList, body);
  }

  static ScmProcedure fromLambda(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    return new ScmJavaProcedure(lexicalEnvironment, parameterList, body);
  }

  static ScmManagedProcedure fromScheme(
      final ScmParameterList parameterList, final Queue<ScmValue> body) {
    return fromScheme(null, parameterList, body);
  }

  static ScmManagedProcedure fromScheme(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Queue<ScmValue> body) {
    return new ScmSchemeProcedure(lexicalEnvironment, parameterList, body);
  }

  ScmValue apply(List<ScmValue> parameters);

  ScmParameterList getParameterList();
}
