package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCourseCommand;
import seedu.address.model.course.Course;

public class AddCourseCommandParserTest {
    private AddCourseCommandParser parser = new AddCourseCommandParser();

    @Test
    public void parse_validSingleCourseCode_success() {
        String input = "c/CS2103T";
        AddCourseCommand expectedCommand = new AddCourseCommand(Arrays.asList("CS2103T"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_validMultipleCourseCodes_success() {
        String input = "c/CS2103T,CS2101";
        AddCourseCommand expectedCommand = new AddCourseCommand(Arrays.asList("CS2103T", "CS2101"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_courseCodesWithWhitespace_success() {
        String input = "c/CS2103T , CS2101 , CS2100";
        AddCourseCommand expectedCommand = new AddCourseCommand(Arrays.asList("CS2103T", "CS2101", "CS2100"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, "CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnlyInput_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixedInput_failure() {
        assertParseFailure(parser, "c/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        assertParseFailure(parser, "c/@@@", Course.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        assertParseFailure(parser, "c/CS2103T c/CS2101",
                getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }
}
