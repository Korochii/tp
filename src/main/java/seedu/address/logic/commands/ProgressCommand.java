package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Task;

/**
 * Displays the completion status of all students that are assigned to a particular task.
 */
public class ProgressCommand extends Command {

    public static final String COMMAND_WORD = "progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the completion status of all students that are assigned to a particular task. "
            + "Parameters: "
            + PREFIX_TASK_NAME + "TASK_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "Assignment 1";

    public static final String MESSAGE_SUCCESS = "%1$s has been assigned to the following students:\n\n" + "%2$s";
    public static final String MESSAGE_NO_RESULTS_FOUND = "This task is not assigned to any student!\n"
            + "Perhaps you might want to assign this task to a student, using the 'assign' command?";

    private final Task task;

    /**
     * Constructs a new ProgressCommand.
     * @param task the task that the user wants to know more about the progress.
     */
    public ProgressCommand(Task task) {
        requireNonNull(task);
        this.task = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert task != null; // task should not be null, as it is specified by the user.

        LinkedHashMap<Person, Boolean> possiblePairings = model.checkProgress(task);
        if (possiblePairings != null && possiblePairings.isEmpty()) {
            throw new CommandException(MESSAGE_NO_RESULTS_FOUND);
        }

        StringBuilder result = new StringBuilder();
        assert possiblePairings != null;
        for (Map.Entry<Person, Boolean> entry : possiblePairings.entrySet()) {
            Person currPerson = entry.getKey();
            Boolean isCompleted = entry.getValue();
            result.append(currPerson.getName()).append(" (").append(currPerson.getStudentId()).append("): ");
            if (isCompleted) { // This student has already completed the task.
                String tick = "\u2713"; // unicode representation of a check symbol
                result.append(tick).append("\n");
            } else {
                String cross = "\u274C"; // unicode representation of a cross symbol
                result.append(cross).append("\n");
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, task.getTaskName(), result));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProgressCommand // instanceof handles nulls
                && task.equals(((ProgressCommand) other).task)); // state check
    }

}
