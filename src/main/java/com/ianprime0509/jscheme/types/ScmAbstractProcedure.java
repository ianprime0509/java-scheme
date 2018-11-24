package com.ianprime0509.jscheme.types;

import com.ianprime0509.jscheme.ScmCallStackFrame;
import com.ianprime0509.jscheme.ScmStackFrame;
import java.util.List;
import java.util.Map;

/**
 * An abstract base class for custom procedure types, providing some basic behavior (such as storing
 * the parameter list and lexical environment) that is common to all implementations of {@link
 * ScmProcedure}.
 */
public abstract class ScmAbstractProcedure implements ScmProcedure {
  /**
   * The enclosing lexical environment of the procedure; that is, the environment in which the
   * procedure was defined.
   */
  protected final ScmEnvironment lexicalEnvironment;

  /** The parameter list of the procedure, specifying the parameters that this procedure accepts. */
  protected final ScmParameterList parameterList;

  /**
   * Initializes the lexical environment and parameter list of this procedure.
   *
   * @param lexicalEnvironment the enclosing lexical environment of the procedure. This may be
   *     {@code null}, in which case the behavior is semantically identical to passing {@link
   *     ScmEnvironment#empty()}.
   * @param parameterList the parameter list of this procedure
   */
  public ScmAbstractProcedure(
      final ScmEnvironment lexicalEnvironment, final ScmParameterList parameterList) {
    if (parameterList == null) {
      throw new IllegalArgumentException("parameterList must not be null");
    }
    this.lexicalEnvironment = lexicalEnvironment;
    this.parameterList = parameterList;
  }

  /**
   * Applies the procedure in the given execution environment.
   *
   * <p>Subclasses should override this method rather than {@link
   * ScmProcedure#apply(java.util.Map)}; the latter has been overridden to call this method with the
   * complete execution environment, including not only the parameters passed to the procedure
   * (properly bound according to the {@link ScmParameterList}) but the enclosing lexical
   * environment as well.
   *
   * @param executionEnvironment the execution environment of the procedure, consisting of the
   *     environment formed by the parameters passed to the procedure along with the enclosing
   *     lexical environment as its parent
   * @return the return value of the procedure
   */
  public abstract ScmCallStackFrame apply(
      ScmEnvironment executionEnvironment, ScmStackFrame context);

  /**
   * Returns the execution environment of the procedure when called with the given parameters.
   *
   * @param parameters the parameters passed to the procedure
   * @return the complete execution environment of the procedure, consisting of the environment
   *     formed by the parameters with the enclosing lexical environment as its parent
   * @throws IllegalArgumentException if the given parameters do not satisfy the requirements of
   *     this procedure's parameter list
   */
  protected ScmEnvironment getExecutionEnvironment(final List<ScmValue> parameters) {
    final Map<ScmSymbol, ScmValue> bindings = parameterList.getExecutionBindings(parameters);
    return ScmEnvironment.of(lexicalEnvironment, bindings);
  }

  @Override
  public ScmCallStackFrame apply(final List<ScmValue> parameters, final ScmStackFrame context) {
    final ScmEnvironment executionEnvironment = getExecutionEnvironment(parameters);
    return apply(executionEnvironment, context);
  }

  @Override
  public ScmParameterList getParameterList() {
    return parameterList;
  }
}
