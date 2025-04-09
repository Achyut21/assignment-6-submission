package calendar.view.ui;

import calendar.controller.CalendarController;
import calendar.model.event.Event;
import calendar.view.views.CalendarView;
import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A graphical user interface for the Calendar Application.
 */
public class CalendarGUI extends JFrame {

  private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
  private final CalendarController controller;
  private LocalDate currentDate;
  private LocalDate currentSelectedDate = null;
  private CalendarTopPanel topPanel;
  private CalendarMonthPanel monthPanel;
  private CalendarSidePanel sidePanel;

  /**
   * Constructs a new CalendarGUI with the specified controller.
   *
   * @param controller the CalendarController used for calendar operations
   */
  public CalendarGUI(CalendarController controller) {
    this.controller = controller;
    this.currentDate = LocalDate.now();
    initUI();
    this.setVisible(true);
  }

  /**
   * Initializes the user interface components.
   */
  private void initUI() {
    setTitle("Calendar Application - GUI");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 700);
    setLocationRelativeTo(null);

    topPanel =
        new CalendarTopPanel(
            controller.getCurrentCalendarName(),
            currentDate.format(monthFormatter),
            () -> {
              currentDate = currentDate.minusMonths(1);
              topPanel.updateMonthLabel(currentDate.format(monthFormatter));
              monthPanel.drawMonth(currentDate);
            },
            () -> {
              currentDate = currentDate.plusMonths(1);
              topPanel.updateMonthLabel(currentDate.format(monthFormatter));
              monthPanel.drawMonth(currentDate);
            });

    monthPanel = new CalendarMonthPanel(controller);
    monthPanel.setDaySelectedListener((LocalDate date) -> displayEventsForDay(date));

    sidePanel = new CalendarSidePanel(this, controller);

    setJMenuBar(new CalendarMenuBar(this, controller));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(topPanel, BorderLayout.NORTH);
    getContentPane().add(monthPanel, BorderLayout.CENTER);
    getContentPane().add(sidePanel, BorderLayout.EAST);

    // FIX: Draw the month view initially so that day buttons appear on launch.
    monthPanel.drawMonth(currentDate);
  }

  /**
   * Returns the selected date if available otherwise returns the current date.
   *
   * @return the currently selected date or current date as default
   */
  public LocalDate getCurrentSelectedDateOrDefault() {
    return (currentSelectedDate != null) ? currentSelectedDate : currentDate;
  }

  /**
   * Refreshes the view by redrawing the month panel and updating event information.
   */
  public void refreshView() {
    monthPanel.drawMonth(currentDate);
    if (currentSelectedDate != null) {
      displayEventsForDay(currentSelectedDate);
    }
    topPanel.updateCalendarName(controller.getCurrentCalendarName());
  }

  /**
   * Displays the events for the specified day by updating the side panel.
   *
   * @param date the date for which to display events
   */
  private void displayEventsForDay(LocalDate date) {
    currentSelectedDate = date;
    try {
      List<Event> events = controller.getEventsOn(date.toString());
      String eventsText = CalendarView.formatEventsOn(date.toString(), events);
      sidePanel.updateEvents(eventsText);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(
          this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Exports the current calendar to a CSV file.
   */
  public void exportCalendar() {
    JFileChooser chooser = new JFileChooser();
    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      String path = chooser.getSelectedFile().getAbsolutePath();
      try {
        String result = controller.exportCalendar(path);
        JOptionPane.showMessageDialog(this, "Calendar exported to CSV at:\n" + result);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(
            this, "Error exporting: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Imports events from a CSV file into the current calendar using the controller.
   */
  void importCalendar() {
    JFileChooser chooser = new JFileChooser();
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      String path = chooser.getSelectedFile().getAbsolutePath();
      try {
        int count = controller.importCalendar(path);
        JOptionPane.showMessageDialog(this, "Import successful. " + count + " events imported.");
        monthPanel.drawMonth(currentDate);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(
            this, "Error importing: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
