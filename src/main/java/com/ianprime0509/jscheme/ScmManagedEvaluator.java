package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmManagedEvaluator extends ScmEvaluator {
  class Result {
    private final Object value;

    private Result(final Object value) {
      this.value = value;
    }

    public static Result ofCompleted(ScmValue value) {
      return new Result(value);
    }

    public static Result ofContinuing(ScmStackFrame value) {
      return new Result(value);
    }

    public boolean isCompleted() {
      return value instanceof ScmValue;
    }

    public ScmValue getCompleted() {
      if (!isCompleted()) {
        throw new IllegalStateException("not completed");
      }
      return (ScmValue) value;
    }

    public ScmStackFrame getContinuing() {
      if (isCompleted()) {
        throw new IllegalStateException("not continuing");
      }
      return (ScmStackFrame) value;
    }
  }

  Result evaluateManaged(
      ScmValue expression, ScmExecutionManager executionManager, ScmStackFrame context);
}
