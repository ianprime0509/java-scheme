package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmValue;

public class ScmEvaluationResult {
    private final Object value;

    private ScmEvaluationResult(final Object value) {
      this.value = value;
    }

    public static ScmEvaluationResult ofCompleted(ScmValue value) {
      return new ScmEvaluationResult(value);
    }

    public static ScmEvaluationResult ofContinuing(ScmStackFrame value) {
      return new ScmEvaluationResult(value);
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
