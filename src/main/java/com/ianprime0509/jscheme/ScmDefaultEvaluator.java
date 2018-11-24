package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.ScmCallStackFrame.Result;
import com.ianprime0509.jscheme.types.ScmValue;

class ScmDefaultEvaluator implements ScmEvaluator {
  ScmDefaultEvaluator() {}

  @Override
  public Result evaluate(ScmValue expression, ScmExecutionManager executionManager, ScmStackFrame context) {
    return null;
  }
}
