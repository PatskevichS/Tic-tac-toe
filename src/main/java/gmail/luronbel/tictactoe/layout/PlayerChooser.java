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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * PlayerChooser.
 *
 * @author Stanislav_Patskevich
 */
@Component(PlayerChooser.PLAYER_CHOOSER_BEAN)
public class PlayerChooser extends VBox {
    public static final String PLAYER_CHOOSER_BEAN = "playerChooser";

    private static final int PADDING = 25;
    private static final int SPACING = 10;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 160;
    private static final int FONT_SIZE = 16;
    private static final String FONT_NAME = "times new roman";

    private final Button redPlayer;
    private final Button bluePLayer;
    private final Button back;

    public PlayerChooser(@Value("${window_width}") final int windowWidth, @Value("${window_height}") final int windowHeight) {
        final int menuWidth = BUTTON_WIDTH + (PADDING * 2);
        final double layoutX = (double) (windowWidth - menuWidth) / 2;

        final int menuHeight = (BUTTON_HEIGHT * 3) + (PADDING * 2) + (SPACING * 2);
        final double layoutY = (double) (windowHeight - menuHeight) / 2;

        setLayoutY(layoutY);
        setLayoutX(layoutX);
        setPadding(new Insets(PADDING));
        setSpacing(SPACING);
        setAlignment(Pos.CENTER);

        final javafx.scene.layout.Background background = new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY));

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

        final Circle circle = new Circle(BUTTON_HEIGHT);

        final Font font = new Font(FONT_NAME, FONT_SIZE);

        final Text caption = new Text("Pick your color");
        caption.setFill(Color.WHITE);
        caption.setFont(Font.font(FONT_NAME, FontWeight.BOLD, 22));

        redPlayer = new Button();
        redPlayer.setShape(circle);
        redPlayer.setStyle("-fx-background-color: #FF0000");
        redPlayer.setMinWidth(BUTTON_HEIGHT);
        redPlayer.setMinHeight(BUTTON_HEIGHT);
        redPlayer.setEffect(dropShadow);

        bluePLayer = new Button();
        bluePLayer.setShape(circle);
        bluePLayer.setStyle("-fx-background-color: #0000FF");
        bluePLayer.setMinWidth(BUTTON_HEIGHT);
        bluePLayer.setMinHeight(BUTTON_HEIGHT);
        bluePLayer.setEffect(dropShadow);

        back = new Button("Back");
        back.setFont(font);
        back.setShape(rectangle);
        back.setMinHeight(BUTTON_HEIGHT);
        back.setMinWidth(BUTTON_WIDTH);
        back.setEffect(dropShadow);

        final HBox hBox = new HBox(redPlayer, bluePLayer);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(BUTTON_HEIGHT);

        getChildren().addAll(caption, hBox, back);
        hide();
    }

    public void setRedPlayerButtonAction(final EventHandler<? super MouseEvent> action) {
        redPlayer.setOnMouseClicked(action);
    }

    public void setBluePlayerButtonAction(final EventHandler<? super MouseEvent> action) {
        bluePLayer.setOnMouseClicked(action);
    }

    public void setBackButtonAction(final EventHandler<? super MouseEvent> action) {
        back.setOnMouseClicked(action);
    }


    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
