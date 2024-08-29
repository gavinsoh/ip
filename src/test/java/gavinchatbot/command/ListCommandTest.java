package gavinchatbot.command;

import gavinchatbot.task.Deadline;
import gavinchatbot.task.Task;
import gavinchatbot.task.TaskList;
import gavinchatbot.task.ToDos;
import gavinchatbot.util.GavinException;
import gavinchatbot.util.Storage;
import gavinchatbot.util.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class ListCommandTest {

    @Test
    public void execute_emptyTaskList_success() throws GavinException {

        TaskList tasks = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        Storage storage = new Storage("data/gavin.txt");
        ListCommand listCommand = new ListCommand();

        // capture System.out output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // execute command
        listCommand.execute(tasks, ui, storage);

        // reset System.out to original
        System.setOut(originalOut);

        String expectedOutput = "___________________________________________________________________________________\n" +
                "\n" +
                "Here are the tasks in your list:\n" +
                "___________________________________________________________________________________\n" +
                "\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_nonEmptyTaskList_success() throws GavinException {
        ArrayList<Task> tasksArray = new ArrayList<>();
        tasksArray.add(new ToDos("task1"));
        tasksArray.add(new Deadline("task2", "2024-08-29"));
        TaskList tasks = new TaskList(tasksArray);
        Ui ui = new Ui();
        Storage storage = new Storage("data/gavin.txt");
        ListCommand listCommand = new ListCommand();

        // capture System.out output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // execute command
        listCommand.execute(tasks, ui, storage);

        // reset System.out to original
        System.setOut(originalOut);

        // verify output
        String expectedOutput = "___________________________________________________________________________________\n" +
                "\n" +
                "Here are the tasks in your list:\n" +
                "1. [T][ ] task1\n" +
                "2. [D][ ] task2 (by: Aug 29 2024)\n" +
                "___________________________________________________________________________________\n" +
                "\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
