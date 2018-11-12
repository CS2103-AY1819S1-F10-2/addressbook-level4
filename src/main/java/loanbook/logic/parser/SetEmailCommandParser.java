package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import loanbook.logic.commands.SetEmailCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.loan.Email;

/**
 * Parses input arguments and creates a new SetEmailCommand object.
 */
public class SetEmailCommandParser extends ArgumentParser<SetEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetEmailCommand
     * and returns an SetEmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetEmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
                List.of(PREFIX_EMAIL, PREFIX_PASSWORD),
                List.of(),
                SetEmailCommand.MESSAGE_USAGE);

        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        String password = ParserUtil.parsePass(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new SetEmailCommand(email, password);
    }
}
