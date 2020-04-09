package gmail.luronbel.tictactoe.component;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(Grid.GRID_BEAN)
public class Grid extends Group {
    public static final String GRID_BEAN = "grid";

    private final int lineWidth = 10;

    public Grid(@Value("${cell_size}") final int cellSize, @Value("${border_size}") final int borderSize,
                @Value("${header_size}") final int headerSize) {
        super();

        final Line l1 = new Line();
        final Line l2 = new Line();
        final Line l3 = new Line();
        final Line l4 = new Line();
        l1.setStroke(Color.WHITE);
        l2.setStroke(Color.WHITE);
        l3.setStroke(Color.WHITE);
        l4.setStroke(Color.WHITE);
        l1.setStrokeWidth(lineWidth);
        l2.setStrokeWidth(lineWidth);
        l3.setStrokeWidth(lineWidth);
        l4.setStrokeWidth(lineWidth);
        getChildren().addAll(l1, l2, l3, l4);

        final int size = cellSize * 3 + lineWidth * 2;
//        Vertical
        createVerticalLine(borderSize + cellSize, borderSize + headerSize, size);
        createVerticalLine(borderSize + 2 * cellSize + lineWidth, borderSize + headerSize, size);
//        Horizontal
        createHorizontalLine(borderSize, borderSize + cellSize + headerSize, size);
        createHorizontalLine(borderSize, borderSize + 2 * cellSize + lineWidth + headerSize, size);
    }

    private void createVerticalLine(final int x, final int y, final int size) {
        final double arc = (lineWidth / 4) * 3;
        final Rectangle rectangle = new Rectangle(x, y, lineWidth, size);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcHeight(arc);
        rectangle.setArcWidth(arc);
        getChildren().add(rectangle);
    }

    private void createHorizontalLine(final int x, final int y, final int size) {
        final double arc = (lineWidth / 4) * 3;
        final Rectangle rectangle = new Rectangle(x, y, size, lineWidth);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcHeight(arc);
        rectangle.setArcWidth(arc);
        getChildren().add(rectangle);
    }
}
