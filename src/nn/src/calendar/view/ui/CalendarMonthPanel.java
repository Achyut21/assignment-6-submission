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

/**
 * A panel that displays the days of the current month in a grid. Days that have events are colored
 * in lavender.
 */
public class CalendarMonthPanel extends JPanel {

  private final Color lavender = new Color(230, 230, 250);
  private final CalendarController controller;
  private DaySelectedListener daySelectedListener;

  /**
   * Constructs a CalendarMonthPanel.
   *
   * @param controller the CalendarController used to obtain event data
   */
  public CalendarMonthPanel(CalendarController controller) {
    super(new GridLayout(0, 7));
    this.controller = controller;
  }

  /**
   * Sets the listener to be notified when a day is selected.
   *
   * @param listener the listener to notify
   */
  public void setDaySelectedListener(DaySelectedListener listener) {
    this.daySelectedListener = listener;
  }

  /**
   * Draws the month view for the provided date.
   *
   * @param currentDate the date representing the current month to display
   */
  public void drawMonth(LocalDate currentDate) {
    removeAll();

    // Create day-of-week headers.
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

    // Fill empty cells before the first day.
    for (int i = 0; i < startIndex; i++) {
      add(new JLabel(""));
    }
    // Create day buttons.
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

  /** Listener interface for when a day is selected. */
  public interface DaySelectedListener {
    /**
     * Called when a day is selected from the month view.
     *
     * @param date the selected date
     */
    void onDaySelected(LocalDate date);
  }
}
