package com.ianprime0509.jscheme.types;

import java.util.List;
import java.util.Queue;
import java.util.function.Function;

/**
 * A basic interface for Scheme procedure types.
 *
 * <p>This interface provides the two basic methods required of any procedure, namely obtaining
 * information about the parameters it accepts and applying it to a list of parameters. This
 * provides a sufficient foundation for many types of procedures; it is not, however, sufficient to
 * implement features that rely on finer control of procedure execution, such as tail call
 * elimination and continuations. To implement procedures that support these features, implement the
 * {@link ScmManagedProcedure} interface, which extends this one.
 *
 * <p>Implementing this interface directly is a good choice for implementations that cannot
 * implement {@link ScmManagedProcedure} due to technical restrictions. For example, the concrete
 * procedure class returned by {@link ScmProcedure#fromLambda(ScmEnvironment, ScmParameterList,
 * Function) fromLambda} implements this interface directly, since Java does not support the
 * low-level stack control that would be necessary for more advanced implementations.
 */
public interface ScmProcedure extends ScmValue {
  /**
   * Returns a procedure that uses the given {@link Function} for execution.
   *
   * @param parameterList the parameter list of the procedure. Must not be {@code null}.
   * @param body the function to be executed when applying the procedure to a list of arguments. If
   *     the function is called, its input environment is guaranteed to contain bindings for all
   *     required parameters specified in the parameter list. Must not be {@code null}.
   * @return a procedure that uses the given body for its underlying behavior
   * @see #fromLambda(ScmEnvironment, ScmParameterList, Function)
   */
  static ScmProcedure fromLambda(
      final ScmParameterList parameterList, final Function<ScmEnvironment, ScmValue> body) {
    return fromLambda(null, parameterList, body);
  }

  /**
   * Returns a procedure that uses the given {@link Function} for execution.
   *
   * @param lexicalEnvironment the enclosing lexical environment of the procedure. May be {@code
   *     null} to indicate that the procedure should not have such an environment, and should only
   *     be aware of the parameters explicitly passed to it.
   * @param parameterList the parameter list of the procedure. Must not be {@code null}.
   * @param body the function to be executed when applying the procedure to a list of arguments. If
   *     the function is called, its input environment is guaranteed to contain bindings for all
   *     required parameters specified in the parameter list. Must not be {@code null}.
   * @return a procedure that uses the given body for its underlying behavior
   */
  static ScmProcedure fromLambda(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    return new ScmJavaProcedure(lexicalEnvironment, parameterList, body);
  }

  /**
   * Returns a procedure whose body consists of the given Scheme expressions.
   *
   * <p>Creating a procedure using this method is directly analogous to how lambdas are defined in
   * Scheme itself; a lambda consists of the lexical environment in which it was defined, a
   * parameter list and a body consisting of a series of expressions to be evaluated, in order, with
   * the final expression providing the return value.
   *
   * @param parameterList the parameter list of the procedure. Must not be {@code null}.
   * @param body a queue of Scheme expressions, which will be evaluated in order when the procedure
   *     is called. The last expression will be used to provide the return value of the procedure.
   *     Must not be {@code null}.
   * @return a procedure defined in terms of the given Scheme expressions
   * @see #fromScheme(ScmEnvironment, ScmParameterList, Queue)
   */
  static ScmManagedProcedure fromScheme(
      final ScmParameterList parameterList, final Queue<ScmValue> body) {
    return fromScheme(null, parameterList, body);
  }

  /**
   * Returns a procedure whose body consists of the given Scheme expressions.
   *
   * <p>Creating a procedure using this method is directly analogous to how lambdas are defined in
   * Scheme itself; a lambda consists of the lexical environment in which it was defined, a
   * parameter list and a body consisting of a series of expressions to be evaluated, in order, with
   * the final expression providing the return value.
   *
   * @param lexicalEnvironment the enclosing lexical environment of the procedure. May be {@code
   *     null} to indicate that the procedure should not have such an environment, and should only
   *     be aware of the parameters explicitly passed to it.
   * @param parameterList the parameter list of the procedure. Must not be {@code null}.
   * @param body a queue of Scheme expressions, which will be evaluated in order when the procedure
   *     is called. The last expression will be used to provide the return value of the procedure.
   *     Must not be {@code null}.
   * @return a procedure defined in terms of the given Scheme expressions
   */
  static ScmManagedProcedure fromScheme(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Queue<ScmValue> body) {
    return new ScmSchemeProcedure(lexicalEnvironment, parameterList, body);
  }

  /**
   * Applies the procedure to the given parameters.
   *
   * <p>It is the responsibility of the implementing class to ensure that the given parameters
   * satisfy the requirements of the procedure's parameter list, as no such checking is guaranteed
   * to occur before this method is called. To avoid implementing this behavior manually,
   * implementors may choose to extends {@link ScmAbstractProcedure}, which handles the details of
   * checking the parameters and constructing the execution environment.
   *
   * @param parameters the parameters to which to apply the procedure. Must not be {@code null}.
   * @return the return value of the procedure. This must not be {@code null}; in this
   *     implementation of Scheme, every procedure must return some value (a good default choice
   *     would be {@link ScmNil}).
   */
  ScmValue apply(List<ScmValue> parameters);

  /**
   * Returns the parameter list of the procedure.
   *
   * <p>This method allows callers to obtain more information about the procedure; for example, a
   * REPL might use this information to provide basic documentation to the user, or this information
   * could be exposed using a Scheme procedure, allowing for basic reflective functionality.
   *
   * @return the procedure's parameter list. Must not be {@code null}.
   */
  ScmParameterList getParameterList();
}
