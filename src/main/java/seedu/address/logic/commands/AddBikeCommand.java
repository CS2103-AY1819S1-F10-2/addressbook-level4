package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bike.Bike;

/**
 * Adds a bike to the address book.
 */
public class AddBikeCommand extends Command {

    public static final String COMMAND_WORD = "addbike";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bike to the loan book. "
        + "Parameters: "
        + PREFIX_NAME + "NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bike001";

    public static final String MESSAGE_SUCCESS = "New bike added: %1$s";
    public static final String MESSAGE_DUPLICATE_BIKE = "A bike with the same name already exists in the loan book";

    private final Bike toAdd;

    /**
     * Creates an AddBikeCommand to add the specified {@code Bike}
     */
    public AddBikeCommand(Bike bike) {
        requireNonNull(bike);
        toAdd = bike;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasBike(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BIKE);
        }

        model.addBike(toAdd);
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddBikeCommand // instanceof handles nulls
            && toAdd.equals(((AddBikeCommand) other).toAdd));
    }
}
