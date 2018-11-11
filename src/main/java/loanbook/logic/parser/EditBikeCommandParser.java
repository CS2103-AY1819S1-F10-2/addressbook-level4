package loanbook.logic.parser;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_NOT_EDITED;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;

import loanbook.logic.commands.EditBikeCommand;
import loanbook.logic.commands.EditBikeCommand.EditBikeDescriptor;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.loan.Name;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditBikeCommandParser implements Parser<EditBikeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBikeCommand
     * and returns an EditBikeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBikeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Name bikeName;

        try {
            bikeName = ParserUtil.parseName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBikeCommand.MESSAGE_USAGE), pe);
        }

        EditBikeDescriptor editBikeDescriptor = new EditBikeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editBikeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editBikeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditBikeCommand(bikeName, editBikeDescriptor);
    }

}
