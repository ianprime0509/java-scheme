package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmSymbol;
import com.ianprime0509.jscheme.types.ScmValue;

class ScmDefaultEvaluator implements ScmEvaluator {
  ScmDefaultEvaluator() {}

  @Override
  public ScmEvaluationResult evaluate(
      ScmValue expression, ScmExecutionManager executionManager, ScmStackFrame context) {
    if (expression instanceof ScmSymbol) {
      final ScmSymbol symbol = (ScmSymbol) expression;
      return ScmEvaluationResult.ofCompleted(
          context
              .getEnvironment()
              .get(symbol)
              .orElseThrow(
                  () -> new IllegalArgumentException("Symbol is undefined: " + symbol.getName())));
    }
    return ScmEvaluationResult.ofCompleted(expression);
  }
}
