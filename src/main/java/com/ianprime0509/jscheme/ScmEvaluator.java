package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmEvaluator {
  static ScmManagedEvaluator newDefaultEvaluator() {
    return new ScmDefaultEvaluator();
  }

  ScmValue evaluate(ScmValue expression, ScmEnvironment evaluationEnvironment);
}
