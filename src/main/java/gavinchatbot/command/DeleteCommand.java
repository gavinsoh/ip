package gavinchatbot.command;

import java.io.IOException;
import gavinchatbot.task.Task;
import gavinchatbot.task.TaskList;
import gavinchatbot.util.Ui;
import gavinchatbot.util.Storage;
import gavinchatbot.util.GavinException;

public class DeleteCommand implements Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GavinException, IOException {
        try {
            Task taskToDelete = tasks.deleteTask(index);
            ui.showDeletedTask(taskToDelete, tasks.size());
        } catch (GavinException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
