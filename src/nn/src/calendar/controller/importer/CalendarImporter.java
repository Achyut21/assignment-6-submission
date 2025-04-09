package calendar.controller.importer;

import calendar.model.Calendar;

public interface CalendarImporter {
  /**
   * Imports events into the provided Calendar from the specified CSV file. Returns the number of
   * events imported.
   */
  int importCalendar(Calendar calendar, String fileName) throws Exception;
}
