package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import loanbook.logic.commands.AddBikeCommand;
import loanbook.logic.commands.AddCommand;
import loanbook.logic.commands.CheckEmailCommand;
import loanbook.logic.commands.ClearCommand;
import loanbook.logic.commands.Command;
import loanbook.logic.commands.DeleteCommand;
import loanbook.logic.commands.EditCommand;
import loanbook.logic.commands.ExitCommand;
import loanbook.logic.commands.FindCommand;
import loanbook.logic.commands.HelpCommand;
import loanbook.logic.commands.HistoryCommand;
import loanbook.logic.commands.ListBikesCommand;
import loanbook.logic.commands.ListCommand;
import loanbook.logic.commands.RedoCommand;
import loanbook.logic.commands.RemindCommand;
import loanbook.logic.commands.ReturnCommand;
import loanbook.logic.commands.SelectCommand;
import loanbook.logic.commands.SetEmailCommand;
import loanbook.logic.commands.SetPasswordCommand;
import loanbook.logic.commands.UndoCommand;
import loanbook.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class LoanBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddBikeCommand.COMMAND_WORD:
            return new AddBikeCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case ReturnCommand.COMMAND_WORD:
            return new ReturnCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SetPasswordCommand.COMMAND_WORD:
            return new SetPasswordCommandParser().parse(arguments);

        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);

        case SetEmailCommand.COMMAND_WORD:
            return new SetEmailCommandParser().parse(arguments);

        case ListBikesCommand.COMMAND_WORD:
            return new ListBikesCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case CheckEmailCommand.COMMAND_WORD:
            return new CheckEmailCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
