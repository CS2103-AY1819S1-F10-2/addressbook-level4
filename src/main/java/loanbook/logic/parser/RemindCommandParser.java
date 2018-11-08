package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_EMAILPW;
import static loanbook.logic.parser.CliSyntax.PREFIX_ID;

import java.util.List;

import loanbook.logic.commands.RemindCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.loan.LoanId;

/**
 * Parses input arguments and creates a new RemindCommand object.
 */
public class RemindCommandParser extends ArgumentParser<RemindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemindCommand
     * and returns an RemindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RemindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
                List.of(PREFIX_EMAILPW, PREFIX_ID),
                List.of(),
                RemindCommand.MESSAGE_USAGE);

        LoanId id = ParserUtil.parseLoanId(argMultimap.getValue(PREFIX_ID).get());
        String password = argMultimap.getValue(PREFIX_EMAILPW).get();

        return new RemindCommand(password, id);
    }
}
