package calendar.view.dialog;

import calendar.controller.CalendarController;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/** A dialog that provides functionality for copying events. */
public class CopyEventDialog extends JDialog {
  private final CalendarController controller;

  /**
   * Constructs the CopyEventDialog.
   *
   * @param parent the parent frame for this dialog
   * @param controller the CalendarController to handle copy operations
   */
  public CopyEventDialog(JFrame parent, CalendarController controller) {
    super(parent, "Copy Event", true);
    this.controller = controller;
    initComponents();
  }

  /** Initializes the dialog components and layout. */
  private void initComponents() {
    JTabbedPane tabbedPane = new JTabbedPane();

    // Tab 1: Single Event
    JPanel singlePanel = createSingleEventPanel();
    tabbedPane.addTab("Single Event", singlePanel);

    // Tab 2: Events On a Specific Date
    JPanel onDatePanel = createEventsOnPanel();
    tabbedPane.addTab("Events On Date", onDatePanel);

    // Tab 3: Events Between Two Dates
    JPanel betweenPanel = createEventsBetweenPanel();
    tabbedPane.addTab("Events Between Dates", betweenPanel);

    add(tabbedPane);
    pack();
    setLocationRelativeTo(getParent());
  }

  /**
   * Creates the panel for copying a single event.
   *
   * @return the constructed JPanel for the single event tab
   */
  private JPanel createSingleEventPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
    JTextField nameField = new JTextField(20);
    JTextField sourceDateTimeField = new JTextField(15);
    JTextField targetCalField = new JTextField(20);
    JTextField targetDateTimeField = new JTextField(15);
    JButton copyButton = new JButton("Copy Single Event");
    JButton cancelButton = new JButton("Cancel");

    panel.add(new JLabel("Event Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Source DateTime (yyyy-MM-dd'T'HH:mm):"));
    panel.add(sourceDateTimeField);
    panel.add(new JLabel("Target Calendar Name:"));
    panel.add(targetCalField);
    panel.add(new JLabel("Target DateTime (yyyy-MM-dd'T'HH:mm):"));
    panel.add(targetDateTimeField);
    panel.add(copyButton);
    panel.add(cancelButton);

    copyButton.addActionListener(
        (ActionEvent e) -> {
          String name = nameField.getText().trim();
          String sourceDT = sourceDateTimeField.getText().trim();
          String targetCal = targetCalField.getText().trim();
          String targetDT = targetDateTimeField.getText().trim();
          if (name.isEmpty() || sourceDT.isEmpty() || targetCal.isEmpty() || targetDT.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          try {
            controller.copyEvent(name, sourceDT, targetCal, targetDT);
            JOptionPane.showMessageDialog(this, "Single event copied successfully!");
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error copying single event: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });
    cancelButton.addActionListener((ActionEvent e) -> dispose());
    return panel;
  }

  /**
   * Creates the panel for copying all events on a specific date.
   *
   * @return the constructed JPanel for the "Events On Date" tab
   */
  private JPanel createEventsOnPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
    JTextField dateField = new JTextField(10);
    JTextField targetCalField = new JTextField(20);
    JTextField targetDateTimeField = new JTextField(15);
    JButton copyButton = new JButton("Copy Events On Date");
    JButton cancelButton = new JButton("Cancel");

    panel.add(new JLabel("Date (yyyy-MM-dd):"));
    panel.add(dateField);
    panel.add(new JLabel("Target Calendar Name:"));
    panel.add(targetCalField);
    panel.add(new JLabel("Target DateTime (yyyy-MM-dd'T'HH:mm):"));
    panel.add(targetDateTimeField);
    panel.add(copyButton);
    panel.add(cancelButton);

    copyButton.addActionListener(
        (ActionEvent e) -> {
          String dateStr = dateField.getText().trim();
          String targetCal = targetCalField.getText().trim();
          String targetDT = targetDateTimeField.getText().trim();
          if (dateStr.isEmpty() || targetCal.isEmpty() || targetDT.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          try {
            controller.copyEventsOn(dateStr, targetCal, targetDT);
            JOptionPane.showMessageDialog(this, "Events on date copied successfully!");
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error copying events on date: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });
    cancelButton.addActionListener((ActionEvent e) -> dispose());
    return panel;
  }

  /**
   * Creates the panel for copying events between two dates.
   *
   * @return the constructed JPanel for the "Events Between Dates" tab
   */
  private JPanel createEventsBetweenPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
    JTextField startDateField = new JTextField(10);
    JTextField endDateField = new JTextField(10);
    JTextField targetCalField = new JTextField(20);
    JTextField targetDateField = new JTextField(10);
    JButton copyButton = new JButton("Copy Events Between Dates");
    JButton cancelButton = new JButton("Cancel");

    panel.add(new JLabel("Start Date (yyyy-MM-dd):"));
    panel.add(startDateField);
    panel.add(new JLabel("End Date (yyyy-MM-dd):"));
    panel.add(endDateField);
    panel.add(new JLabel("Target Calendar Name:"));
    panel.add(targetCalField);
    panel.add(new JLabel("Target Date (yyyy-MM-dd):"));
    panel.add(targetDateField);
    panel.add(copyButton);
    panel.add(cancelButton);

    copyButton.addActionListener(
        (ActionEvent e) -> {
          String startDateStr = startDateField.getText().trim();
          String endDateStr = endDateField.getText().trim();
          String targetCal = targetCalField.getText().trim();
          String targetDateStr = targetDateField.getText().trim();
          if (startDateStr.isEmpty()
              || endDateStr.isEmpty()
              || targetCal.isEmpty()
              || targetDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          try {
            controller.copyEventsBetween(startDateStr, endDateStr, targetCal, targetDateStr);
            JOptionPane.showMessageDialog(this, "Events between dates copied successfully!");
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error copying events between dates: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });
    cancelButton.addActionListener((ActionEvent e) -> dispose());
    return panel;
  }
}
