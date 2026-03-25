package seedu.address.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Splash screen shown on startup. Transitions to the main window on SPACE.
 */
public class SplashScreen {

    private final Stage stage;
    private final Runnable onEnter;
    private boolean proceeded = false;

    /**
     * Creates a {@code SplashScreen} that calls {@code onEnter} when the user proceeds.
     */
    public SplashScreen(Stage stage, Runnable onEnter) {
        this.stage = stage;
        this.onEnter = onEnter;
    }

    /** Builds and shows the splash scene on the given stage. */
    public void show() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #fdfcdc;");

        VBox content = new VBox(16);
        content.setAlignment(Pos.CENTER);

        Label title = new Label("CatPals");
        javafx.scene.text.Font momoFont = javafx.scene.text.Font.loadFont(
                getClass().getResourceAsStream("/view/MomoSignature-Regular.ttf"), 14);
        if (momoFont != null) {
            title.setStyle(
                    "-fx-font-family: '" + momoFont.getFamily() + "';"
                    + "-fx-font-size: 58pt;"
                    + "-fx-text-fill: #0081a7;");
        } else {
            title.setStyle(
                    "-fx-font-family: 'Georgia';"
                    + "-fx-font-size: 58pt;"
                    + "-fx-font-style: italic;"
                    + "-fx-text-fill: #0081a7;");
        }

        Label subtitle = new Label("cat colony manager");
        subtitle.setStyle(
                "-fx-font-family: 'Georgia';"
                + "-fx-font-size: 22pt;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-style: italic;"
                + "-fx-text-fill: #00afb9;");

        Label hint = new Label("~ press SPACE to enter ~");
        hint.setStyle(
                "-fx-font-family: 'Georgia';"
                + "-fx-font-size: 15pt;"
                + "-fx-font-style: italic;"
                + "-fx-text-fill: #f07167;"
                + "-fx-padding: 24 0 0 0;");

        content.getChildren().addAll(title, subtitle, hint);
        root.getChildren().add(content);

        Scene splashScene = new Scene(root);
        splashScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                proceed();
            }
        });

        stage.setScene(splashScene);
        stage.setTitle("CatPals");

        root.setOpacity(0);
        stage.show();

        FadeTransition ft = new FadeTransition(Duration.millis(700), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    private void proceed() {
        if (proceeded) {
            return;
        }
        proceeded = true;
        onEnter.run();
    }
}
