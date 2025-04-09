package calendar.view.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A top panel for the calendar GUI that displays the current calendar name and month,
 * and provides navigation buttons.
 */
public class CalendarTopPanel extends JPanel {

  private final JLabel currentCalLabel;
  private final JLabel monthLabel;
  private final JButton prevButton;
  private final JButton nextButton;

  /**
   * Constructs a CalendarTopPanel.
   *
   * @param calendarName the name of the current calendar
   * @param formattedMonth the formatted month string
   * @param onPrev a Runnable to execute when the previous button is pressed
   * @param onNext a Runnable to execute when the next button is pressed
   */
  public CalendarTopPanel(
      String calendarName, String formattedMonth, Runnable onPrev, Runnable onNext) {
    setLayout(new FlowLayout());
    currentCalLabel = new JLabel("Calendar: " + calendarName);
    prevButton = new JButton("<");
    nextButton = new JButton(">");
    monthLabel = new JLabel(formattedMonth);

    add(currentCalLabel);
    add(prevButton);
    add(monthLabel);
    add(nextButton);

    prevButton.addActionListener((ActionEvent e) -> onPrev.run());
    nextButton.addActionListener((ActionEvent e) -> onNext.run());
  }

  /**
   * Updates the month label with the provided formatted month string.
   *
   * @param formattedMonth the new formatted month string
   */
  public void updateMonthLabel(String formattedMonth) {
    monthLabel.setText(formattedMonth);
  }

  /**
   * Updates the calendar name label with the provided name.
   *
   * @param calendarName the new calendar name
   */
  public void updateCalendarName(String calendarName) {
    currentCalLabel.setText("Calendar: " + calendarName);
  }
}
