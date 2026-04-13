package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;

/**
 * Shows an overview summary of the current data.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "viewall";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows an overview summary of all assessments and grades.\n"
            + "Example: " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(ViewAllCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing viewall command");

        model.setDisplayMode(DisplayMode.OVERVIEW);

        return new CommandResult("Showing overview.");
    }
}
