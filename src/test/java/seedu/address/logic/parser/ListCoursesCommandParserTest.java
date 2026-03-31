package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCoursesCommand;

public class ListCoursesCommandParserTest {
    private ListCoursesCommandParser parser = new ListCoursesCommandParser();

    @Test
    public void parse_noArgs_success() {
        assertParseSuccess(parser, "", new ListCoursesCommand());
    }

    @Test
    public void parse_whitespaceOnlyArgs_success() {
        assertParseSuccess(parser, "   ", new ListCoursesCommand());
    }

    @Test
    public void parse_nonEmptyArgs_throwsParseException() {
        assertParseFailure(parser, "some random text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCoursesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleWords_throwsParseException() {
        assertParseFailure(parser, "extra arguments here",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCoursesCommand.MESSAGE_USAGE));
    }
}
