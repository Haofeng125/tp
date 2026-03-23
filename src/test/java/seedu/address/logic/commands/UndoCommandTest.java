package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCats.BOWIE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains unit tests for {@link UndoCommand}.
 */
public class UndoCommandTest {

    @Test
    public void execute_noUndoableState_returnsNothingToUndo() throws Exception {
        Model model = new ModelManager();
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_NOTHING_TO_UNDO, result.getFeedbackToUser());
    }

    @Test
    public void execute_afterAdd_undoesAdd() throws Exception {
        Model model = new ModelManager();
        model.saveUndoState();
        model.addCat(BOWIE);

        CommandResult result = new UndoCommand().execute(model);

        assertEquals(UndoCommand.MESSAGE_UNDO_SUCCESS, result.getFeedbackToUser());
        assertEquals(new ModelManager(), model);
    }

    @Test
    public void execute_consecutiveUndo_secondUndoDoesNothing() throws Exception {
        Model model = new ModelManager();
        model.saveUndoState();
        model.addCat(BOWIE);

        new UndoCommand().execute(model); // first undo — restores state and clears snapshot
        CommandResult result = new UndoCommand().execute(model); // second undo — nothing to undo

        assertEquals(UndoCommand.MESSAGE_NOTHING_TO_UNDO, result.getFeedbackToUser());
    }
}
