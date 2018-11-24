package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmExecutionManager {
  static ScmExecutionManager newDefaultExecutionManager() {
    return new ScmDefaultExecutionManager();
  }

  ScmValue execute(ScmStackFrame stackFrame, ScmEvaluator evaluator);
}
