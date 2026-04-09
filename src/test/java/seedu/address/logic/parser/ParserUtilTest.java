package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;

public class ParserUtilTest {
    private static final String INVALID_EMAIL = "example.com";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        assertEquals(VALID_EMAIL, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        assertEquals(VALID_EMAIL, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseAssessmentName_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAssessmentName(" "));
    }

    @Test
    public void parseAssessmentName_valid_success() throws Exception {
        assertEquals("Quiz 1", ParserUtil.parseAssessmentName("  quiz   1 ").toString());
    }

    @Test
    public void parseMaxScore_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMaxScore("0"));
    }

    @Test
    public void parseMaxScore_valid_success() throws Exception {
        assertEquals("100.0", ParserUtil.parseMaxScore("100").toString());
    }

    @Test
    public void parseScore_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("-1"));
    }

    @Test
    public void parseScore_valid_success() throws Exception {
        assertEquals("9.0", ParserUtil.parseScore("9").toString());
    }

    @Test
    public void parseCourseCode_valid_success() throws Exception {
        assertEquals("CS2103T", ParserUtil.parseCourseCode("cs2103t"));
    }

    @Test
    public void parseStudentId_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudentId("A1"));
    }

    @Test
    public void parseStudentId_invalidMessage_throwsParseExceptionWithCentralizedMessage() {
        assertThrows(ParseException.class, Messages.MESSAGE_INVALID_STUDENT_ID_FORMAT, () ->
                ParserUtil.parseStudentId("A1"));
    }

    @Test
    public void parseStudentId_valid_success() throws Exception {
        assertEquals("A0123456X", ParserUtil.parseStudentId("a0123456x"));
    }

    @Test
    public void parseStudentName_invalid_throwsParseExceptionWithCentralizedMessage() {
        assertThrows(ParseException.class, Messages.MESSAGE_INVALID_STUDENT_NAME_FORMAT, () ->
                ParserUtil.parseStudentName("1234"));
    }

    public void parseCourseCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCourseCode(null));
    }

    @Test
    public void parseCourseCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, Course.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseCourseCode("CS 2103T"));
    }

    @Test
    public void parseCourseCode_validValueWithWhitespace_returnsUppercaseTrimmedCode() throws Exception {
        assertEquals("CS2103T", ParserUtil.parseCourseCode(WHITESPACE + "cs2103t" + WHITESPACE));
    }
}
