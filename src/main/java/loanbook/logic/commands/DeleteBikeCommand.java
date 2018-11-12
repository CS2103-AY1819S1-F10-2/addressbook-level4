package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.core.Messages.MESSAGE_BIKE_NOT_FOUND;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.Optional;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * Deletes a bike identified using it's displayed index from the loan book.
 */
public class DeleteBikeCommand extends PasswordProtectedCommand {

    public static final String COMMAND_WORD = "deletebike";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the bike with the given name.\n"
        + "Requires a password for verification.\n"
        + "Parameters: " + PREFIX_NAME + "NAME " + PREFIX_PASSWORD + "PASSWORD\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Bike001 " + PREFIX_PASSWORD + "a12345";

    public static final String MESSAGE_DELETE_BIKE_SUCCESS = "Deleted Bike: %1$s";

    private final Name bikeName;
    private final String targetPassword;

    public DeleteBikeCommand(Name bikeName, String pass) {
        super(pass, COMMAND_WORD);
        this.bikeName = bikeName;
        targetPassword = pass;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assertCorrectPassword(model);

        Optional<Bike> actualBike = model.getBike(bikeName.value);
        if (!actualBike.isPresent()) {
            throw new CommandException(MESSAGE_BIKE_NOT_FOUND);
        }

        model.deleteBike(actualBike.get());
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_DELETE_BIKE_SUCCESS, actualBike.get()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteBikeCommand)) {
            return false;
        }

        // state check
        DeleteBikeCommand otherCommand = (DeleteBikeCommand) other;
        return bikeName.equals(otherCommand.bikeName)
            && targetPassword.equals(otherCommand.targetPassword);
    }
}
