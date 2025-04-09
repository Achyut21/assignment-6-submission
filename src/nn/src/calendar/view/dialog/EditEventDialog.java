package calendar.view.dialog;

import calendar.controller.CalendarController;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A dialog for editing an event's property (such as name, description, location, or visibility).
 */
public class EditEventDialog extends JDialog {
  private final CalendarController controller;
  private JTextField nameField;
  private JTextField startField; // expects yyyy-MM-dd'T'HH:mm format
  private JTextField endField; // expects yyyy-MM-dd'T'HH:mm format
  private JTextField propertyField;
  private JTextField newValueField;

  /**
   * Constructs the EditEventDialog.
   *
   * @param parent the parent frame
   * @param controller the CalendarController to process the event edit
   */
  public EditEventDialog(JFrame parent, CalendarController controller) {
    super(parent, "Edit Event", true);
    this.controller = controller;
    initComponents();
  }

  /** Initializes the dialog components and layout. */
  private void initComponents() {
    nameField = new JTextField(20);
    startField = new JTextField(15);
    endField = new JTextField(15);
    propertyField = new JTextField(10);
    newValueField = new JTextField(20);
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");

    JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
    panel.add(new JLabel("Event Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Start DateTime (yyyy-MM-dd'T'HH:mm):"));
    panel.add(startField);
    panel.add(new JLabel("End DateTime (yyyy-MM-dd'T'HH:mm):"));
    panel.add(endField);
    panel.add(new JLabel("Property (name, description, location, ispublic):"));
    panel.add(propertyField);
    panel.add(new JLabel("New Value:"));
    panel.add(newValueField);
    panel.add(saveButton);
    panel.add(cancelButton);

    add(panel);
    pack();
    setLocationRelativeTo(getParent());

    saveButton.addActionListener(
        (ActionEvent e) -> {
          String name = nameField.getText().trim();
          String startDT = startField.getText().trim();
          String endDT = endField.getText().trim();
          String property = propertyField.getText().trim();
          String newValue = newValueField.getText().trim();
          if (name.isEmpty()
              || startDT.isEmpty()
              || endDT.isEmpty()
              || property.isEmpty()
              || newValue.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          try {
            controller.editSingleEvent(property, name, startDT, endDT, newValue);
            JOptionPane.showMessageDialog(this, "Event edited successfully!");
            dispose();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error editing event: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    cancelButton.addActionListener((ActionEvent e) -> dispose());
  }
}
