package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListGradesCommand;

public class ListGradesCommandParserTest {

    private final ListGradesCommandParser parser = new ListGradesCommandParser();

    @Test
    public void parse_studentFilter_success() {
        String userInput = " " + PREFIX_STUDENT_ID + "a0123456x";

        ListGradesCommand expectedCommand = new ListGradesCommand("student", "A0123456X", null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_courseFilter_success() {
        String userInput = " " + PREFIX_COURSE_CODE + "cs2103t";

        ListGradesCommand expectedCommand = new ListGradesCommand("course", "CS2103T", null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_courseAssessmentFilter_success() {
        String userInput = " " + PREFIX_COURSE_CODE + "cs2103t " + PREFIX_ASSESSMENT + "2";

        ListGradesCommand expectedCommand = new ListGradesCommand("courseassessment", "CS2103T", Index.fromOneBased(2));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noArguments_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGradesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = "preamble " + PREFIX_COURSE_CODE + "CS2103T";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGradesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS 2103T";

        assertParseFailure(parser, userInput,
                "❌ Invalid course code. Example: c/CS2103T");
    }

    @Test
    public void parse_invalidStudentId_failure() {
        String userInput = " " + PREFIX_STUDENT_ID + "A1";

        assertParseFailure(parser, userInput,
                "❌ Invalid student ID. Example: id/A0123456X");
    }

    @Test
    public void parse_invalidAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT + "0";

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_duplicateCourseCode_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_COURSE_CODE + "CS2101";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }

    @Test
    public void parse_duplicateStudentId_failure() {
        String userInput = " " + PREFIX_STUDENT_ID + "A0123456X "
                + PREFIX_STUDENT_ID + "A7654321B";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));
    }

    @Test
    public void parse_duplicateAssessment_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_ASSESSMENT + "2";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSESSMENT));
    }

    @Test
    public void parse_studentAndCourseTogether_failure() {
        String userInput = " " + PREFIX_STUDENT_ID + "A0123456X "
                + PREFIX_COURSE_CODE + "CS2103T";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGradesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_studentAndAssessmentTogether_failure() {
        String userInput = " " + PREFIX_STUDENT_ID + "A0123456X "
                + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGradesCommand.MESSAGE_USAGE));
    }
}
