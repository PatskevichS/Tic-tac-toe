package gmail.luronbel.tictactoe.layout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Indication.
 *
 * @author Stas_Patskevich
 */
@Component(Indication.INDICATION_BEAN)
public class Indication extends Group {
    public static final String INDICATION_BEAN = "indication";

    private static final String IND_CAPTION = "turn";
    private static final int FONT_SIZE = 16;
    private static final String FONT_NAME = "times new roman";


    private final int width = 40;
    private final int height = 20;

    private final Rectangle redInd;
    private final Rectangle blueInd;

    public Indication(@Value("${window_width}") final int windowWidth) {
        redInd = new Rectangle(width, height, Color.RED);
        blueInd = new Rectangle(width, height, Color.BLUE);

        redInd.setX(0);
        redInd.setY(0);

        final int x = windowWidth - width;
        blueInd.setX(x);
        blueInd.setY(0);

        getChildren().addAll(redInd, blueInd);
    }

    public void showRedInd() {
        blueInd.setVisible(false);
        redInd.setVisible(true);
    }

    public void showBlueInd() {
        redInd.setVisible(false);
        blueInd.setVisible(true);
    }

    public void switchIndsOff() {
        redInd.setVisible(false);
        blueInd.setVisible(false);
    }
}
