package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "%d cat(s) cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int count = model.getFilteredCatList().size();
        model.setAddressBook(new AddressBook());
        return new CommandResult(String.format(MESSAGE_SUCCESS, count));
    }
}
