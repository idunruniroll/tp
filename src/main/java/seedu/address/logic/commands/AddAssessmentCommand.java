package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

public class AddAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "addassessment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assessment.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_MAX_SCORE + "MAX_SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Midterm "
            + PREFIX_MAX_SCORE + "100";

    public static final String MESSAGE_SUCCESS = "New assessment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "This assessment already exists.";

    private final Assessment toAdd;

    public AddAssessmentCommand(Assessment assessment) {
        requireNonNull(assessment);
        toAdd = assessment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAssessment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSESSMENT);
        }

        model.addAssessment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAssessmentCommand
                        && toAdd.equals(((AddAssessmentCommand) other).toAdd));
    }
}
