package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.util.CollectionUtil.requireAllNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.SendReminder;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.Name;

/**
 * Send a reminder email to the customer.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Send a reminder email to the customer.\n"
            + "Parameter: "
            + PREFIX_PASSWORD + "EMAILPASSWORD "
            + PREFIX_NAME + "NAME "
            + PREFIX_BIKE + "BIKE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "samplepassword "
            + PREFIX_NAME + "John Doe "
            + PREFIX_BIKE + "BIKE001";

    public static final String MESSAGE_SUCCESS = "Email sent!";

    private final Name name;
    private final Bike bike;
    private final String emailPassword;

    /**
     * Creates an RemindCommand to send a reminder email to customer's {@code Email}
     * according to the {@code Name} and {@code BikeId} provided.
     */
    public RemindCommand(String emailPassword, Name name, Bike bike) {
        requireAllNonNull(emailPassword, name, bike);
        this.name = name;
        this.bike = bike;
        this.emailPassword = emailPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Loan> lastShownList = model.getLoanBook().getLoanList();

        Loan targetLoan = getLoan(lastShownList, name, bike);

        if (targetLoan == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INFO, name, bike.getName()));
        }

        if (targetLoan.getLoanStatus().equals(LoanStatus.RETURNED)) {
            throw new CommandException(String.format(Messages.MESSAGE_LOAN_IS_DONE, LoanStatus.RETURNED.toString()));
        } else if (targetLoan.getLoanStatus().equals(LoanStatus.DELETED)) {
            throw new CommandException(String.format(Messages.MESSAGE_LOAN_IS_DONE, LoanStatus.DELETED.toString()));
        }

        SendReminder sendReminder = new SendReminder(model, emailPassword, targetLoan);

        try {
            sendReminder.send();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (AuthenticationFailedException e) {
            throw new CommandException(Messages.MESSAGE_AUTHEN_FAILURE);
        } catch (MessagingException e) {
            throw new CommandException(Messages.MESSAGE_NO_NETWORK_CONNECTION);
        } catch (UnsupportedEncodingException e) {
            throw new CommandException(Messages.MESSAGE_BAD_RUNTIME);
        }
    }

    /**
     * Returns the {@code Loan} Object with both the target name and bike id.
     */
    private Loan getLoan(List<Loan> loanList, Name name, Bike bike) {
        for (Loan loan: loanList) {
            if (loan.getName().equals(name) && loan.getBike().equals(bike)) {
                return loan;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindCommand // instanceof handles nulls
                && emailPassword.equals(((RemindCommand) other).emailPassword)
                && name.equals(((RemindCommand) other).name)
                && bike.equals(((RemindCommand) other).bike)); // state check
    }
}
