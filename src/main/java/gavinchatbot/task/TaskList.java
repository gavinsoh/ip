package gavinchatbot.task;

import gavinchatbot.util.GavinException;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) throws GavinException {
        if (index < 0 || index >= tasks.size()) {
            throw new GavinException("gavinchatbot.task.Task number is invalid!!!");
        }
        return tasks.get(index);
    }

    public void markTask(int index) throws GavinException {
        getTask(index).markAsDone();
    }

    public void unmarkTask(int index) throws GavinException {
        getTask(index).markAsNotDone();
    }

    public Task deleteTask(int index) throws GavinException {
        if (index < 0 || index >= tasks.size()) {
            throw new GavinException("OOPS!!! The task number provided does not exist.");
        }
        return tasks.remove(index);
    }


    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

}
