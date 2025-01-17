package duke.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.task.Task;

/**
 * Tests the functionality of the FileRead class.
 */
public class FileReadTest {

    private FileRead fileRead;
    private List<Task> taskList;
    private File testFile;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setup() {

        fileRead = new FileRead();
        taskList = new ArrayList<>();
        testFile = new File("./data/duke.txt");
    }

    /**
     * Test loading saved tasks from a file.
     *
     * @throws IOException if there is an issue with file operations.
     */
    @Test
    public void testGetSavedTask() throws IOException {
        // Create a temporary file with sample task data
        FileWriter writer = new FileWriter(testFile);
        writer.write("T | 0 | TestTodo\n");
        writer.write("D | 0 | TestDeadline | 2023-10-31 12:00\n");
        writer.write("E | 1 | TestEvent | 2023-10-31 12:00 | 2023-10-31 14:00\n");
        writer.close();

        fileRead.getSavedTask(taskList);

        // Verify the loaded tasks
        assertEquals("TestTodo", taskList.get(0).getTaskName());
        assertFalse(taskList.get(0).isCompleted());
        assertEquals("TestDeadline", taskList.get(1).getTaskName());
        assertFalse(taskList.get(1).isCompleted());
        assertEquals("TestEvent", taskList.get(2).getTaskName());
        assertTrue(taskList.get(2).isCompleted());
    }

    /**
     * Test loading saved tasks when the file is not found.
     */
    @Test
    public void testGetSavedTaskFileNotFound() {

        // Attempt to load saved tasks from a non-existing file
        fileRead.getSavedTask(taskList);

        // Verify that no tasks were loaded
        assertEquals(0, taskList.size());
    }

    /**
     * Test loading saved tasks when the file contains corrupted data.
     *
     * @throws IOException if there is an issue with file operations.
     */
    @Test
    public void testGetSavedTaskFileCorrupted() throws IOException {
        // Create a temporary file with corrupted task data
        FileWriter writer = new FileWriter(testFile);
        writer.write("T | 0 | Test Todo 1 | AdditionalData\n");
        writer.write("D | Test Deadline 1 | false\n");
        writer.write("E | Test Event 1 | true | 2023-10-31T12:00 | InvalidDate\n");
        writer.close();

        fileRead.getSavedTask(taskList);

        // Verify that no tasks were loaded due to data corruption
        assertEquals(0, taskList.size());
    }

}
