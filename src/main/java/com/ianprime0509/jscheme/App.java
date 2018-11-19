package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmNil;
import com.ianprime0509.jscheme.types.ScmPair;
import com.ianprime0509.jscheme.types.ScmParameterList;
import com.ianprime0509.jscheme.types.ScmProcedure;
import com.ianprime0509.jscheme.types.ScmString;
import com.ianprime0509.jscheme.types.ScmSymbol;
import java.util.ArrayDeque;
import java.util.Arrays;

public class App {
  public static void main(String[] args) {
    final ScmInterpreter interpreter = ScmInterpreter.newDefaultInterpreter();
    interpreter
        .getInteractionEnvironment()
        .define(ScmSymbol.of("test"), ScmString.of("Hello, world!"));
    interpreter
        .getInteractionEnvironment()
        .define(
            ScmSymbol.of("display"),
            ScmProcedure.fromLambda(
                interpreter.getInteractionEnvironment(),
                ScmParameterList.builder().required(ScmSymbol.of("value")).build(),
                env -> {
                  System.out.println(env.get(ScmSymbol.of("value")).get());
                  System.out.println(env.get(ScmSymbol.of("test")).get());
                  return ScmNil.get();
                }));
    interpreter
        .getInteractionEnvironment()
        .define(
            ScmSymbol.of("test2"),
            ScmProcedure.fromScheme(
                interpreter.getInteractionEnvironment(),
                ScmParameterList.empty(),
                new ArrayDeque<>(Arrays.asList(ScmPair.ofList(ScmSymbol.of("test2"))))));
    interpreter
        .getInteractionEnvironment()
        .define(
            ScmSymbol.of("test3"),
            ScmProcedure.fromLambda(
                interpreter.getInteractionEnvironment(),
                ScmParameterList.empty(),
                env -> {
                  interpreter.evaluate(ScmPair.ofList(ScmSymbol.of("test3")));
                  return ScmNil.get();
                }));

    interpreter.evaluate(ScmPair.ofList(ScmSymbol.of("display"), ScmString.of("hello")));
    interpreter.evaluate(ScmPair.ofList(ScmSymbol.of("test3")));

    System.out.println("Done");
  }
}
