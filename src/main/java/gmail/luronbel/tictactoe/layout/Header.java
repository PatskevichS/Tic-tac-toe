package gmail.luronbel.tictactoe.layout;

import static gmail.luronbel.tictactoe.component.Game.GAME_BEAN;

import gmail.luronbel.tictactoe.component.Game;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 30;
    private static final int CORRELATION = 25;

    private final Button refreshButton;
    private final Text redText = new Text("0");
    private final Text blueText = new Text("0");

    private int redScore = 0;
    private int blueScore = 0;

    @Autowired
    @Qualifier(GAME_BEAN)
    private Game game;

    public Header(@Value("${window_width}") final int windowWidth,
                  @Value("${header_size}") final int headerSize,
                  @Value("${cell_size}") final int cellSize,
                  @Value("${border_size}") final int borderSize,
                  @Value("${line_width}") final int lineWidth) {
        final InnerShadow innerShadow = new InnerShadow();
        final DropShadow dropShadow = new DropShadow();
        innerShadow.setInput(dropShadow);
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
        refreshButton.setMaxWidth(BUTTON_WIDTH);
        refreshButton.setEffect(innerShadow);

        final int x = borderSize + cellSize + lineWidth + ((cellSize - BUTTON_WIDTH) / 2);
        final int y = (headerSize - BUTTON_HEIGHT) / 2;
        refreshButton.setLayoutX(x);
        refreshButton.setLayoutY(y);

        refreshButton.setOnMouseClicked(event -> {
            game.refresh();
            redScore = 0;
            blueScore = 0;
            redText.setText("" + redScore);
            blueText.setText("" + blueScore);
        });

        final Rectangle redScoreRec = new Rectangle(BUTTON_WIDTH - 20, BUTTON_HEIGHT, Color.WHITE);
        final Rectangle blueScoreRec = new Rectangle(BUTTON_WIDTH - 20, BUTTON_HEIGHT, Color.WHITE);
        redScoreRec.setEffect(innerShadow);
        blueScoreRec.setEffect(innerShadow);

        redText.setFont(font);
        blueText.setFont(font);

        final StackPane redStack = new StackPane(redScoreRec, redText);
        final StackPane blueStack = new StackPane(blueScoreRec, blueText);

        redStack.setLayoutX(x - BUTTON_WIDTH + 20 - 15);
        redStack.setLayoutY(y + 3);

        blueStack.setLayoutX(x + BUTTON_WIDTH + 15);
        blueStack.setLayoutY(y + 3);

        getChildren().addAll(refreshButton, redStack, blueStack);
    }

    // true - red, false - blue
    public void addScoreTo(final boolean player) {
        if (player) {
            redScore++;
            redText.setText("" + redScore);
        } else {
            blueScore++;
            blueText.setText("" + blueScore);
        }
    }
}
