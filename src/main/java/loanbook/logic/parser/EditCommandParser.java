package loanbook.logic.parser;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_NOT_EDITED;
import static loanbook.logic.parser.CliSyntax.PREFIX_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static loanbook.logic.parser.CliSyntax.PREFIX_LOANRATE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_NRIC;
import static loanbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static loanbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import loanbook.commons.core.index.Index;
import loanbook.logic.commands.EditCommand;
import loanbook.logic.commands.EditCommand.EditLoanDescriptor;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_BIKE, PREFIX_LOANRATE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditLoanDescriptor editLoanDescriptor = new EditLoanDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editLoanDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editLoanDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editLoanDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editLoanDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_BIKE).isPresent()) {
            editLoanDescriptor.setBike(ParserUtil.parseBike(argMultimap.getValue(PREFIX_BIKE).get()));
        }
        if (argMultimap.getValue(PREFIX_LOANRATE).isPresent()) {
            editLoanDescriptor.setLoanRate(ParserUtil.parseLoanRate(argMultimap.getValue(PREFIX_LOANRATE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editLoanDescriptor::setTags);

        if (!editLoanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editLoanDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
