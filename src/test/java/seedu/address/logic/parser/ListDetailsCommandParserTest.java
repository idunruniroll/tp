package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListDetailsCommand;
import seedu.address.model.course.Course;

public class ListDetailsCommandParserTest {

    private ListDetailsCommandParser parser = new ListDetailsCommandParser();

    @Test
    public void parse_validSingleCourse_success() {
        assertParseSuccess(parser, " c/CS2103T", new ListDetailsCommand(Arrays.asList("CS2103T")));
    }

    @Test
    public void parse_validMultipleCourses_success() {
        assertParseSuccess(parser, " c/CS2103T,CS2101", new ListDetailsCommand(Arrays.asList("CS2103T", "CS2101")));
    }

    @Test
    public void parse_whitespaceArguments_success() {
        assertParseSuccess(parser, " c/CS2103T , CS2101 ", new ListDetailsCommand(Arrays.asList("CS2103T", "CS2101")));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, "CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDetailsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        assertParseFailure(parser, " c/@@@", Course.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_whitespaceOnlyInput_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDetailsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixedInput_failure() {
        assertParseFailure(parser, " c/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDetailsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        assertParseFailure(parser, " c/CS2103T c/CS2101",
                getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }
}
