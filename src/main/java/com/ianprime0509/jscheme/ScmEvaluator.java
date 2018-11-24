package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmEvaluator {
  static ScmEvaluator newDefaultEvaluator() {
    return new ScmDefaultEvaluator();
  }

  ScmEvaluationResult evaluate(
      ScmValue expression, ScmExecutionManager executionManager, ScmStackFrame context);

  default ScmValue evaluateFully(
      ScmValue expression, ScmExecutionManager executionManager, ScmStackFrame context) {
    final ScmEvaluationResult result = evaluate(expression, executionManager, context);
    return result.isCompleted()
        ? result.getCompleted()
        : executionManager.execute(result.getContinuing(), this);
  }
}
