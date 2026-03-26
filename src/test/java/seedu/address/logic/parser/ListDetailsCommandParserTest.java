package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListDetailsCommand;

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
        assertParseFailure(parser, "CS2103T", String.format(MESSAGE_INVALID_COMMAND_FORMAT
                                    + ListDetailsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        assertParseFailure(parser, " c/@@@", "\u274C Invalid course code. Example: CS2103T");
    }
}
