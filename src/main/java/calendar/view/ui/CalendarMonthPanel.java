package calendar.view.ui;

import calendar.controller.CalendarController;
import calendar.model.event.Event;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class CalendarMonthPanel extends JPanel {

  private final Color lavender = new Color(230, 230, 250);
  private LocalDate currentDate;
  private final CalendarController controller;
  private DaySelectedListener daySelectedListener;

  public CalendarMonthPanel(CalendarController controller) {
    super(new GridLayout(0, 7));
    this.controller = controller;
  }

  public void setDaySelectedListener(DaySelectedListener listener) {
    this.daySelectedListener = listener;
  }

  public void drawMonth(LocalDate currentDate) {
    this.currentDate = currentDate;
    removeAll();

    // Create headers
    String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    for (String header : headers) {
      JLabel headerLabel = new JLabel(header, JLabel.CENTER);
      headerLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      add(headerLabel);
    }
    LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
    int firstDayValue = firstOfMonth.getDayOfWeek().getValue();
    int startIndex = firstDayValue % 7;
    int daysInMonth = currentDate.lengthOfMonth();
    for (int i = 0; i < startIndex; i++) {
      add(new JLabel(""));
    }
    for (int day = 1; day <= daysInMonth; day++) {
      JButton dayButton = new JButton(String.valueOf(day));
      dayButton.setOpaque(true);
      dayButton.setContentAreaFilled(true);
      LocalDate date = currentDate.withDayOfMonth(day);
      try {
        List<Event> events = controller.getEventsOn(date.toString());
        if (!events.isEmpty()) {
          dayButton.setBackground(lavender);
        } else {
          dayButton.setBackground(UIManager.getColor("Button.background"));
        }
      } catch (Exception ex) {
        dayButton.setBackground(lavender);
      }
      dayButton.addActionListener(
          (ActionEvent e) -> {
            if (daySelectedListener != null) {
              daySelectedListener.onDaySelected(date);
            }
          });
      add(dayButton);
    }
    revalidate();
    repaint();
  }

  public interface DaySelectedListener {
    void onDaySelected(LocalDate date);
  }
}
