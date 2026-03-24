package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveCourseCommand;

public class RemoveCourseCommandParserTest {
    private RemoveCourseCommandParser parser = new RemoveCourseCommandParser();

    @Test
    public void parse_validCourseCode_success() {
        String validCourseCode = "CS2103T";
        RemoveCourseCommand expectedCommand = new RemoveCourseCommand(validCourseCode);

        assertParseSuccess(parser, validCourseCode, expectedCommand);
    }

    @Test
    public void parse_courseCodeWithLeadingWhitespace_success() {
        String validCourseCode = "CS2101";
        RemoveCourseCommand expectedCommand = new RemoveCourseCommand(validCourseCode);

        // Leading whitespace should be trimmed
        assertParseSuccess(parser, "   " + validCourseCode, expectedCommand);
    }

    @Test
    public void parse_courseCodeWithTrailingWhitespace_success() {
        String validCourseCode = "CS2100";
        RemoveCourseCommand expectedCommand = new RemoveCourseCommand(validCourseCode);

        // Trailing whitespace should be trimmed
        assertParseSuccess(parser, validCourseCode + "   ", expectedCommand);
    }

    @Test
    public void parse_emptyCourseCode_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_COMMAND_FORMAT + ": " + RemoveCourseCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_whitespaceOnlyInput_failure() {
        assertParseFailure(parser, "   ", MESSAGE_INVALID_COMMAND_FORMAT + ": " + RemoveCourseCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_courseCodeWithSpaces_failure() {
        // Course codes with spaces are not allowed (regex [A-Za-z0-9]{2,10})
        String courseCodeWithSpaces = "CS 2103T";
        assertParseFailure(parser, courseCodeWithSpaces,
                MESSAGE_INVALID_COMMAND_FORMAT + ": " + RemoveCourseCommand.MESSAGE_USAGE);
    }
}
