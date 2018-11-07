package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import loanbook.logic.commands.ResetAllCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.Password;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class ResetAllCommandParser extends ArgumentParser<ResetAllCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResetAllCommand
     * and returns a ResetAllCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ResetAllCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
                List.of(PREFIX_PASSWORD),
                List.of(),
                ResetAllCommand.MESSAGE_USAGE);

        Password password = ParserUtil.parsePass(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new ResetAllCommand(password);
    }

}
