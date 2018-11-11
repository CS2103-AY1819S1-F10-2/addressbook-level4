package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.util.CollectionUtil.requireAllNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_EMAILPW;
import static loanbook.logic.parser.CliSyntax.PREFIX_ID;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.SendReminder;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanStatus;

/**
 * Send a reminder email to the customer.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Send a reminder email to the customer.\n"
            + "Parameter: "
            + PREFIX_EMAILPW + "EMAILPASSWORD "
            + PREFIX_ID + "ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAILPW + "samplepassword "
            + PREFIX_ID + "0 ";

    public static final String MESSAGE_SUCCESS = "Email sent!";

    private final LoanId id;
    private final String emailPassword;

    /**
     * Creates an RemindCommand to send a reminder email to customer's {@code Email}
     * according to the {@code Name} and {@code BikeId} provided.
     */
    public RemindCommand(String emailPassword, LoanId id) {
        requireAllNonNull(emailPassword, id);
        this.id = id;
        this.emailPassword = emailPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Optional<Loan> targetLoan = model.getLoanById(id);

        if (!targetLoan.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN);
        }

        if (targetLoan.get().getLoanStatus().equals(LoanStatus.RETURNED)) {
            throw new CommandException(String.format(Messages.MESSAGE_LOAN_IS_DONE, LoanStatus.RETURNED.toString()));
        }

        SendReminder sendReminder = new SendReminder(model, emailPassword, targetLoan.get());

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindCommand // instanceof handles nulls
                && emailPassword.equals(((RemindCommand) other).emailPassword)
                && id.equals(((RemindCommand) other).id)); // state check
    }
}
