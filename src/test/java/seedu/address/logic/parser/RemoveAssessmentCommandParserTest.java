package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.RemoveAssessmentCommand;

public class RemoveAssessmentCommandParserTest {

    private final RemoveAssessmentCommandParser parser = new RemoveAssessmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_COURSE_CODE + "cs2103t " + PREFIX_ASSESSMENT + "1";

        RemoveAssessmentCommand expectedCommand = new RemoveAssessmentCommand("CS2103T", Index.fromOneBased(1));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCourseCode_failure() {
        String userInput = " " + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = "preamble " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS 2103T " + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput, "❌ Invalid course code. Example: CS2103T");
    }

    @Test
    public void parse_invalidAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT + "0";

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_multipleCourseCodeValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_COURSE_CODE + "CS2101 "
                + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }

    @Test
    public void parse_multipleAssessmentValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_ASSESSMENT + "2";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSESSMENT));
    }
}
