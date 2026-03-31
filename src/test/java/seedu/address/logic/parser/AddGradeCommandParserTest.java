package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.model.grade.Score;

public class AddGradeCommandParserTest {

    private final AddGradeCommandParser parser = new AddGradeCommandParser();

    @Test
    public void parse_invalidStudentId_failure() {
            String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                            + PREFIX_STUDENT_ID + "A12 "
                            + PREFIX_ASSESSMENT + "1 "
                            + PREFIX_GRADE + "9";

            assertParseFailure(parser, userInput, "❌ Invalid student ID. Example: id/A0123456X");
    }

    @Test
    public void parse_whitespaceOnlyPreamble_success() {
        String userInput = " \n \t " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT_ID + "A0123456X "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        AddGradeCommand expectedCommand = new AddGradeCommand("CS2103T",
                "A0123456X", Index.fromOneBased(1), new Score("9"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCourseCode_failure() {
        String userInput = " " + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStudentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingGrade_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = "preamble " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS 2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput, "❌ Invalid course code. Example: CS2103T");
    }

    @Test
    public void parse_invalidStudentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "0 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "0 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidScore_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "-1";

        assertParseFailure(parser, userInput, seedu.address.model.grade.Score.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleCourseCodeValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_COURSE_CODE + "CS2101 "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }

    @Test
    public void parse_multipleStudentValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_STUDENT + "2 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT));
    }

    @Test
    public void parse_multipleAssessmentValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_ASSESSMENT + "2 "
                + PREFIX_GRADE + "9";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSESSMENT));
    }

    @Test
    public void parse_multipleGradeValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_STUDENT + "1 "
                + PREFIX_ASSESSMENT + "1 "
                + PREFIX_GRADE + "9 "
                + PREFIX_GRADE + "10";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));
    }
}
