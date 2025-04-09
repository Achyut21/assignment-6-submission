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

public class CalendarGUI extends JFrame {

  private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
  private final CalendarController controller;
  private LocalDate currentDate;
  private LocalDate currentSelectedDate = null;
  private CalendarTopPanel topPanel;
  private CalendarMonthPanel monthPanel;
  private CalendarSidePanel sidePanel;

  public CalendarGUI(CalendarController controller) {
    this.controller = controller;
    this.currentDate = LocalDate.now();
    initUI();
    this.setVisible(true);
  }

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

  public LocalDate getCurrentSelectedDateOrDefault() {
    return (currentSelectedDate != null) ? currentSelectedDate : currentDate;
  }

  public void refreshView() {
    monthPanel.drawMonth(currentDate);
    if (currentSelectedDate != null) {
      displayEventsForDay(currentSelectedDate);
    }
    topPanel.updateCalendarName(controller.getCurrentCalendarName());
  }

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

  void importCalendar() {
    JFileChooser chooser = new JFileChooser();
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      String path = chooser.getSelectedFile().getAbsolutePath();
      try {
        // Call controller's import method.
        int count = controller.importCalendar(path);
        JOptionPane.showMessageDialog(this, "Import successful. " + count + " events imported.");
        // Redraw the month panel since events have changed.
        monthPanel.drawMonth(currentDate);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(
            this, "Error importing: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
