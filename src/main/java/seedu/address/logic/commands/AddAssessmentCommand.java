package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;

/**
 * Adds an assessment to the address book.
 */
public class AddAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "addassessment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assessment.\n"
            + "Parameters: "
            + "c/COURSE_CODE "
            + "an/ASSESSMENT_NAME "
            + "m/MAX_SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + "c/CS2103T "
            + "an/Midterm "
            + "m/100";

    private final String courseCode;
    private final AssessmentName assessmentName;
    private final MaxScore maxScore;

    /**
     * Constructs an AddAssessmentCommand with the specified course code, assessment
     * name, and max score.
     *
     * @param courseCode     the course code
     * @param assessmentName the assessment name
     * @param maxScore       the maximum score
     */
    public AddAssessmentCommand(String courseCode, String assessmentName, String maxScore) {
        requireNonNull(courseCode);
        requireNonNull(assessmentName);
        requireNonNull(maxScore);

        this.courseCode = courseCode;
        this.assessmentName = new AssessmentName(assessmentName);
        this.maxScore = new MaxScore(maxScore);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(Messages.MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        Assessment toAdd = new Assessment(courseCode, assessmentName, maxScore);

        if (model.hasAssessment(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_ASSESSMENT);
        }

        for (Assessment existing : model.getAssessmentList()) {
            if (existing.getCourseCode().equalsIgnoreCase(toAdd.getCourseCode())) {
                String existingName = existing.getAssessmentName().getNormalizedName();
                String newName = toAdd.getAssessmentName().getNormalizedName();

                if (!existingName.equals(newName) && areLikelyTypos(existingName, newName)) {
                    throw new CommandException(String.format(
                            Messages.MESSAGE_SIMILAR_ASSESSMENT, existing.getAssessmentName()));
                }
            }
        }

        model.addAssessment(toAdd);
        return new CommandResult(String.format(Messages.MESSAGE_ADD_ASSESSMENT_SUCCESS, toAdd));
    }

    /**
     * Returns the minimum number of single-character edits needed
     * to change firstString into secondString.
     */
    private int getEditDistance(String firstString, String secondString) {
        int firstLength = firstString.length();
        int secondLength = secondString.length();

        int[][] distanceTable = new int[firstLength + 1][secondLength + 1];

        for (int i = 0; i <= firstLength; i++) {
            for (int j = 0; j <= secondLength; j++) {
                if (i == 0) {
                    distanceTable[i][j] = j;
                } else if (j == 0) {
                    distanceTable[i][j] = i;
                } else {
                    int deleteCost = distanceTable[i - 1][j] + 1;
                    int insertCost = distanceTable[i][j - 1] + 1;

                    int replaceCost = distanceTable[i - 1][j - 1];
                    if (firstString.charAt(i - 1) != secondString.charAt(j - 1)) {
                        replaceCost += 1;
                    }

                    distanceTable[i][j] = Math.min(
                            Math.min(deleteCost, insertCost),
                            replaceCost);
                }
            }
        }

        return distanceTable[firstLength][secondLength];
    }

    /**
     * Returns true if two assessment names are likely spelling mistakes
     * of each other rather than genuinely different assessments.
     */
    private boolean areLikelyTypos(String firstName, String secondName) {
        String firstBaseName = removeTrailingNumber(firstName);
        String secondBaseName = removeTrailingNumber(secondName);

        String firstTrailingNumber = getTrailingNumber(firstName);
        String secondTrailingNumber = getTrailingNumber(secondName);

        boolean bothHaveNumbers = !firstTrailingNumber.isEmpty() && !secondTrailingNumber.isEmpty();
        boolean sameBaseName = firstBaseName.equals(secondBaseName);
        boolean differentNumbers = !firstTrailingNumber.equals(secondTrailingNumber);

        // Allow names like "quiz 1" and "quiz 2", "presentation 1" and "presentation 2"
        if (bothHaveNumbers && sameBaseName && differentNumbers) {
            return false;
        }

        // Otherwise, check whether the names are very close
        return getEditDistance(firstName, secondName) <= 2;
    }

    /**
     * Removes a trailing number from the assessment name.
     * Example: "quiz 2" -> "quiz"
     */
    private String removeTrailingNumber(String assessmentName) {
        return assessmentName.replaceFirst("\\s+\\d+$", "").trim();
    }

    /**
     * Returns the trailing number in the assessment name, if any.
     * Example: "quiz 2" -> "2"
     */
    private String getTrailingNumber(String assessmentName) {
        String trimmedName = assessmentName.trim();
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+)$").matcher(trimmedName);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAssessmentCommand
                        && courseCode.equals(((AddAssessmentCommand) other).courseCode)
                        && assessmentName.equals(((AddAssessmentCommand) other).assessmentName)
                        && maxScore.equals(((AddAssessmentCommand) other).maxScore));
    }
}
