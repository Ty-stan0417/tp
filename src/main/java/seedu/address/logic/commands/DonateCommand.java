package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKLIST;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.BookList;
import seedu.address.model.person.Person;

/**
 * Recorded the book from the specific person and add the merit score to the specific person.
 */
public class DonateCommand extends Command {
    public static final String COMMAND_WORD = "donate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the merit score of the person identified "
            + "by the index number used in the last person listing. "
            + "Merit score will be overwritten to existing score + 1\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BOOKLIST + "[borrow]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BOOKLIST + "The Book of Answers";

    // todo : later need to edit this MESSAGE when the bookTitle recorded to the database.
    public static final String MESSAGE_DONATE_SUCCESS = "Merit score added 1 to Person: %1$s";

    private final Index index;
    private final BookList bookTitle;

    /**
     * @param index     of the person in the filtered person list to edit the
     *                  merit score
     * @param bookTitle of the person donated to be updated to the database
     */
    public DonateCommand(Index index, BookList bookTitle) {
        requireAllNonNull(index, bookTitle);

        this.index = index;
        this.bookTitle = bookTitle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMeritScore().incrementScore(),
                personToEdit.getBookList(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // todo later : add the bookTitle to the storage of library.
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message when book title is successfully removed
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_DONATE_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DonateCommand)) {
            return false;
        }

        DonateCommand e = (DonateCommand) other;
        return index.equals(e.index)
                && bookTitle.equals(e.bookTitle);
    }
}
