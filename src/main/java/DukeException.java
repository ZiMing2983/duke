class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}

class EmptyCommandException extends DukeException {
    public EmptyCommandException() {
        super("You did not provide any command. Please try again.");
    }
}

class InvalidCommandException extends DukeException {
    public InvalidCommandException() {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}

class InvalidTaskException extends DukeException {
    public InvalidTaskException() {
        super("OOPS!!! I'm sorry, but I can't find this task!");
    }
}

class EmptyArgumentException extends DukeException {
    public EmptyArgumentException() {
        super("You did not provide any details on your todo. Please provide in the format of <event + Task Name>.");
    }
}

class TaskCreationException extends DukeException {
    public TaskCreationException(String message) {
        super("Error creating task: " + message);
    }
}

class InvalidNumberFormatException extends DukeException {
    public InvalidNumberFormatException(String message) {
        super(message + " is not a valid number! Please Try again.");
    }

    public InvalidNumberFormatException() {
        super("Invalid Number! Please Try again.");
    }
}

class InvalidTaskFormatException extends DukeException {
    public InvalidTaskFormatException(String taskType) throws DukeException {
        super(getErrorMessage(taskType));
    }

    private static String getErrorMessage(String taskType) throws DukeException {
        switch (taskType) {
            case "deadline":
                return "Invalid format for Deadline task. Please provide in the format of <deadline + Task Name + /by + by time>";
            case "event":
                return "Invalid format for Event task. Please provide in the format of <event + Task Name + /from + From time + /to + To time>";
            default:
                throw new DukeException("Unknown task type: " + taskType);
        }
    }
}