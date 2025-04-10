# Calendar Application - Assignment 6

## Overview

This is Assignment 6 of the Calendar Application project. In this iteration, a graphical user interface (GUI) has been implemented using Java Swing. The application now supports three different modes:

- **GUI Mode (Default):** Launches a rich, interactive calendar GUI.
- **Interactive Mode:** Provides a command-line interface for manual command entry.
- **Headless Mode:** Reads commands from a script file and processes them in batch mode.

The system supports creating single and recurring events (both timed and all-day), editing events, copying events between calendars, importing events from CSV files, and exporting calendars to CSV format. Multiple calendars with timezone support are also included.

## How to Run

### Requirements
- The application is packaged as an executable JAR file (`CalendarApp.jar`).

### Running in Different Modes

#### 1. GUI Mode (Default)
To launch the application with its graphical user interface, simply run:
```bash
java -jar CalendarApp.jar
```

This will open the GUI which allows you to:
- View the current month and navigate between months.
- See which days have events (highlighted in lavender).
- Add new events by clicking the "Add Event" button in the event panel.
- Edit and copy events via dedicated dialogs.
- Switch between multiple calendars and view the current calendar name.
- Import events from and export events to CSV files.

#### 2. Interactive Mode
To run the application in interactive text mode, use the following command:
```bash
java -jar CalendarApp.jar --mode interactive
```

In this mode, you will be prompted to enter commands directly in the console. Use the command formats defined in the documentation (for example, create event, edit event, etc.). Type `exit` to quit interactive mode.

#### 3. Headless Mode
To run the application in headless mode (scripting mode), use:
```bash
java -jar CalendarApp.jar --mode headless path/to/script.txt
```

Replace `path/to/script.txt` with the path to your script file containing calendar commands. Each line in the file will be processed as a command by the application.

## CSV Import/Export

### Exporting:
The calendar can be exported to a CSV file (compatible with Google Calendar). You can use the "Export CSV" option from the File menu in the GUI or run the command `export cal <filename>` in interactive/headless mode.

### Importing:
Import events from a CSV file using the "Import CSV" option in the GUI. The CSV file must follow the format produced by the export function.

## Code Flow

### Main Entry Point (CalendarApp.java)
- **Initialization**: The application starts in the main method of CalendarApp.java. It creates a default calendar using the system's default timezone and initializes a CalendarController with that calendar.
- **Mode Selection**:
    - If no command-line arguments are provided, the GUI mode is launched on the Swing Event Dispatch Thread (EDT) via the CalendarGUI class.
    - If at least two arguments are provided (e.g., --mode headless or --mode interactive), a ModeFactory is used to choose the appropriate mode.
    - In case of invalid arguments, an error is printed and the application exits.

### Modes (Headless and Interactive)
- **Headless Mode**: Implemented in HeadlessMode.java, this mode reads commands from a specified script file. For each non-empty line, it displays the command being processed, uses the CommandFactory to parse and generate a concrete Command implementation, executes the command, and shows the output.
- **Interactive Mode**: Implemented in InteractiveMode.java, this mode runs in a continuous loop, prompting the user to enter commands. It reads each command via a ConsoleUIHandler, translates input into a Command, executes it, and outputs the result.

### Graphical User Interface (CalendarGUI)
- **Layout**: The GUI window is divided into different panels:
    - Top Panel: Displays the current calendar name and month with navigation buttons.
    - Month Panel: Presents a grid view of the month where days with events are highlighted in lavender.
    - Side Panel: Shows event details for the selected day and includes an "Add Event" button.
    - Menu Bar: Provides options for file operations, calendar management, and event operations.
- **Refreshing the View**: After operations like creating an event or changing the calendar, the view is redrawn to update the month panel and the current calendar name.

### Controller (CalendarController)
- **Event Management**: Handles all calendar operations including creating, editing, and copying events.
- **Calendar Management**: Interacts with the CalendarManager to maintain multiple calendars and support timezone operations.
- **Import Functionality**: Calls the importer to handle CSV import functionality.

### Importer (CSVCalendarImporter)
- **CSV Parsing**: Provides functionality to read a CSV file and import events into a given calendar.
- **Return Value**: Returns the total count of events imported.

### Command Pattern (Command & CommandFactory)
- **Command Processing**: User inputs are processed using the CommandFactory, which translates the input string into a concrete Command implementation.
- **Execution**: Once created, the command's execute() method is invoked, which calls the appropriate methods on the CalendarController.

### View Components (CalendarView and ErrorView)
- **Formatting**: CalendarView formats event details into human-readable strings.
- **Error Display**: ErrorView displays error messages in headless and interactive modes.

## Additional Information

### Error Handling:
Errors are handled gracefully. In the GUI, error messages are shown in dialog boxes, while in interactive or headless modes, errors are printed to the console.

### MVC Architecture:
The project follows an MVC architecture with a clear separation between the model, view, and controller layers.

### Calendar Persistence:
When switching between calendars, events are retained within each calendar instance.

## Key Changes from Assignment 5 to Assignment 6

* **Graphical User Interface (GUI) Implementation:**
    * **New GUI Framework:** The entire front end now uses Java Swing. A new class (CalendarGUI) has been added to provide a graphical month view of the calendar.
    * **Visual Enhancements:**
        * The month view now colors dates that have events in a lavender background.
        * Added navigation buttons (next/previous month) and a dedicated "Add Event" button below the event display area.
        * The current calendar name is now displayed prominently at the top of the GUI.
    * **Dialog Windows:** A set of dialog classes (e.g., NewCalendarDialog, CreateEventDialog, EditEventDialog, SelectCalendarDialog, and an enhanced CopyEventDialog with multiple tabs) have been introduced to handle user interactions for creating, editing, and copying events, as well as for managing calendars.

* **Separation of Import Functionality:**
    * Whereas export functionality was already handled by the `CSVCalendarExporter`, import functionality is now shifted to its own package (`calendar.controller.importer`). A new class (`CSVCalendarImporter`) implements a `CalendarImporter` interface. This decouples the import logic from the GUI so that the GUI simply calls a controller method to import calendar data from a CSV file.

* **MVC Refinements:**
    * **Enhanced Controller & View Interaction:**
        * The controller now provides methods to retrieve the current calendar name (e.g., `getCurrentCalendarName`) so that the GUI displays this information dynamically.
        * The GUI calls controller methods to refresh its view after any event creation or calendar change.
    * **Improved Error Handling:**
        * Errors during operations (e.g., during CSV import/export or event processing) are now handled more gracefully in the GUI by displaying dialog messages, while headless/interactive modes use the ConsoleUIHandler and ErrorView.

* **Mode Support Retained:**
    * The command-line based modes (headless and interactive) remain available, chosen via command-line parameters. The ModeFactory was updated to now require one argument (a string array) when selecting a mode.

### Distribution of Work

For this assignment, Achyut primarily worked on implementing the functional parts of the rubric.
Marcus did the refactoring and worked on implementing the factories as well as the testing.
However, both Marcus and Achyut spent much of the assignment in both in person working sessions and
remote working sessions.