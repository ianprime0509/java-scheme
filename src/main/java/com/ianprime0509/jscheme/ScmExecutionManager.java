package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmExecutionManager {
  static ScmExecutionManager newDefaultExecutionManager(final ScmManagedEvaluator evaluator) {
    return new ScmDefaultExecutionManager(evaluator);
  }

  ScmValue execute(ScmStackFrame stackFrame);
}
