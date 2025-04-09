package calendar.view;

import calendar.controller.CalendarController;
import calendar.view.dialog.CreateEventDialog;
import calendar.view.ui.CalendarGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CalendarSidePanel extends JPanel {

  private JTextArea eventTextArea;
  private JButton addEventButton;
  private CalendarGUI parent;
  private CalendarController controller;

  public CalendarSidePanel(CalendarGUI parent, CalendarController controller) {
    this.parent = parent;
    this.controller = controller;
    initSidePanel();
  }

  private void initSidePanel() {
    setLayout(new BorderLayout());
    eventTextArea = new JTextArea();
    eventTextArea.setEditable(false);
    JScrollPane eventScrollPane = new JScrollPane(eventTextArea);
    eventScrollPane.setPreferredSize(new Dimension(300, 600));

    addEventButton = new JButton("Add Event");
    addEventButton.addActionListener((ActionEvent e) -> {
      LocalDate dateForEvent = parent.getCurrentSelectedDateOrDefault();
      new CreateEventDialog(parent, controller, dateForEvent).setVisible(true);
      parent.refreshView();
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addEventButton);

    add(eventScrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  public void updateEvents(String eventsText) {
    eventTextArea.setText(eventsText);
  }
}
