package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gamer.Gamer;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the gamer contacts identified by the index number used in the displayed gamer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Contact deleted: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Gamer> lastShownList = model.getFilteredPersonList();

        validateDeleteIndex(lastShownList);

        int index = targetIndex.getZeroBased();
        assert index < lastShownList.size();

        Gamer gamerToDelete = lastShownList.get(index);
        model.deletePerson(gamerToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(gamerToDelete)));
    }

    /**
     * Validates whether the target index refers to a valid gamer in the given list.
     *
     * @param gamerList The currently displayed list of gamers.
     * @throws CommandException If the list is empty or if the index is out of range.
     */
    private void validateDeleteIndex(List<Gamer> gamerList) throws CommandException {
        if (gamerList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CONTACT_LIST);
        }

        int index = targetIndex.getZeroBased();
        if (index >= gamerList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
