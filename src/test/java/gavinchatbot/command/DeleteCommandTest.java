package gavinchatbot.command;

import gavinchatbot.task.Task;
import gavinchatbot.task.TaskList;
import gavinchatbot.task.ToDos;
import gavinchatbot.task.Deadline;
import gavinchatbot.util.Ui;
import gavinchatbot.util.Storage;
import gavinchatbot.util.GavinException;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCommandTest {

    @Test
    public void execute_deleteFirstTask_success() throws GavinException, IOException {
        // setup
        ArrayList<Task> tasksArray = new ArrayList<>();
        tasksArray.add(new ToDos("task1"));
        TaskList tasks = new TaskList(tasksArray);
        Ui ui = new Ui();
        Storage storage = new Storage("data/gavin.txt");

        // initialise DeleteCommand with a valid task index
        DeleteCommand deleteCommand = new DeleteCommand(1);

        // capture System.out output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // execute command
        deleteCommand.execute(tasks, ui, storage);

        // reset System.out to its original
        System.setOut(originalOut);

        String expectedOutput = "___________________________________________________________________________________\n" +
                "\n" +
                "Noted. I've removed this task:\n" +
                "  [T][ ] task1\n" +
                "Now you have 0 tasks in the list.\n" +
                "___________________________________________________________________________________\n";

        // normalize and verify output
        String actualOutput = outputStream.toString().trim().replaceAll("\\r?\\n", "\n");
        expectedOutput = expectedOutput.trim().replaceAll("\\r?\\n", "\n");

        assertEquals(expectedOutput, actualOutput);

        // ensure list has zero tasks remaining
        assertEquals(0, tasks.size());
    }

    @Test
    public void execute_deleteInvalidTask_failure() throws GavinException, IOException {
        // setup
        ArrayList<Task> tasksArray = new ArrayList<>();
        tasksArray.add(new ToDos("task1")); // only one task in the list, index 0 (1st task)
        TaskList tasks = new TaskList(tasksArray);
        Ui ui = new Ui();
        Storage storage = new Storage("data/gavin.txt");

        // trying to delete 2nd task which doesn't exist
        DeleteCommand deleteCommand = new DeleteCommand(2);

        // capture System.out output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // execute command
        deleteCommand.execute(tasks, ui, storage);

        // reset System.out to its original
        System.setOut(originalOut);

        String expectedOutput = "___________________________________________________________________________________\n" +
                "\n" +
                "!!!ERROR!!! OOPS!!! The task number provided does not exist.\n" +
                "___________________________________________________________________________________\n";
        String actualOutput = outputStream.toString().trim().replaceAll("\\r?\\n", "\n");
        assertTrue(actualOutput.contains("OOPS!!! The task number provided does not exist."));

        // ensure the list still has one task
        assertEquals(1, tasks.size());
    }
}
