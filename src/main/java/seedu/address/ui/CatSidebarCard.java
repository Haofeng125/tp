package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.cat.Cat;

/**
 * A simplified UI card for the sidebar list, showing only the cat's index and name.
 */
public class CatSidebarCard extends UiPart<Region> {

    private static final String FXML = "CatSidebarCard.fxml";

    public final Cat cat;

    @FXML private HBox sidebarCardPane;
    @FXML private Label id;
    @FXML private Label name;

    /**
     * Creates a {@code CatSidebarCard} for the given {@code Cat} at the given index.
     */
    public CatSidebarCard(Cat cat, int displayedIndex) {
        super(FXML);
        this.cat = cat;
        id.setText(displayedIndex + ".");
        name.setText(cat.getName().fullName);
    }
}
