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
import seedu.address.logic.commands.RemoveGradeCommand;

public class RemoveGradeCommandParserTest {

    private final RemoveGradeCommandParser parser = new RemoveGradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_COURSE_CODE + "cs2103t "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "2";

        RemoveGradeCommand expectedCommand = new RemoveGradeCommand("CS2103T", "A0123456X",
            Index.fromOneBased(2));

        assertParseSuccess(parser, userInput, expectedCommand);
        }

     @Test
     public void parse_whitespaceOnlyPreamble_success() {
        String userInput = " \n \t " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "1";

        RemoveGradeCommand expectedCommand = new RemoveGradeCommand("CS2103T", "A0123456X",
        Index.fromOneBased(1));

        assertParseSuccess(parser, userInput, expectedCommand);
}

    @Test
    public void parse_missingCourseCode_failure() {
        String userInput = " " + PREFIX_STUDENT_ID + "A0123456X " + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
}

    @Test
    public void parse_missingStudentId_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
}

    @Test
    public void parse_missingAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_STUDENT_ID + "A0123456X";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = "preamble " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
}

    @Test
    public void parse_invalidCourseCode_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS 2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                "❌ Invalid course code. Example: CS2103T");
        }

    @Test
    public void parse_invalidStudentId_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A12 "
            + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                "❌ Invalid student ID. Example: id/A0123456X");
        }

    @Test
    public void parse_invalidAssessmentIndex_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "0";

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_multipleCourseCodeValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_COURSE_CODE + "CS2101 "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE_CODE));
        }

    @Test
    public void parse_multipleStudentValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_STUDENT_ID + "A0123456Y "
            + PREFIX_ASSESSMENT + "1";

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));
        }

    @Test
    public void parse_multipleAssessmentValues_failure() {
        String userInput = " " + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_ASSESSMENT + "1 "
            + PREFIX_ASSESSMENT + "2";

        assertParseFailure(parser, userInput,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSESSMENT));
        }
}
