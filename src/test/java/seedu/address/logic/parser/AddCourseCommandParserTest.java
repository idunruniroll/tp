package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
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

        assertParseSuccess(parser, " " + PREFIX_COURSE_CODE + validCourseCode, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyPreamble_success() {
        String validCourseCode = "CS2101";
        AddCourseCommand expectedCommand = new AddCourseCommand(validCourseCode);

        // whitespace before prefix should be accepted
        assertParseSuccess(parser, "   " + PREFIX_COURSE_CODE + validCourseCode, expectedCommand);
    }

    @Test
    public void parse_multipleCourseCodeValues_failure() {
        // Multiple course code values should fail (only one expected)
        String input = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_COURSE_CODE + "CS2101";
        assertParseFailure(parser, input, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }

    @Test
    public void parse_missingCourseCodePrefix_failure() {
        String invalidInput = "CS2103T";
        assertParseFailure(parser, invalidInput, AddCourseCommandParser.MESSAGE_FORMAT_ERROR);
    }

    @Test
    public void parse_missingCourseCode_failure() {
        String invalidInput = " " + PREFIX_COURSE_CODE;
        assertParseFailure(parser, invalidInput, "\u274C Invalid course code. Example: c/CS2103T");
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String invalidInput = "extra " + PREFIX_COURSE_CODE + "CS2103T";
        assertParseFailure(parser, invalidInput, AddCourseCommandParser.MESSAGE_FORMAT_ERROR);
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "", AddCourseCommandParser.MESSAGE_FORMAT_ERROR);
    }

    @Test
    public void parse_courseCodeWithSpaces_failure() {
        // Course codes with spaces are not allowed (regex [A-Za-z0-9]{2,10})
        String courseCodeWithSpaces = "CS 2103T";
        assertParseFailure(parser, " " + PREFIX_COURSE_CODE + courseCodeWithSpaces,
                "\u274C Invalid course code. Example: c/CS2103T");
    }
}
