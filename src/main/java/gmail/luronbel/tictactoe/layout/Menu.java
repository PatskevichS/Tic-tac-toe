package gmail.luronbel.tictactoe.layout;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Menu.
 *
 * @author Stas_Patskevich
 */
@Component(Menu.MENU_BEAN)
public class Menu extends VBox {
    public static final String MENU_BEAN = "menu";

    private static final int PADDING = 25;
    private static final int SPACING = 10;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 160;
    private static final int FONT_SIZE = 16;
    private static final String FONT_NAME = "times new roman";

    private final Button playerVsBotButton;
    private final Button playerVsPlayerButton;
    private final Button exitButton;

    public Menu(@Value("${window_width}") final int windowWidth, @Value("${window_height}") final int windowHeight) {

        final int menuWidth = BUTTON_WIDTH + (PADDING * 2);
        final double layoutX = (double) (windowWidth - menuWidth) / 2;

        final int menuHeight = (BUTTON_HEIGHT * 3) + (PADDING * 2) + (SPACING * 2);
        final double layoutY = (double) (windowHeight - menuHeight) / 2;

        setLayoutY(layoutY);
        setLayoutX(layoutX);
        setPadding(new Insets(PADDING));
        setSpacing(SPACING);
        setAlignment(Pos.CENTER);

        final Background background = new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY));

        setBackground(background);
        final InnerShadow innerShadow = new InnerShadow();
        final DropShadow dropShadow = new DropShadow();
        innerShadow.setInput(dropShadow);
        setEffect(innerShadow);


        final int arc = (BUTTON_HEIGHT / 4) * 3;
        final Rectangle rectangle = new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT);
        rectangle.setArcHeight(arc);
        rectangle.setArcWidth(arc);
        rectangle.setFill(Color.DARKGRAY);

        final Font font = new Font(FONT_NAME, FONT_SIZE);

        playerVsBotButton = new Button("Player vs Bot");
        playerVsBotButton.setFont(font);
        playerVsBotButton.setShape(rectangle);
        playerVsBotButton.setMinHeight(BUTTON_HEIGHT);
        playerVsBotButton.setMinWidth(BUTTON_WIDTH);
        playerVsBotButton.setEffect(dropShadow);

        playerVsPlayerButton = new Button("Player vs Player");
        playerVsPlayerButton.setFont(font);
        playerVsPlayerButton.setShape(rectangle);
        playerVsPlayerButton.setMinHeight(BUTTON_HEIGHT);
        playerVsPlayerButton.setMinWidth(BUTTON_WIDTH);
        playerVsPlayerButton.setEffect(dropShadow);

        exitButton = new Button("Exit");
        exitButton.setFont(font);
        exitButton.setShape(rectangle);
        exitButton.setMinHeight(BUTTON_HEIGHT);
        exitButton.setMinWidth(BUTTON_WIDTH);
        exitButton.setEffect(dropShadow);

        getChildren().addAll(playerVsBotButton, playerVsPlayerButton, exitButton);
        hide();
    }

    public void setPlayerVsBotButtonAction(final EventHandler<? super MouseEvent> action) {
        playerVsBotButton.setOnMouseClicked(action);
    }

    public void setPlayerVsPlayerButtonAction(final EventHandler<? super MouseEvent> action) {
        playerVsPlayerButton.setOnMouseClicked(action);
    }

    public void setExitButtonAction(final EventHandler<? super MouseEvent> action) {
        exitButton.setOnMouseClicked(action);
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public boolean isShown() {
        return isVisible();
    }
}
