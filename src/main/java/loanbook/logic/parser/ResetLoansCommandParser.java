package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import loanbook.logic.commands.ResetLoansCommand;
import loanbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class ResetLoansCommandParser extends ArgumentParser<ResetLoansCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResetAllCommand
     * and returns a ResetAllCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ResetLoansCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
                List.of(PREFIX_PASSWORD),
                List.of(),
                ResetLoansCommand.MESSAGE_USAGE);

        String password = ParserUtil.parsePass(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new ResetLoansCommand(password);
    }

}
