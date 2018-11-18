package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.ScmManagedEvaluator.Result;
import com.ianprime0509.jscheme.types.ScmNil;
import com.ianprime0509.jscheme.types.ScmValue;

class ScmDefaultExecutionManager implements ScmExecutionManager {
  private final ScmManagedEvaluator evaluator;

  ScmDefaultExecutionManager(final ScmManagedEvaluator evaluator) {
    this.evaluator = evaluator;
  }

  @Override
  public ScmValue execute(ScmStackFrame stackFrame) {
    while (stackFrame.hasExpression()) {
      final ScmValue expression = stackFrame.pollExpression();
      final Result result = evaluator.evaluateManaged(expression, this, stackFrame);

      if (result.isCompleted()) {
        if (!stackFrame.hasExpression()) {
          return result.getCompleted();
        }
      } else {
        ScmStackFrame newFrame = result.getContinuing();
        if (!stackFrame.hasExpression()) {
          // This is a tail call, so set its parent to the parent of
          // the current frame and continue from there.
          newFrame =
              ScmStackFrame.builder() //
                  .inherit(newFrame) //
                  .parent(stackFrame.getParent()) //
                  .build();
        }
        stackFrame = newFrame;
      }
    }
    return ScmNil.get();
  }
}
