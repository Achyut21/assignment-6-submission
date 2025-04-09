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
import javax.swing.JTextField;

/** A dialog for editing the current calendar's timezone. */
public class EditCalendarDialog extends JDialog {
  private final CalendarController controller;
  private JTextField timezoneField;

  /**
   * Constructs the EditCalendarDialog.
   *
   * @param parent the parent frame
   * @param controller the CalendarController to perform the edit
   */
  public EditCalendarDialog(JFrame parent, CalendarController controller) {
    super(parent, "Edit Calendar Timezone", true);
    this.controller = controller;
    initComponents();
  }

  /** Initializes the components and layout for the dialog. */
  private void initComponents() {
    timezoneField = new JTextField(20);
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");

    JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
    panel.add(new JLabel("New Timezone:"));
    panel.add(timezoneField);
    panel.add(saveButton);
    panel.add(cancelButton);
    add(panel);
    pack();
    setLocationRelativeTo(getParent());

    saveButton.addActionListener(
        (ActionEvent e) -> {
          String newTimezone = timezoneField.getText().trim();
          if (newTimezone.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please enter a timezone.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }

          String currentCalName = JOptionPane.showInputDialog(this, "Enter current calendar name:");
          if (currentCalName == null || currentCalName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Calendar name required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          try {
            controller.editCalendar(currentCalName, "timezone", newTimezone);
            JOptionPane.showMessageDialog(this, "Calendar timezone updated to: " + newTimezone);
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        });
    cancelButton.addActionListener((ActionEvent e) -> dispose());
  }
}
