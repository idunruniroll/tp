package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_SCORE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAssessmentCommand;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;

public class AddAssessmentCommandParserTest {

    private final AddAssessmentCommandParser parser = new AddAssessmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_COURSE_CODE + "cs2103t "
                + PREFIX_ASSESSMENT_NAME + "Midterm "
                + PREFIX_MAX_SCORE + "100";

        AddAssessmentCommand expectedCommand = new AddAssessmentCommand("CS2103T", "Midterm", "100");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyPreamble_success() {
        String userInput = " \n \t " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + "Finals "
                + PREFIX_MAX_SCORE + "50";

        AddAssessmentCommand expectedCommand = new AddAssessmentCommand("CS2103T", "Finals", "50");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCourseCode_failure() {
        String userInput = " " + PREFIX_ASSESSMENT_NAME + "Midterm " + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssessmentName_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingMaxScore_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT_NAME + "Midterm";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = "preamble " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + "Midterm " + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssessmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCourseCode_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS 2103T "
                + PREFIX_ASSESSMENT_NAME + "Midterm " + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput, "❌ Invalid course code. Example: CS2103T");
    }

    @Test
    public void parse_blankAssessmentName_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + "    " + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput, AssessmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidMaxScore_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + "Midterm " + PREFIX_MAX_SCORE + "0";

        assertParseFailure(parser, userInput, MaxScore.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleCourseCodeValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_COURSE_CODE + "CS2101 "
                + PREFIX_ASSESSMENT_NAME + "Midterm "
                + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
    }

    @Test
    public void parse_multipleAssessmentNameValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + "Midterm "
                + PREFIX_ASSESSMENT_NAME + "Finals "
                + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSESSMENT_NAME));
    }

    @Test
    public void parse_multipleMaxScoreValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + "Midterm "
                + PREFIX_MAX_SCORE + "100 "
                + PREFIX_MAX_SCORE + "50";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MAX_SCORE));
    }

    @Test
    public void parse_assessmentNameTooLong_failure() {
        String longName = "A".repeat(51);
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
                + PREFIX_ASSESSMENT_NAME + longName + " "
                + PREFIX_MAX_SCORE + "100";

        assertParseFailure(parser, userInput, AssessmentName.MESSAGE_CONSTRAINTS);
    }
}
