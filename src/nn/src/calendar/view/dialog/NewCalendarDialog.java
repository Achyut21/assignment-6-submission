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

/** A dialog for creating a new calendar. */
public class NewCalendarDialog extends JDialog {
  private final CalendarController controller;
  private JTextField nameField;
  private JTextField timezoneField;

  /**
   * Constructs the NewCalendarDialog.
   *
   * @param parent the parent frame
   * @param controller the CalendarController to create the new calendar
   */
  public NewCalendarDialog(JFrame parent, CalendarController controller) {
    super(parent, "New Calendar", true);
    this.controller = controller;
    initComponents();
  }

  /** Initializes the components and layout for the dialog. */
  private void initComponents() {
    nameField = new JTextField(20);
    timezoneField = new JTextField(20);
    JButton createButton = new JButton("Create");
    JButton cancelButton = new JButton("Cancel");

    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
    panel.add(new JLabel("Calendar Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Timezone (e.g., America/New_York):"));
    panel.add(timezoneField);
    panel.add(createButton);
    panel.add(cancelButton);

    add(panel);
    pack();
    setLocationRelativeTo(getParent());

    createButton.addActionListener(
        (ActionEvent e) -> {
          String name = nameField.getText().trim();
          String timezone = timezoneField.getText().trim();
          if (name.isEmpty() || timezone.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          try {
            controller.createCalendar(name, timezone);
            JOptionPane.showMessageDialog(this, "Calendar created: " + name);
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        });

    cancelButton.addActionListener((ActionEvent e) -> dispose());
  }
}
