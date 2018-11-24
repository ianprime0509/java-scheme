package com.ianprime0509.jscheme;

public interface ScmCallStackFrame extends ScmStackFrame {
  boolean hasNextResult();

  ScmEvaluationResult nextResult(ScmEvaluator evaluator, ScmExecutionManager executionManager);
}
