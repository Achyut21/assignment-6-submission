package calendar.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarTopPanel extends JPanel {

  private JLabel currentCalLabel;
  private JLabel monthLabel;
  private JButton prevButton;
  private JButton nextButton;

  public CalendarTopPanel(String calendarName, String formattedMonth,
                          Runnable onPrev, Runnable onNext) {
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

  public void updateMonthLabel(String formattedMonth) {
    monthLabel.setText(formattedMonth);
  }

  public void updateCalendarName(String calendarName) {
    currentCalLabel.setText("Calendar: " + calendarName);
  }
}
