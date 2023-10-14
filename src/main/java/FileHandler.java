import java.io.*;
import java.util.List;

abstract class FileHandler {
    String filePath = "./data/duke.txt";
}

class FileStorage extends FileHandler {
    public void fileStorage(List<Task> taskList) {

        File outputFile = new File(filePath);

        try {
            // Check if the file exists, and create it if it doesn't
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }

            // Open the file for writing
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile, false);
                 PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
                // Append output to the file
                for (Task task : taskList) {
                    printWriter.println(task.toFile());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class FileRead extends FileHandler {

    public void getSavedTask(List<Task> taskList) {
        try {
            // Create a FileReader to open the file
            FileReader fileReader = new FileReader(filePath);

            // Create a BufferedReader to read the file efficiently
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String inputs;
            // Read each line from the file until the end is reached
            while ((inputs = bufferedReader.readLine()) != null) {
                String[] input = inputs.replaceAll(" ", "").split("\\|");
                if(input.length <= 2 || input.length > 5){
                    throw new FileCorruptedException();
                }
                String taskType = input[0];
                boolean isCompleted;
                try{
                    int temp = Integer.parseInt(input[1]);
                    isCompleted = temp == 1;
                }catch(NumberFormatException e){
                    throw new FileCorruptedException();
                }

                String taskName = input[2];
                Task task;
                switch (taskType) {
                    case "T":
                        if(input.length != 3){
                            throw new FileCorruptedException();
                        }
                        task = new TodoTask(taskName,isCompleted);
                        break;
                    case "D":
                        if(input.length != 4){
                            throw new FileCorruptedException();
                        }
                        task = new DeadlineTask(taskName,isCompleted,input[3]);
                        break;
                    case "E":
                        if(input.length != 5){
                            throw new FileCorruptedException();
                        }
                        task = new EventTask(taskName,isCompleted,input[3],input[4]);
                        break;
                    default:
                        throw new FileCorruptedException();
                }
                taskList.add(task);

            }
            // Close the BufferedReader and FileReader to release system resources
            bufferedReader.close();
            fileReader.close();
        }catch (FileNotFoundException e){
            System.out.println("No saved tasks found, proceed to start.");
        }catch (FileCorruptedException e) {
            System.out.printf("%s\n%s\n", e.getMessage(), UserInterface.MessageDisplay.LINE_BREAK);
        }catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions that may occur
        }
    }
}

abstract class FileException extends Exception {
    public FileException(String message) {
        super(message);
    }
}

class FileCorruptedException extends FileException {
    public FileCorruptedException() {
        super("File is corrupted, proceed to start a new session without data loading.");
    }
}