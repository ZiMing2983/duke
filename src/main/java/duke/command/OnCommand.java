package duke.command;

import duke.task.Task;
import duke.userinterface.UserInterface.MessageDisplay;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an `OnCommand` to list and display user's tasks as of a specific date.
 */
public class OnCommand extends CheckTaskCommand {

  private final LocalDate checkedDate;

  public OnCommand(LocalDate checkedDate) {

    this.checkedDate = checkedDate;
  }

  /**
   * Displays the list of user tasks as of the specified date.
   *
   * @param display  The message display interface to show messages to the user.
   * @param taskList The list of user tasks to be checked.
   * @param date     The specific date for which tasks should be displayed.
   */
  public void checkTasks(List<Task> taskList, LocalDate date) {
    // Check if there are no tasks in the list
    if (taskList.isEmpty()) {
      System.out.println("There's nothing on " + date);
      MessageDisplay.printLineBreak();
      return;
    }
    boolean headerPrinted = false;
    int index = 1;
    // Display the date and the tasks as of that date
    for (Task task : taskList) {
      // Check if the task should be displayed based on the date
      if (task.checkDate(date)) {
        if (!headerPrinted) {
          System.out.println("Here are the tasks in your list as of " + date);
          MessageDisplay.printLineBreak();
          headerPrinted = true;
        }
        System.out.println(index + "." + task);
        index++;
      }
      if (index - 1 == taskList.size()) {
        MessageDisplay.printLineBreak();
      }
    }

    if (!headerPrinted) {
      System.out.printf("There's nothing on %s%n", date.toString());
      MessageDisplay.printLineBreak();
    }
  }

  /**
   * Executes the command by displaying the list of user tasks as of a specified date.
   *
   * @param display  The message display interface to show messages to the user.
   * @param taskList The list of user tasks to be checked.
   */
  @Override
  public void execute(MessageDisplay display, List<Task> taskList) {
    // Call the checkTasks method to display the tasks as of the specified date
    checkTasks(taskList, checkedDate);
  }

}
