package gmail.luronbel.tictactoe.layout;

import static gmail.luronbel.tictactoe.component.Game.GAME_BEAN;

import gmail.luronbel.tictactoe.component.Game;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Header.
 *
 * @author Stas_Patskevich
 */

@Component(Header.HEADER_BEAN)
public class Header extends Group {
    public static final String HEADER_BEAN = "header";

    private static final String FONT_NAME = "times new roman";
    private static final int FONT_SIZE = 20;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private static final int CORRELATION = 25;

    private final Button refreshButton;

    @Autowired
    @Qualifier(GAME_BEAN)
    private Game game;

    public Header(@Value("${window_width}") final int windowWidth,
                  @Value("${header_size}") final int headerSize) {
        final InnerShadow innerShadow = new InnerShadow();
        final DropShadow dropShadow = new DropShadow();
        final Font font = new Font(FONT_NAME, FONT_SIZE);
        final int arc = (BUTTON_HEIGHT / 4) * 3;
        final Rectangle rectangle = new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT);
        rectangle.setArcHeight(arc);
        rectangle.setArcWidth(arc);
        rectangle.setFill(Color.DARKGRAY);

        refreshButton = new Button("refresh");
        refreshButton.setFont(font);
        refreshButton.setShape(rectangle);
        refreshButton.setMinHeight(BUTTON_HEIGHT);
        refreshButton.setMinWidth(BUTTON_WIDTH);
        refreshButton.setEffect(dropShadow);

        final int x = (windowWidth - BUTTON_WIDTH - CORRELATION) / 2;
        final int y = (headerSize - BUTTON_HEIGHT) / 2;
        refreshButton.setLayoutX(x);
        refreshButton.setLayoutY(y);

        refreshButton.setOnMouseClicked(event -> {
            game.refresh();
        });

        getChildren().addAll(refreshButton);
    }
}
