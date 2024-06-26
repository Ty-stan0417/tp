package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.library.Threshold;

/**
 * Sets the limit threshold for Merit Score to not allow borrowers from borrowing books from the library.
 */
public class LimitCommand extends Command {
    public static final String COMMAND_WORD = "limit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the limit for the Merit Score such that any "
            + "borrower with less than or equal to the limit cannot borrow any books from the library.\n"
            + "Parameters: INTEGER (cannot include spaces or + signs)\n"
            + "Example: " + COMMAND_WORD + " -3";

    public static final String MESSAGE_LIMIT_THRESHOLD_SUCCESS = "Limit set to: %s";
    public static final String MESSAGE_HAS_NO_ARGUMENT = "Current limit: %s\n"
            + "To change the current limit, use parameters with limit.\n"
            + "Parameters: INTEGER (cannot include spaces or + signs)\n"
            + "Example: " + COMMAND_WORD + " -3";;
    public static final String MESSAGE_DUPLICATE_LIMIT = "Library already has the same limit set";
    public final Threshold threshold;
    private boolean hasNoArgument = false;

    /**
     * @param threshold Limit for the Merit Score during borrowing.
     */
    public LimitCommand(Threshold threshold) {
        requireNonNull(threshold);

        this.threshold = threshold;
    }

    /**
     * @param hasNoArgument Limit for the Merit Score during borrowing.
     */
    public LimitCommand(boolean hasNoArgument) {
        assert(hasNoArgument);

        this.threshold = new Threshold();
        this.hasNoArgument = hasNoArgument;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (hasNoArgument) {
            return new CommandResult(String.format(MESSAGE_HAS_NO_ARGUMENT, model.getThreshold()));
        }

        if (model.hasThreshold(threshold)) {
            throw new CommandException(MESSAGE_DUPLICATE_LIMIT);
        }
        model.setThreshold(threshold);
        return new CommandResult(String.format(MESSAGE_LIMIT_THRESHOLD_SUCCESS, threshold));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LimitCommand)) {
            return false;
        }

        LimitCommand otherLimitCommand = (LimitCommand) other;
        return threshold.equals(otherLimitCommand.threshold);
    }
}
