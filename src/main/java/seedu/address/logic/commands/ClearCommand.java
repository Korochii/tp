package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Requests for confirmation to clear the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    //public static final String MESSAGE_FAILED_CONFIRMATION = "Clear command not confirmed. TAPA will not be cleared.";
    public static final String MESSAGE_REQUEST_CONFIRMATION = "Please enter \"confirm\" to clear all students from TAPA.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_REQUEST_CONFIRMATION);
    }
}
