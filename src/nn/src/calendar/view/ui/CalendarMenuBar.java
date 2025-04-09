package calendar.view.ui;

import calendar.controller.CalendarController;
import calendar.view.dialog.CopyEventDialog;
import calendar.view.dialog.CreateEventDialog;
import calendar.view.dialog.EditCalendarDialog;
import calendar.view.dialog.EditEventDialog;
import calendar.view.dialog.NewCalendarDialog;
import calendar.view.dialog.SelectCalendarDialog;
import java.time.LocalDate;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The menu bar for the calendar application. It provides menus for File, Calendar, and Event
 * operations.
 */
public class CalendarMenuBar extends JMenuBar {

  /**
   * Constructs a CalendarMenuBar.
   *
   * @param parent the parent CalendarGUI frame
   * @param controller the CalendarController to handle operations
   */
  public CalendarMenuBar(CalendarGUI parent, CalendarController controller) {
    // File Menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem importItem = new JMenuItem("Import CSV");
    JMenuItem exportItem = new JMenuItem("Export CSV");
    JMenuItem exitItem = new JMenuItem("Exit");
    fileMenu.add(importItem);
    fileMenu.add(exportItem);
    fileMenu.addSeparator();
    fileMenu.add(exitItem);
    add(fileMenu);

    // Calendar Menu
    JMenu calendarMenu = new JMenu("Calendar");
    JMenuItem newCalItem = new JMenuItem("New Calendar");
    JMenuItem selectCalItem = new JMenuItem("Select Calendar");
    JMenuItem editCalItem = new JMenuItem("Edit Calendar Timezone");
    calendarMenu.add(newCalItem);
    calendarMenu.add(selectCalItem);
    calendarMenu.add(editCalItem);
    add(calendarMenu);

    // Event Menu
    JMenu eventMenu = new JMenu("Event");
    JMenuItem createEventItem = new JMenuItem("Create Event");
    JMenuItem editEventItem = new JMenuItem("Edit Event");
    JMenuItem copyEventItem = new JMenuItem("Copy Event");
    eventMenu.add(createEventItem);
    eventMenu.add(editEventItem);
    eventMenu.add(copyEventItem);
    add(eventMenu);

    // Attach Listeners
    exitItem.addActionListener(e -> System.exit(0));
    exportItem.addActionListener(e -> parent.exportCalendar());
    importItem.addActionListener(e -> parent.importCalendar());

    newCalItem.addActionListener(e -> {
      new NewCalendarDialog(parent, controller).setVisible(true);
      parent.refreshView();
    });
    selectCalItem.addActionListener(e -> {
      new SelectCalendarDialog(parent, controller).setVisible(true);
      parent.refreshView();
    });
    editCalItem.addActionListener(e -> new EditCalendarDialog(parent, controller).setVisible(true));
    createEventItem.addActionListener(e -> {
      LocalDate dateForEvent = parent.getCurrentSelectedDateOrDefault();
      new CreateEventDialog(parent, controller, dateForEvent).setVisible(true);
      parent.refreshView();
    });
    editEventItem.addActionListener(e -> new EditEventDialog(parent, controller).setVisible(true));
    copyEventItem.addActionListener(e -> new CopyEventDialog(parent, controller).setVisible(true));
  }
}
