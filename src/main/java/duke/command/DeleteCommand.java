package duke.command;

import java.util.List;

import duke.filehandler.FileStorage;
import duke.task.Task;
import duke.userinterface.UserInterface.MessageDisplay;

/**
 * Represents a `DeleteCommand` to delete a task from the task list.
 */
public class DeleteCommand extends ModifyTaskCommand {

    /**
     * Constructs a `DeleteCommand` with the specified item index to delete.
     *
     * @param itemIndex The index of the task to be deleted.
     */
    public DeleteCommand(int itemIndex) {

        super(itemIndex);
    }

    /**
     * Executes the command by removing the specified task from the task list.
     *
     * @param fileStorage The file storage handler for saving tasks to a file.
     * @param display     The message display interface to show messages to the user.
     * @param taskList    The list of tasks from which the task will be deleted.
     */
    @Override
    public void execute(FileStorage fileStorage, MessageDisplay display, List<Task> taskList) {
        assert !taskList.isEmpty() : "Task list can't be empty!";
        // Remove the task from the task list
        Task deletedTask = taskList.remove(itemIndex);
        storeDuke(fileStorage, taskList);
        // Display a message indicating the deleted task
        display.deleteMessage(taskList, deletedTask);
    }

}
