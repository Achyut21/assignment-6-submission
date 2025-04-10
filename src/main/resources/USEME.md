# USEME – How to Use the Calendar GUI

## Launching the Application
* Run the program with no command-line arguments (e.g., double-click on the jar file or run `java -jar CalendarApp.jar`) to launch the graphical user interface.

## Viewing the Calendar
* The top panel displays the **current calendar name** and the current month.
* The month view shows all days in the current month arranged in a 7-day grid.
    * Days with one or more events are highlighted in **lavender**.
    * If no event exists on a day, it shows the system default button color.

## Navigating Through Months
* Use the left button (`<`) to navigate to the previous month.
* Use the right button (`>`) to navigate to the next month.
* The month label updates automatically to reflect the current month.

## Viewing Events on a Specific Day
* Click on any day button in the month view.
    * The side panel on the right will show all events scheduled for that day.
* The side panel displays the event details in a formatted list (name, time, location, etc.).

## Adding a New Event
* In the side panel below the event display, click the **"Add Event"** button.
* The event creation dialog will appear:
    * Enter the event name, date (defaulted to the selected day or the current date), start time, and end time.
    * Fill in additional details like description and location.
    * For recurring events, you may select the "Recurring" checkbox and specify the weekdays and either the number of occurrences or an end datetime.
    * Click **"Create"** to add the event.
* Once added, the corresponding day in the month view will change its background to lavender if the event creation is successful.

## Editing an Existing Event
* Use the **"Edit Event"** option under the **Event** menu.
* In the edit dialog, provide the event name, start and end datetime, the property you want to change (e.g., name, description, location, or ispublic), and the new value.
* Click **"Save"** to apply changes.

## Copying Events
* Select **"Copy Event"** from the **Event** menu to open the copy event dialog.
* The dialog offers three tabs for different copy operations:
    * **Single Event:** Enter the event name, source datetime, target calendar name, and target datetime.
    * **Events On Date:** Specify a date and target calendar plus target datetime to copy all events on that date.
    * **Events Between Dates:** Provide a start date and an end date, along with the target calendar and target date, to copy events that fall within this interval.
* Complete the fields for the desired copy tab and click the corresponding copy button.

## Calendar Management
* **Create a New Calendar:**
    * Choose **"New Calendar"** from the **Calendar** menu.
    * In the dialog, type in the desired calendar name and timezone (e.g., `America/New_York`), then click **"Create"**.
* **Switching Calendars:**
    * Select **"Select Calendar"** from the **Calendar** menu.
    * Choose the desired calendar from the dropdown list and click **"OK"**.
    * The current calendar name on the top panel will update.
* **Edit Calendar Timezone:**
    * Select **"Edit Calendar Timezone"** from the **Calendar** menu.
    * Enter a new timezone for the current calendar and click **"Save"**.

## Importing and Exporting Calendars
* **Export Calendar:**
    * Choose **"Export CSV"** from the **File** menu.
    * Select a location to save the CSV file. The exported file is formatted for Google Calendar import.
* **Import Calendar:**
    * Choose **"Import CSV"** from the **File** menu.
    * Select a CSV file that was exported from the application.
    * The program will import events from the file into the current calendar and display the number of events imported.

## Error Handling
* If any operation fails (e.g., due to invalid input), a clear error message will be displayed using pop-up dialog boxes.
* Follow the instructions in the error messages to correct the problem.