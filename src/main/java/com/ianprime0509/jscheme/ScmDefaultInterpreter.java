package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmNil;
import com.ianprime0509.jscheme.types.ScmParameterList;
import com.ianprime0509.jscheme.types.ScmProcedure;
import com.ianprime0509.jscheme.types.ScmSymbol;
import com.ianprime0509.jscheme.types.ScmValue;

class ScmDefaultInterpreter implements ScmInterpreter {
  private final ScmEvaluator evaluator;

  private final ScmEnvironment interactionEnvironment;

  ScmDefaultInterpreter() {
    evaluator = ScmEvaluator.newDefaultEvaluator();

    interactionEnvironment = ScmEnvironment.empty();
  }

  @Override
  public ScmValue evaluate(ScmValue expression) {
    return evaluator.evaluate(expression, interactionEnvironment);
  }

  @Override
  public ScmEnvironment getInteractionEnvironment() {
    return interactionEnvironment;
  }
}
