package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListDetailsCommand;
import seedu.address.logic.commands.ViewAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listDetails() throws Exception {
        assertTrue(parser.parseCommand(ListDetailsCommand.COMMAND_WORD + " c/CS2103T")
                instanceof ListDetailsCommand);
        assertTrue(parser.parseCommand(ListDetailsCommand.COMMAND_WORD + " c/CS2103T,CS2101")
                instanceof ListDetailsCommand);
    }

    @Test
    public void parseCommand_viewAll() throws Exception {
        assertTrue(parser.parseCommand(ViewAllCommand.COMMAND_WORD) instanceof ViewAllCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, "Unknown command", () -> parser.parseCommand("unknownCommand"));
    }
}
