package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCourseCommand;

public class AddCourseCommandParserTest {
    private AddCourseCommandParser parser = new AddCourseCommandParser();

    @Test
    public void parse_validSingleCourseCode_success() {
        String validCourseCode = "CS2103T";
        AddCourseCommand expectedCommand = new AddCourseCommand(Arrays.asList(validCourseCode));

        assertParseSuccess(parser, validCourseCode, expectedCommand);
    }

    @Test
    public void parse_validMultipleCourseCodes_success() {
        String input = "CS2103T,CS2101";
        AddCourseCommand expectedCommand = new AddCourseCommand(Arrays.asList("CS2103T", "CS2101"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_courseCodesWithWhitespace_success() {
        String input = "CS2103T , CS2101 , CS2100";
        AddCourseCommand expectedCommand = new AddCourseCommand(Arrays.asList("CS2103T", "CS2101", "CS2100"));

        assertParseSuccess(parser, input, expectedCommand);
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
}
