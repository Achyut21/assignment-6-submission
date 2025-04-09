package calendar.view.ui;

import calendar.controller.CalendarController;
import calendar.view.dialog.CreateEventDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A side panel for the calendar GUI that displays events for the selected day and includes a button
 * for creating new events.
 */
public class CalendarSidePanel extends JPanel {

  private final CalendarGUI parent;
  private final CalendarController controller;
  private JTextArea eventTextArea;

  /**
   * Constructs a CalendarSidePanel.
   *
   * @param parent the parent CalendarGUI frame
   * @param controller the CalendarController to handle event operations
   */
  public CalendarSidePanel(CalendarGUI parent, CalendarController controller) {
    this.parent = parent;
    this.controller = controller;
    initSidePanel();
  }

  /** Initializes the side panel components. */
  private void initSidePanel() {
    setLayout(new BorderLayout());
    eventTextArea = new JTextArea();
    eventTextArea.setEditable(false);
    JScrollPane eventScrollPane = new JScrollPane(eventTextArea);
    eventScrollPane.setPreferredSize(new Dimension(300, 600));

    JButton addEventButton = new JButton("Add Event");
    addEventButton.addActionListener(
        (ActionEvent e) -> {
          LocalDate dateForEvent = parent.getCurrentSelectedDateOrDefault();
          new CreateEventDialog(parent, controller, dateForEvent).setVisible(true);
          parent.refreshView();
        });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addEventButton);

    add(eventScrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Updates the displayed events text.
   *
   * @param eventsText the new events text to display
   */
  public void updateEvents(String eventsText) {
    eventTextArea.setText(eventsText);
  }
}
