package com.ianprime0509.jscheme;

import java.util.Collections;
import java.util.List;

import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmReader {
  class PartialResult {
    private final List<ScmValue> parsed;

    private final String unparsed;

    private PartialResult(final List<ScmValue> parsed, final String unparsed) {
      if (parsed == null) {
        throw new IllegalArgumentException("parsed must not be null");
      }
      if (unparsed == null) {
        throw new IllegalArgumentException("unparsed must not be null");
      }
      this.parsed = parsed;
      this.unparsed = unparsed;
    }

    public static PartialResult of(final List<ScmValue> parsed, final String unparsed) {
      return new PartialResult(parsed, unparsed);
    }

    public List<ScmValue> getParsed() {
      return Collections.unmodifiableList(parsed);
    }

    String getUnparsed() {
      return unparsed;
    }

    public boolean isComplete() {
      return !unparsed.isEmpty();
    }
  }

  /**
   * Reads zero or more complete expressions from the given input string.
   *
   * <p>This method does not perform any evaluation of its input; it is used solely for converting
   * textual representations of Scheme data into their internal forms as defined elsewhere in this
   * project.
   *
   * @param input the string to parse
   * @return the (possibly empty) list of expressions parsed from the given input
   * @throws IllegalArgumentException if the given input could not be parsed completely
   */
  default List<ScmValue> read(String input) {
    final PartialResult result = readPartialResult(input, null);
    if (!result.isComplete()) {
      throw new IllegalArgumentException("incomplete input");
    }
    return result.getParsed();
  }

  /**
   * Reads zero or more complete expressions from the given input string, ignoring any incomplete
   * expression at the end of the string.
   *
   * <p>This method is designed for use in an interactive interpreter, where users may want to write
   * expressions spanning several lines and each line is processed individually by the interpreter.
   *
   * @param input the string to parse
   * @param read a partial result from a previous invocation of this method, or {@code null} if not
   *     applicable. Any unparsed input from this partial result will be combined with {@code input}
   *     before parsing.
   * @return the partial input that was parsed
   * @throws IllegalArgumentException if the given input is syntactically invalid in such a way that
   *     further input could not correct it (for example, if given the string {@code ")"} with no
   *     prior partial input).
   */
  PartialResult readPartialResult(String input, PartialResult read);
}
