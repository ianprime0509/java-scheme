package com.ianprime0509.jscheme.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An interface representing a pair of Scheme values.
 *
 * <p>The reason why this is an interface and not a class is that there are several ways to
 * implement Scheme's pair type, depending on which functionality is required. For example, it may
 * be more efficient to implement an immutable pair than to implement a mutable pair. A more
 * interesting application of this interface is a specialized immutable list type: to avoid the
 * overhead of a traditional linked list, it is possible to implement an immutable list using a
 * simple {@link ArrayList} if the elements are known in advance, while still providing efficient
 * implementations of all the required primitive functionality of a pair. The concrete class
 * returned by {@link ScmPair#ofList(List)} applies this strategy.
 *
 * <p>Lists in Scheme are implemented in terms of pairs; the pair {@code (1 . '())} (that is, the
 * pair whose first element is {@code 1} and whose second element is the empty list, or {@link
 * ScmNil nil}) is a list containing the single element {@code 1}. To prepend an element to this
 * list, one would construct a new pair whose first element is the element to be prepended and whose
 * second element is the original list, and so on. This interface provides a default implementation
 * of the {@link ScmPair#isList()} method, which determines whether an arbitrary pair is such a
 * list.
 *
 * <p>Classes that implement this interface are not guaranteed to be mutable. Mutable pair classes
 * should instead implement {@link ScmMutablePair}, which extends this interface and provides
 * mutators.
 */
public interface ScmPair extends ScmValue {
  /**
   * Returns an immutable list consisting of the given elements.
   *
   * <p>The concrete class returned by this method provides efficient, constant-time implementations
   * of all the methods in this interface, and uses contiguous memory rather than a linked list
   * structure to reduce storage overhead.
   *
   * @param values the values of which the returned list will consist. The array will be copied, so
   *     future modifications will not affect the elements in the returned list. Must not be {@code
   *     null}.
   * @return an immutable list consisting of the given elements
   * @see #ofList(List)
   */
  static ScmPair ofList(ScmValue... values) {
    return ofList(Arrays.asList(values));
  }

  /**
   * Returns an immutable list consisting of the given elements.
   *
   * <p>The concrete class returned by this method provides efficient, constant-time implementations
   * of all the methods in this interface, and uses contiguous memory rather than a linked list
   * structure to reduce storage overhead.
   *
   * @param values the values of which the returned list will consist. The list will be copied, so
   *     future modifications will not affect the elements in the returned list. Must not be {@code
   *     null}.
   * @return an immutable list consisting of the given elements
   */
  static ScmPair ofList(List<ScmValue> values) {
    return new ScmList(new ArrayList<>(values));
  }

  /**
   * Returns the first element of the pair.
   *
   * <p>For a list, this is the first element of the list.
   */
  ScmValue getCar();

  /**
   * Returns the second element of the pair.
   *
   * <p>For a list, this is the tail of the list, that is, the list consisting of all elements
   * except the first.
   */
  ScmValue getCdr();

  /**
   * Returns whether this pair is a list, that is, whether repeatedly calling {@link #getCdr()} will
   * eventually return nil.
   *
   * <p>The default implementation of this method runs in linear time with the number of elements in
   * the list. Implementing classes that can provide better implementations should do so; for
   * example, this method simply returns {@code true} for any pair obtained using the {@link
   * #ofList(List)} method.
   *
   * @return whether this pair is a list
   */
  default boolean isList() {
    ScmPair pair = this;
    while (pair.getCdr() instanceof ScmPair) {
      pair = (ScmPair) pair.getCdr();
    }
    return pair.getCdr() instanceof ScmNil;
  }
}
