package calendar.view.views;

import calendar.model.event.Event;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Provides static methods for formatting calendar events for display. */
public class CalendarView {
  private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

  /**
   * Formats the events on a specific date into a displayable string.
   *
   * @param date the date as a string
   * @param events the list of events on that date
   * @return the formatted string representing the events and if none a message indicating so
   */
  public static String formatEventsOn(String date, List<Event> events) {
    if (events.isEmpty()) {
      return "No events on " + date;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Events on ").append(date).append(":\n");
    for (Event e : events) {
      boolean isAllDay =
          (e.getStart().getHour() == 0
              && e.getStart().getMinute() == 0
              && e.getEnd().getHour() == 23
              && e.getEnd().getMinute() == 59);
      sb.append(" - ").append(e.getName());
      if (isAllDay) {
        sb.append(" All Day Event ");
      } else {
        sb.append(" (")
            .append(timeFormatter.format(e.getStart()))
            .append(" to ")
            .append(timeFormatter.format(e.getEnd()))
            .append(")");
      }
      if (e.getLocation() != null
          && !e.getLocation().isEmpty()) {
        sb.append(" at ").append(e.getLocation());
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Formats the events between two date-times.
   *
   * @param start the starting date time string
   * @param end the ending date time string
   * @param events the list of events between the given date–times
   * @return the formatted string representing the events and if none a message indicating so
   */
  public static String formatEventsBetween(String start, String end, List<Event> events) {
    if (events.isEmpty()) {
      return "No events between " + start + " and " + end;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Events from ").append(start).append(" to ").append(end).append(":\n");
    for (Event e : events) {
      sb.append(" - ")
          .append(e.getName())
          .append(" (")
          .append(timeFormatter.format(e.getStart()))
          .append(" to ")
          .append(timeFormatter.format(e.getEnd()))
          .append(") at ")
          .append(e.getLocation())
          .append("\n");
    }
    return sb.toString();
  }

  /**
   * Formats the busy status for a given date–time.
   *
   * @param dateTime the date time string to check
   * @param busy true if busy and false otherwise
   * @return a formatted string representing the busy status
   */
  public static String formatBusyStatus(String dateTime, boolean busy) {
    return "Status at " + dateTime + ": " + (busy ? "Busy" : "Available");
  }
}
