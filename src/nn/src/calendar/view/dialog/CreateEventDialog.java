package calendar.view.dialog;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import calendar.controller.CalendarController;

/**
 * Dialog for creating a new event. This dialog allows the user to create a single event or a
 * recurring event by specifying necessary details.
 */
public class CreateEventDialog extends JDialog {
  private final CalendarController controller;
  private final LocalDate defaultDate;
  private JTextField nameField;
  private JTextField dateField;
  private JTextField startTimeField;
  private JTextField endTimeField;
  private JTextField descriptionField;
  private JTextField locationField;
  private JCheckBox recurringCheck;
  private JTextField weekdaysField;
  private JTextField occurrencesField;
  private JTextField untilField;

  /**
   * Constructs the CreateEventDialog.
   *
   * @param parent the parent frame for this dialog
   * @param controller the CalendarController to handle event creation
   * @param defaultDate the default date to prefill the date field
   */
  public CreateEventDialog(JFrame parent, CalendarController controller, LocalDate defaultDate) {
    super(parent, "Create Event", true);
    this.controller = controller;
    this.defaultDate = defaultDate;
    initComponents();
  }

  /** Initializes the dialog components and layout. */
  private void initComponents() {
    nameField = new JTextField(20);
    dateField = new JTextField(10);
    dateField.setText(defaultDate.toString());
    startTimeField = new JTextField(5);
    startTimeField.setText("10:00");
    endTimeField = new JTextField(5);
    endTimeField.setText("11:00");
    descriptionField = new JTextField(20);
    locationField = new JTextField(20);
    recurringCheck = new JCheckBox("Recurring");
    weekdaysField = new JTextField(5);
    occurrencesField = new JTextField(5);
    untilField = new JTextField(15);
    JButton createButton = new JButton("Create");
    JButton cancelButton = new JButton("Cancel");

    JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
    panel.add(new JLabel("Event Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Date (yyyy-MM-dd):"));
    panel.add(dateField);
    panel.add(new JLabel("Start Time (HH:mm):"));
    panel.add(startTimeField);
    panel.add(new JLabel("End Time (HH:mm):"));
    panel.add(endTimeField);
    panel.add(new JLabel("Description:"));
    panel.add(descriptionField);
    panel.add(new JLabel("Location:"));
    panel.add(locationField);
    panel.add(recurringCheck);
    panel.add(new JLabel(""));
    panel.add(new JLabel("Weekdays (e.g., MTW):"));
    panel.add(weekdaysField);
    panel.add(new JLabel("Occurrences (optional):"));
    panel.add(occurrencesField);
    panel.add(new JLabel("Until DateTime (optional, yyyy-MM-dd'T'HH:mm):"));
    panel.add(untilField);
    panel.add(createButton);
    panel.add(cancelButton);

    add(panel);
    pack();
    setLocationRelativeTo(getParent());

    createButton.addActionListener(
        (ActionEvent e) -> {
          String name = nameField.getText().trim();
          String dateStr = dateField.getText().trim();
          String startTime = startTimeField.getText().trim();
          String endTime = endTimeField.getText().trim();
          String description = descriptionField.getText().trim();
          String location = locationField.getText().trim();
          boolean isRecurring = recurringCheck.isSelected();
          try {
            String startDateTime = dateStr + "T" + startTime;
            String endDateTime = dateStr + "T" + endTime;
            if (!isRecurring) {
              controller.createSingleEvent(
                  name, startDateTime, endDateTime, description, location, true, false);
              JOptionPane.showMessageDialog(this, "Event created successfully!");
            } else {
              String weekdays = weekdaysField.getText().trim();
              String occStr = occurrencesField.getText().trim();
              String untilStr = untilField.getText().trim();
              if (!occStr.isEmpty()) {
                int occurrences = Integer.parseInt(occStr);
                controller.createRecurringEventOccurrences(
                    name,
                    startDateTime,
                    endDateTime,
                    description,
                    location,
                    true,
                    weekdays,
                    occurrences,
                    false);
                JOptionPane.showMessageDialog(
                    this, "Recurring event created with " + occurrences + " occurrences.");
              } else if (!untilStr.isEmpty()) {
                controller.createRecurringEventUntil(
                    name,
                    startDateTime,
                    endDateTime,
                    description,
                    location,
                    true,
                    weekdays,
                    untilStr,
                    false);
                JOptionPane.showMessageDialog(
                    this, "Recurring event created until " + untilStr + ".");
              } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Please specify either occurrences or an until date for recurring event.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
              }
            }
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        });

    cancelButton.addActionListener((ActionEvent e) -> dispose());
  }
}
