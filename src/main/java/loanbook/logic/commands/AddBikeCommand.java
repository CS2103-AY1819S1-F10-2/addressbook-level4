package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.core.Messages.MESSAGE_DUPLICATE_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.bike.Bike;

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
