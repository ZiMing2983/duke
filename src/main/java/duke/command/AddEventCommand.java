package duke.command;

import java.time.LocalDateTime;
import java.util.List;

import duke.filehandler.FileStorage;
import duke.task.EventTask;
import duke.task.Task;
import duke.userinterface.UserInterface.MessageDisplay;

/**
 * Represents a command to add an event task to the task list.
 */
public class AddEventCommand extends AddTaskCommand {

    // The start date and time of the event task
    private final LocalDateTime taskFromDate;
    // The end date and time of the event task
    private final LocalDateTime taskToDate;

    /**
     * Constructs an `AddEventCommand` with the specified task name, start date, and end date.
     *
     * @param taskName     The name of the event task.
     * @param taskFromDate The start date and time of the event task.
     * @param taskToDate   The end date and time of the event task.
     */
    public AddEventCommand(String taskName, LocalDateTime taskFromDate, LocalDateTime taskToDate) {

        super(taskName);
        this.taskFromDate = taskFromDate;
        this.taskToDate = taskToDate;
    }

    /**
     * Executes the command by creating an `EventTask` and adding it to the task list.
     *
     * @param fileStorage The file storage handler for saving tasks to a file.
     * @param display     The message display interface to show messages to the user.
     * @param taskList    The list of tasks to which the new Event task will be added.
     */
    @Override
    public void execute(FileStorage fileStorage, MessageDisplay display, List<Task> taskList) {
        assert !taskName.isEmpty() : "Task name can't be blank!";
        // Create an `EventTask` with the specified name, start date, and end date
        Task task = new EventTask(taskName, taskFromDate, taskToDate);
        // Add the created `EventTask` to the task list
        addTask(fileStorage, display, taskList, task);
    }

}
