package calendar.view.handler;

/**
 * Interface defining methods for handling user interface input and output.
 */
public interface UIHandler {
  /**
   * Retrieves input from the user.
   *
   * @return the string input provided by the user
   */
  String getInput();

  /**
   * Displays a message to the user.
   *
   * @param message the message to display
   */
  void display(String message);
}