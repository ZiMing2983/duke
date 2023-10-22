package Duke.Task;

import java.time.LocalDateTime;

/**
 * Represents an `EventTask` class that extends `Task` and represents a task with an event.
 */
public class EventTask extends Task {
    private final LocalDateTime taskFrom;
    private final LocalDateTime taskTo;

    /**
     * Constructs an `EventTask` with the specified task name, start date, and end date.
     *
     * @param taskName     The name of the event task.
     * @param taskFrom     The start date and time of the event task.
     * @param taskTo       The end date and time of the event task.
     */
    public EventTask(String taskName, LocalDateTime taskFrom, LocalDateTime taskTo) {
        super('E', taskName);
        this.taskFrom = taskFrom;
        this.taskTo = taskTo;
    }

    /**
     * Constructs an `EventTask` with the specified task name, completion status, start date, and end date.
     *
     * @param taskName     The name of the event task.
     * @param completed    `true` if the task is completed, `false` otherwise.
     * @param taskFrom     The start date and time of the event task.
     * @param taskTo       The end date and time of the event task.
     */
    public EventTask(String taskName, boolean completed, LocalDateTime taskFrom, LocalDateTime taskTo) {
        super('E', taskName, completed);
        this.taskFrom = taskFrom;
        this.taskTo = taskTo;
    }

    /**
     * Gets the formatted start date and time of the task.
     *
     * @return The start date and time of the task as a formatted string.
     */
    private String getTaskFrom() {
        return dateTimetoString(taskFrom);
    }

    /**
     * Gets the formatted end date and time of the task.
     *
     * @return The end date and time of the task as a formatted string.
     */
    private String getTaskTo() {
        return dateTimetoString(taskTo);
    }

    /**
     * Converts the `EventTask` to a string for display.
     *
     * @return A string representation of the `EventTask`.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" (from: %s to: %s)", getTaskFrom(), getTaskTo());
    }

    /**
     * Converts the `EventTask` to a string for saving to Duke Data File.
     *
     * @return A string representation of the `EventTask` for saving to Duke Data File.
     */
    @Override
    public String toFile() {
        return super.toFile() + " | " + taskFrom.toString() + " | " + taskTo.toString();
    }
}