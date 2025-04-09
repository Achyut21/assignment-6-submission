package calendar.controller.importer;

import calendar.model.Calendar;
import calendar.model.event.SingleEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVCalendarImporter implements CalendarImporter {

  @Override
  public int importCalendar(Calendar calendar, String fileName) throws Exception {
    // The CSV export writes dates in "MM/dd/yyyy" format.
    DateTimeFormatter csvDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    int importCount = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      boolean firstLine = true;
      while ((line = reader.readLine()) != null) {
        if (firstLine) {
          firstLine = false; // Skip header line.
          continue;
        }
        String[] tokens = line.split(",");
        if (tokens.length < 9) continue;

        // Remove quotes
        String subject = tokens[0].replace("\"", "");
        String startDateStr = tokens[1].replace("\"", "");
        String startTime = tokens[2].replace("\"", "");
        String endDateStr = tokens[3].replace("\"", "");
        String endTime = tokens[4].replace("\"", "");
        String allDay = tokens[5].replace("\"", "");
        String description = tokens[6].replace("\"", "");
        String location = tokens[7].replace("\"", "");

        // Parse dates using the CSV formatter.
        LocalDate startDate = LocalDate.parse(startDateStr, csvDateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, csvDateFormatter);

        // If the event is marked as all-day, use "00:00" and "23:59" respectively.
        String startDateTimeStr =
            startDate + "T" + (allDay.equalsIgnoreCase("True") ? "00:00" : startTime);
        String endDateTimeStr =
            endDate + "T" + (allDay.equalsIgnoreCase("True") ? "23:59" : endTime);

        // Parse the datetime strings.
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr);

        // Create the event
        SingleEvent event =
            new SingleEvent(subject, startDateTime, endDateTime, description, location, true);
        calendar.addEvent(event, false);
        importCount++;
      }
    }
    return importCount;
  }
}
