package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCourseCommand;

public class AddCourseCommandParserTest {
    private AddCourseCommandParser parser = new AddCourseCommandParser();

    @Test
    public void parse_validCourseCode_success() {
        String validCourseCode = "CS2103T";
        AddCourseCommand expectedCommand = new AddCourseCommand(validCourseCode);

        assertParseSuccess(parser, validCourseCode, expectedCommand);
    }

    @Test
    public void parse_courseCodeWithLeadingWhitespace_success() {
        String validCourseCode = "CS2101";
        AddCourseCommand expectedCommand = new AddCourseCommand(validCourseCode);

        assertParseSuccess(parser, "   " + validCourseCode, expectedCommand);
    }

    @Test
    public void parse_multipleCourseCodeValues_failure() {
        String input = "CS2103T CS2101";
        assertParseFailure(parser, input, Messages.MESSAGE_INVALID_COMMAND_FORMAT + AddCourseCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_missingCourseCode_failure() {
        assertParseFailure(parser, "", Messages.MESSAGE_INVALID_COMMAND_FORMAT + AddCourseCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        String invalidCourseCode = "@@@";
        assertParseFailure(parser, invalidCourseCode, Messages.MESSAGE_INVALID_COMMAND_FORMAT
            + AddCourseCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "", Messages.MESSAGE_INVALID_COMMAND_FORMAT + AddCourseCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_courseCodeWithSpaces_failure() {
        // Course codes with spaces are not allowed (regex [A-Za-z0-9]{2,10})
        String courseCodeWithSpaces = "CS 2103T";
        assertParseFailure(parser, courseCodeWithSpaces,
                Messages.MESSAGE_INVALID_COMMAND_FORMAT + AddCourseCommand.MESSAGE_USAGE);
    }
}
