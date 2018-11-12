package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import loanbook.logic.commands.DeleteBikeCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.loan.Name;

/**
 * Parses input arguments and creates a new DeleteBikeCommand object.
 */
public class DeleteBikeCommandParser extends ArgumentParser<DeleteBikeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteBikeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
            List.of(PREFIX_NAME, PREFIX_PASSWORD),
            List.of(),
            DeleteBikeCommand.MESSAGE_USAGE);

        Name bikeName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String password = ParserUtil.parsePass(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new DeleteBikeCommand(bikeName, password);
    }

}
