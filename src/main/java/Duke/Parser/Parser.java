package Duke.Parser;

import Duke.Command.*;
import Duke.DukeExceptions.*;
import Duke.Task.Task;
import Duke.UserInterface.UserInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The `Parser` class is responsible for parsing user input and converting it into
 * commands and parameters for execution.
 */
public class Parser {

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Parses a date and time string and returns a `LocalDateTime` object.
     *
     * @param dateTimeString The date and time string to be parsed.
     * @return A `LocalDateTime` object representing the parsed date and time.
     * @throws DateTimeParseException if the string cannot be parsed as a valid date and time.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString.replace("T", " "), dateTimeFormatter);
    }

    /**
     * Parses an integer from a string and returns it.
     *
     * @param integer The string containing the integer.
     * @return The parsed integer.
     * @throws InvalidNumberFormatException if the string is not a valid integer.
     */
    public static Integer parseInteger(String integer) throws InvalidNumberFormatException {
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormatException(e.getMessage());
        }
    }

    /**
     * Parse the user's input into commands and parameters for execution.
     *
     * @param userInput The user's input string of commands.
     */
    public void parseUserInput(String userInput) {
        try {
            String[] inputs = userInput.split("\\s+");
            if (inputs.length == 0 || userInput.isEmpty()) {
                throw new EmptyCommandException();
            }
            String command = inputs[0];
            executeCommand(command, userInput);
        } catch (DukeException e) {
            System.out.printf("%s\n%s\n", e.getMessage(), UserInterface.MessageDisplay.LINE_BREAK);
        }
    }

    /**
     * Execute the user's command.
     *
     * @param command   The parsed user command.
     * @param userInput The user's remaining string of commands excluding the command string.
     */
    private void executeCommand(String command, String userInput) throws DukeException {
        String arguments = userInput.substring(command.length()).trim();
        switch (command) {
            case "list":
                executeListCommand();
                break;
            case "todo":
                executeTodoCommand(arguments);
                break;
            case "deadline":
                executeDeadlineCommand(arguments);
                break;
            case "event":
                executeEventCommand(arguments);
                break;
            case "delete":
            case "mark":
            case "unmark":
                modifyTask(userInput);
                break;
            default:
                throw new InvalidCommandException();
        }
    }

    /**
     * Execute the user's "list" command.
     */
    private void executeListCommand() {
        new ListCommand().execute();
    }

    /**
     * Execute the "todo" task command.
     *
     * @param taskName The name of the todo task.
     */
    private void executeTodoCommand(String taskName) throws DukeException {
        if (taskName.isEmpty()) {
            throw new EmptyTodoArgumentException();
        }
        new AddTodoCommand(taskName).execute();
    }

    /**
     * Execute the "deadline" task command.
     *
     * @param arguments The user's input string after the deadline task command.
     */
    private void executeDeadlineCommand(String arguments) throws DukeException {
        // Check for empty arguments
        if (arguments.isEmpty()) {
            throw new EmptyDeadlineArgumentException();
        }
        // Find the positions of "/by"
        int byIndex = arguments.indexOf("/by");

        // Check if "/by" exist
        if (byIndex == -1) {
            throw new InvalidTaskFormatException("deadline");
        }

        // Extract task name, by date
        String taskName = arguments.substring(0, byIndex).trim();
        String taskDueDateString = arguments.substring(byIndex + 3).trim();

        // Check if date fields are empty
        if (taskDueDateString.isEmpty()) {
            throw new InvalidTaskFormatException("deadline");
        }

        try {
            // Parse date and time
            LocalDateTime taskDueDate = parseDateTime(taskDueDateString);
            // Add Deadline Task
            new AddDeadLineCommand(taskName, taskDueDate).execute();
        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("deadline");
        }

    }

    /**
     * Execute the "event" task command.
     *
     * @param arguments The user's input string after the event task command.
     */
    private void executeEventCommand(String arguments) throws DukeException {
        // Check for empty arguments
        if (arguments.isEmpty()) {
            throw new EmptyEventArgumentException();
        }
        // Find the positions of "/from" and "/to"
        int fromIndex = arguments.indexOf("/from");
        int toIndex = arguments.indexOf("/to");

        // Check if both "/from" and "/to" exist
        if (fromIndex == -1 || toIndex == -1) {
            throw new InvalidTaskFormatException("event");
        }

        // Extract task name, from date, and to date
        String taskName = arguments.substring(0, fromIndex).trim();
        String taskFromDateString = arguments.substring(fromIndex + 5, toIndex).trim();
        String taskToDateString = arguments.substring(toIndex + 3).trim();

        // Check if date fields are empty
        if (taskFromDateString.isEmpty() || taskToDateString.isEmpty()) {
            throw new InvalidTaskFormatException("event");
        }

        try {
            // Parse date and time
            LocalDateTime taskFromDate = parseDateTime(taskFromDateString);
            LocalDateTime taskToDate = parseDateTime(taskToDateString);
            // Add Event Task
            new AddEventCommand(taskName, taskFromDate, taskToDate).execute();
        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("event");
        }
    }

    /**
     * Execute "mark," "unmark," and "delete" commands that modify a task's status.
     *
     * @param userInput User's input string after a mark, unmark, or delete command.
     */
    public void modifyTask(String userInput) {
        try {
            int itemIndex = extractItemIndex(userInput);
            if (itemIndex == -1) {
                return;
            }
            switch (parseCommandFromInput(userInput)) {
                case "mark":
                    new MarkAsCompletedCommand(itemIndex).execute();
                    break;
                case "unmark":
                    new MarkAsInCompletedCommand(itemIndex).execute();
                    break;
                case "delete":
                    new DeleteCommand(itemIndex).execute();
                    break;
                default:
                    // Handle exception case where the command is neither mark nor unmark
                    throw new InvalidNumberFormatException();
            }
        } catch (InvalidNumberFormatException e) {
            // Handle the case where the integer part is not a valid number
            System.out.printf("%s\n%s\n", e.getMessage(), UserInterface.MessageDisplay.LINE_BREAK);
        }
    }

    /**
     * Parse command from user's input string
     *
     * @param userInput User's input string
     */
    private String parseCommandFromInput(String userInput) {
        int spaceIndex = userInput.indexOf(' ');
        return userInput.substring(0, spaceIndex);
    }

    /**
     * Extract the item index from mark, unmark, delete commands that modify a task's status.
     *
     * @param userInput User's input string.
     */
    private int extractItemIndex(String userInput) throws InvalidNumberFormatException {
        try {
            int spaceIndex = userInput.indexOf(' ');
            String integerPart = userInput.substring(spaceIndex + 1).trim();
            int itemIndex = parseInteger(integerPart) - 1;
            if (itemIndex < 0 || itemIndex >= Task.getTotalTasks()) {
                // Handle exception case where the item index is out of bounds or does not exist
                throw new TaskNotFoundException();
            }
            return itemIndex;
        } catch (TaskNotFoundException e) {
            // Handle the case where task is not found from index
            System.out.printf("%s\n%s\n", e.getMessage(), UserInterface.MessageDisplay.LINE_BREAK);
        }
        return -1;
    }
}