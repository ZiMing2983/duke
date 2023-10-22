package Duke.UserInterface;

import Duke.Task.Task;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    public UserInterface.UserInput userInput;
    public UserInterface.MessageDisplay messageDisplay;

    public UserInterface() {
        userInput = new UserInterface.UserInput();
        messageDisplay = new UserInterface.MessageDisplay();
    }

    public static class UserInput {
        private final Scanner scanner;

        public UserInput() {
            scanner = new Scanner(System.in);
        }

        public String getUserInput() {
            return scanner.nextLine().trim().toLowerCase();
        }

        public void closeScanner() {
            scanner.close();
        }
    }

    public static class MessageDisplay {
        public static final String LINE_BREAK = "****************************************";

        private static final String LOGO =
                "╭━━━╮╱╱╱╱╱╭╮\n" +
                        "┃╭━╮┃╱╱╱╱╱┃┃\n" +
                        "┃╰━━┳━━┳━━┫╰━┳━━┳━╮\n" +
                        "╰━━╮┃╭╮┃╭╮┃╭╮┃╭╮┃╭╮╮\n" +
                        "┃╰━╯┃╰╯┃╰╯┃┃┃┃╰╯┃┃┃┃\n" +
                        "╰━━━┻━━┫╭━┻╯╰┻━━┻╯╰╯\n" +
                        "╱╱╱╱╱╱╱┃┃\n" +
                        "╱╱╱╱╱╱╱╰╯\n";

        public static void print(String line) {
            System.out.printf("%s\n%s\n", line, LINE_BREAK);
        }

        /**
         * Displays a message when a task is added.
         */
        public static void addedMessage(List<Task> userInputList, int itemNumber) {
            int totalTasks = Task.getTotalTasks();
            System.out.printf("Got it. I've added this task:\n   %s\nNow you have %d tasks in the list.\n%s%n", userInputList.get(itemNumber).toString(), totalTasks, LINE_BREAK);
        }

        /**
         * Displays a message when attempting to mark a task that is already marked as completed.
         *
         * @param taskName The name of the task.
         */
        public static void alreadyMark(String taskName) {
            print(taskName + " is already marked!");
        }

        /**
         * Displays a message when attempting to unmark a task that is not marked as completed.
         *
         * @param taskName The name of the task.
         */
        public static void notMark(String taskName) {
            print("You did not complete " + taskName + " before!");
        }

        /**
         * Displays a message when a task is marked as completed.
         *
         * @param userInputList The array of user tasks.
         * @param itemNumber    The index of the completed task.
         */
        public static void completeMessage(List<Task> userInputList, int itemNumber) {
            System.out.printf("That's some progress! I've marked this task as done:\n   %s\n%s\n", userInputList.get(itemNumber).toString(), LINE_BREAK);
        }

        /**
         * Displays a message when a task is unmarked.
         *
         * @param userInputList The array of user tasks.
         * @param itemNumber    The index of the unmarked task.
         */
        public static void unCompleteMessage(List<Task> userInputList, int itemNumber) {
            System.out.printf("Okay, you can do it at a later time:\n   %s\n%s\n", userInputList.get(itemNumber).toString(), LINE_BREAK);
        }

        /**
         * Displays a message when a task is deleted.
         *
         * @param task The deleted tasks.
         */
        public static void deleteMessage(Task task) {
            System.out.printf("Noted. I've removed this task:\n   %s\nNow you have %s tasks in the list.\n%s\n", task.toString(), Task.getTotalTasks(), LINE_BREAK);
        }

        /**
         * Displays a welcome message with the Duke LOGO.
         */
        public void Hello() {
            print(LOGO + "Hello, I'm Sophon:). \nHow can I assist you today?");
        }

        /**
         * Displays a goodbye message.
         */
        public void Goodbye() {
            print("Bye! Hope to see you again soon!");
        }
    }
}
