package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmPair;
import com.ianprime0509.jscheme.types.ScmString;
import com.ianprime0509.jscheme.types.ScmSymbol;

public class App {
  public static void main(String[] args) {
    final ScmInterpreter interpreter = ScmInterpreter.newDefaultInterpreter();
    interpreter.getInteractionEnvironment().define(ScmSymbol.of("test"), ScmString.of("Hello, world!"));

    interpreter.evaluate(ScmPair.ofList(ScmSymbol.of("display"), ScmSymbol.of("test")));

    System.out.println("Done");
  }
}
