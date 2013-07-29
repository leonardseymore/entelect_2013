package za.co.entelect.competition.groovy;

import groovy.lang.Binding;
import groovy.lang.Closure;
import jline.History;
import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.InteractiveShellRunner;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.GameState;

import java.io.File;
import java.io.IOException;

public class Console {

  private static final String HISTORY_FILE = ".battlematrix_history";
  private static final String INPUT_PROMPT = "> ";

  public Console(final IO io, GameState gameState) {
    io.out.println(Constants.APP_TITLE);

    Binding binding = new Binding();
    binding.setProperty("g", gameState);
    Groovysh groovy = new Groovysh(binding, io);
    groovy.setHistory(new History());

    final InteractiveShellRunner runner = new InteractiveShellRunner(groovy, new ConsolePromptClosure(groovy, INPUT_PROMPT));
    runner.setErrorHandler(groovy.getDefaultErrorHook());
    try {
      runner.setHistory(new History(new File(System.getProperty("user.home") + "/" + HISTORY_FILE)));
    } catch (IOException e) {
      io.err.println("Unable to create history file: " + HISTORY_FILE);
    }

    try {
      runner.run();
    } catch (Error e) {
      System.err.println(e.getMessage());
    }
  }

  class ConsolePromptClosure extends Closure {

    private final String inputPrompt;

    public ConsolePromptClosure(final Object owner, final String inputPrompt) {
      super(owner);
      this.inputPrompt = inputPrompt;
    }

    public Object call() {
      return this.inputPrompt;
    }
  }
}