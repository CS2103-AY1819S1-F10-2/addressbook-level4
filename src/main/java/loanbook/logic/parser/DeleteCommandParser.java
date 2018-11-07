package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_INDEX;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import loanbook.commons.core.index.Index;
import loanbook.logic.commands.DeleteCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.Password;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser extends ArgumentParser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
                List.of(PREFIX_INDEX, PREFIX_PASSWORD),
                List.of(),
                DeleteCommand.MESSAGE_USAGE);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Password password = ParserUtil.parsePass(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new DeleteCommand(index, password);
    }

}
