package calendar.view.handler;

import java.util.Scanner;

/** Console-based implementation of the UIHandler interface. */
public class ConsoleUIHandler implements UIHandler {
  private final Scanner scanner;

  /** Constructs a new ConsoleUIHandler, initializing the scanner for console input. */
  public ConsoleUIHandler() {
    scanner = new Scanner(System.in);
  }

  /**
   * Reads the next line of input from the console.
   *
   * @return the input line as a string
   */
  @Override
  public String getInput() {
    return scanner.nextLine();
  }

  /**
   * Displays the provided message to the console.
   *
   * @param message the message to print
   */
  @Override
  public void display(String message) {
    System.out.println(message);
  }
}
