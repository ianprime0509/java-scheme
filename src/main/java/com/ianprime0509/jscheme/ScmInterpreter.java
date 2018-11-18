package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmValue;

/**
 * An interpreter for Scheme expressions.
 *
 * <p>This is the basic starting point for users wishing to interpret Scheme code, along with a
 * {@link ScmReader} to parse Scheme expressions into {@code ScmValue} objects which can be
 * interpreted by implementors of this interface. This is a higher-level abstraction than {@link
 * ScmEvaluator}, because the former maintains an implicit <em>interaction environment</em> which is
 * used to evaluate expressions, while the latter requires the environment to be passed explicitly
 * with each evaluation.
 */
public interface ScmInterpreter {
  /**
   * Returns a new interpreter with an implementation-defined interaction environment.
   *
   * @return a new instance of the default interpreter
   */
  static ScmInterpreter newDefaultInterpreter() {
    return new ScmDefaultInterpreter();
  }

  /**
   * Gets the interaction environment used for evaluating expressions.
   *
   * @return the interaction environment. This reference can be mutated; that is, it can be used to
   *     add additional bindings or remove ones already present, and these changes will affect
   *     future evaluations.
   */
  ScmEnvironment getInteractionEnvironment();

  /**
   * Evaluates the given expression in the context of the interaction environment.
   *
   * @param expression the expression to evaluate
   * @return the value to which the expression evaluates
   */
  ScmValue evaluate(ScmValue expression);
}
