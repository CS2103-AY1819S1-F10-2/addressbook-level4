package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static loanbook.logic.commands.CommandTestUtil.BIKE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.DEFAULT_USER_EMAIL;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD2_DESC;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL1;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.logic.commands.AddBikeCommand;
import loanbook.logic.commands.AddCommand;
import loanbook.logic.commands.CheckEmailCommand;
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
import loanbook.logic.commands.ResetLoansCommand;
import loanbook.logic.commands.ReturnCommand;
import loanbook.logic.commands.SearchCommand;
import loanbook.logic.commands.SelectCommand;
import loanbook.logic.commands.SetEmailCommand;
import loanbook.logic.commands.SetPasswordCommand;
import loanbook.logic.commands.UndoCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.Password;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanTime;
import loanbook.model.loan.Name;
import loanbook.model.loan.NameContainsKeywordsPredicate;
import loanbook.testutil.BikeBuilder;
import loanbook.testutil.BikeUtil;
import loanbook.testutil.EditLoanDescriptorBuilder;
import loanbook.testutil.LoanBuilder;
import loanbook.testutil.LoanUtil;

public class LoanBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LoanBookParser parser = new LoanBookParser();

    @Test
    public void parseCommand_addbike() throws Exception {
        Bike bike = new BikeBuilder().build();
        AddBikeCommand command = (AddBikeCommand) parser.parseCommand(BikeUtil.getAddBikeCommand(bike));
        assertEquals(new AddBikeCommand(bike), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Loan loan = new LoanBuilder()
                .withLoanId(AddCommandParser.PLACEHOLDER_LOAN_ID.toString())
                .build();
        AddCommand command = (AddCommand) parser.parseCommand(LoanUtil.getAddCommand(loan));
        assertEquals(new AddCommand(loan), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        String samplePasswordUserInput = "a12345";
        Password samplePassword = new Password(samplePasswordUserInput);

        ResetLoansCommand expectedCommand = new ResetLoansCommand(samplePassword);

        assertEquals(expectedCommand,
                parser.parseCommand(ResetLoansCommand.COMMAND_WORD + " " + PREFIX_PASSWORD + samplePasswordUserInput));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " i/" + INDEX_FIRST_LOAN.getOneBased() + " x/" + "a12345");
        Password pass = new Password("a12345");
        assertEquals(new DeleteCommand(INDEX_FIRST_LOAN, pass), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Loan loan = new LoanBuilder().build();
        EditCommand.EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder(loan).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_LOAN.getOneBased() + " " + LoanUtil.getEditLoanDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_LOAN, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_setemail() throws Exception {
        SetEmailCommand command = (SetEmailCommand) parser.parseCommand(
                SetEmailCommand.COMMAND_WORD + " default " + "abcdefg@gmail.com");
        assertEquals(new SetEmailCommand(DEFAULT_USER_EMAIL, VALID_USER_EMAIL1), command);
    }

    @Test
    public void parseCommand_remind() throws Exception {
        RemindCommand command = (RemindCommand) parser.parseCommand(
                RemindCommand.COMMAND_WORD + PASSWORD2_DESC + NAME_DESC_AMY + BIKE_DESC_AMY);
        Name name = new Name(VALID_NAME_AMY);
        Bike bike = new Bike(new Name(VALID_NAME_BIKE1));
        assertEquals(new RemindCommand("loanbookpassword", name, bike), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_checkemail() throws Exception {
        assertTrue(parser.parseCommand(CheckEmailCommand.COMMAND_WORD) instanceof CheckEmailCommand);
        assertTrue(parser.parseCommand(CheckEmailCommand.COMMAND_WORD + " 3") instanceof CheckEmailCommand);
    }

    @Test
    public void parseCommand_listbikes() throws Exception {
        assertTrue(parser.parseCommand(ListBikesCommand.COMMAND_WORD) instanceof ListBikesCommand);
        assertTrue(parser.parseCommand(ListBikesCommand.COMMAND_WORD + " 3") instanceof ListBikesCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_LOAN), command);
    }

    @Test
    public void parseCommand_return() throws Exception {
        ReturnCommand command = (ReturnCommand) parser.parseCommand(
                ReturnCommand.COMMAND_WORD + " i/" + INDEX_FIRST_LOAN.getOneBased());
        assertEquals(new ReturnCommand(INDEX_FIRST_LOAN), command);
    }

    @Test
    public void parseCommand_setpass() throws Exception {
        String oldPass = "oldPass";
        String newPass = "newPass";
        SetPasswordCommand command = (SetPasswordCommand) parser.parseCommand(
                SetPasswordCommand.COMMAND_WORD + " " + oldPass + " " + newPass);
        assertEquals(new SetPasswordCommand(new Password(oldPass), new Password(newPass)), command);
    }

    @Test
    public void parseCommand_search() throws Exception {
        String oldDate = "2018-01-01";
        String newDate = "2018-01-02";

        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " " + oldDate + " " + newDate);
        assertEquals(new SearchCommand(LoanTime.startOfDayLoanTime(oldDate), LoanTime.endOfDayLoanTime(newDate)),
                command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
