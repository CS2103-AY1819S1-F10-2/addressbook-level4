package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOANRATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOANTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * Adds a loan to the loan book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a loan to the loan book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_BIKE + "BIKE "
            + PREFIX_LOANRATE + "LOANRATE "
            + PREFIX_LOANTIME + "LOANTIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "T0248272F "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_BIKE + "Bike001 "
            + PREFIX_LOANRATE + "3.5 "
            + PREFIX_LOANTIME + "2018-10-01 12:00 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New loan added: %1$s";
    public static final String MESSAGE_DUPLICATE_LOAN = "This loan already exists in the loan book";
    public static final String MESSAGE_BIKE_NOT_FOUND = "No bike with that name exists within the loan book";

    private final Loan toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Loan}
     */
    public AddCommand(Loan loan) {
        requireNonNull(loan);
        toAdd = loan;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasLoan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }

        Optional<Bike> actualBike = model.getBike(toAdd.getBike().getName().value);
        if (!actualBike.isPresent()) {
            throw new CommandException(MESSAGE_BIKE_NOT_FOUND);
        }
        Loan actualLoan = new Loan(toAdd, actualBike.get());

        model.addLoan(actualLoan);
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, actualLoan));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
