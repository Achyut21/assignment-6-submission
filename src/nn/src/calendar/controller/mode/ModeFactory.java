package calendar.controller.mode;

import calendar.controller.CalendarController;
import java.util.Scanner;

/** Class responsible for creating a concrete class implementing the mode interface. */
public class ModeFactory {

  private final CalendarController controller;

  /** Constructor for the Mode factory. */
  public ModeFactory(CalendarController controller) {
    this.controller = controller;
  }

  /** Version for when command‐line arguments are provided. */
  public Mode getMode(String[] args) {
    String modeArg = args[1];

    if (modeArg.equalsIgnoreCase("headless")) {
      if (args.length < 3) {
        System.err.println("Headless mode requires a script file path.");
        System.exit(1);
      }
      return new HeadlessMode(args[2], controller);
    } else if (modeArg.equalsIgnoreCase("interactive")) {
      return new InteractiveMode(controller);
    } else {
      System.err.println("Invalid mode specified: " + modeArg);
      System.exit(1);
    }
    return null;
  }

  /** No-argument version used by test cases or interactive prompts. */
  public Mode getMode() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Choose mode: 1 for Interactive, 2 for Headless, 3 to Exit");
      String mode = scanner.nextLine();
      switch (mode) {
        case "1":
          System.out.println("Interactive mode.");
          return new InteractiveMode(controller);
        case "2":
          System.out.println("Enter the script file path:");
          String filePath = scanner.nextLine();
          return new HeadlessMode(filePath, controller);
        case "3":
          System.out.println("Exiting Calendar App.");
          return null;
        default:
          System.out.println("Invalid command. Please try again.");
      }
    }
  }
}
