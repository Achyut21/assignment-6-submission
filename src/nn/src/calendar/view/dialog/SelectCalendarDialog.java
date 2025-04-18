package calendar.view.dialog;

import calendar.controller.CalendarController;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** A dialog for selecting an existing calendar. */
public class SelectCalendarDialog extends JDialog {
  /**
   * Constructs the SelectCalendarDialog.
   *
   * @param parent the parent frame
   * @param controller the CalendarController to retrieve and switch calendars
   */
  public SelectCalendarDialog(JFrame parent, CalendarController controller) {
    super(parent, "Select Calendar", true);
    java.util.Set<String> calNames = controller.getCalendarNames();
    if (calNames == null || calNames.isEmpty()) {
      add(new JLabel("No calendars available."));
    } else {
      // Create a combo box for calendar selection.
      JComboBox<String> calendarCombo = new JComboBox<>(calNames.toArray(new String[0]));
      JButton okButton = new JButton("OK");
      JButton cancelButton = new JButton("Cancel");

      // Panel for combo box and label.
      JPanel selectionPanel = new JPanel();
      selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
      selectionPanel.add(new JLabel("Select a calendar:"));
      selectionPanel.add(calendarCombo);

      // Panel for buttons.
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);

      // Main panel.
      JPanel mainPanel = new JPanel(new BorderLayout());
      mainPanel.add(selectionPanel, BorderLayout.CENTER);
      mainPanel.add(buttonPanel, BorderLayout.SOUTH);

      add(mainPanel);

      // Action for OK button: switch to the selected calendar.
      okButton.addActionListener(
          (ActionEvent e) -> {
            String selectedCal = (String) calendarCombo.getSelectedItem();
            try {
              controller.useCalendar(selectedCal);
              JOptionPane.showMessageDialog(this, "Switched to calendar: " + selectedCal);
              dispose();
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(
                  this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
          });

      // Cancel button closes the dialog.
      cancelButton.addActionListener((ActionEvent e) -> dispose());
    }
    pack();
    setLocationRelativeTo(getParent());
  }
}
