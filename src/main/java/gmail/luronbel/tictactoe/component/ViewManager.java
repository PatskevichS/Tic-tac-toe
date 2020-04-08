package gmail.luronbel.tictactoe.component;

import static gmail.luronbel.tictactoe.layout.GameElementsGroup.GAME_ELEMENTS_BEAN;

import gmail.luronbel.tictactoe.layout.GameElementsGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ViewManager.
 *
 * @author Stas_Patskevich
 */
@Component(ViewManager.VIEW_MANAGER_BEAN)
public class ViewManager {
    public static final String VIEW_MANAGER_BEAN = "viewManager";

    private static final int COUNT_CROSS = 5;
    private static final int COUNT_CIRCLE = 4;

    @Value("${cell_size}")
    private int cellSize;

    @Value("${border_size}")
    private int borderSize;

    @Value("${margin}")
    private int margin;

    private final int lineWidth;
    private int crossPointer;
    private int circlePointer;
    private final Cross[] crosses;
    private final Circle[] circles;
    private final Line line;

    @Autowired
    public ViewManager(@Qualifier(GAME_ELEMENTS_BEAN) final GameElementsGroup gameElementsGroup,
                       @Value("${line_width}") final int lineWidth) {
        this.lineWidth = lineWidth;
        crosses = new Cross[COUNT_CROSS];
        circles = new Circle[COUNT_CIRCLE];

        final double arc = (lineWidth / 4) * 3;
        for (int i = 0; i < 5; i++) {
            final Rectangle first = new Rectangle();
            final Rectangle second = new Rectangle();
            first.setFill(Color.RED);
            second.setFill(Color.RED);
            first.setArcHeight(arc);
            first.setArcWidth(arc);
            second.setArcHeight(arc);
            second.setArcWidth(arc);
            final Cross cross = new Cross(first, second);
            crosses[i] = cross;
            first.setVisible(false);
            second.setVisible(false);
            gameElementsGroup.getChildren().addAll(first, second);
        }

        for (int i = 0; i < 4; i++) {
            final javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();
            circle.setFill(Color.TRANSPARENT);
            circle.setStrokeWidth(lineWidth);
            circle.setStroke(Color.BLUE);
            final Circle circleView = new Circle(circle);
            circles[i] = circleView;
            circle.setVisible(false);
            gameElementsGroup.getChildren().add(circle);
        }

        line = new Line();
        line.setStrokeWidth(10);
        line.setVisible(false);

        crossPointer = 0;
        circlePointer = 0;
    }

    public void showCross(final int y, final int x) {
        if (crosses.length < crossPointer) {
            throw new IllegalArgumentException("Cross cache is empty.");
        }
        final Cross cross = crosses[crossPointer];
        final Rectangle first = cross.getFirst();
        final Rectangle second = cross.getSecond();

        final int _x = borderSize + margin + (x - 1) * cellSize + (x - 1) * lineWidth;
        final int _y = borderSize + margin + (y - 1) * cellSize + (y - 1) * lineWidth + (cellSize / 7) * 3;

        first.setX(_x);
        first.setY(_y);
        first.setHeight(lineWidth);
        first.setWidth(cellSize - 2 * margin);
        first.setRotate(45);

        second.setX(_x);
        second.setY(_y);
        second.setHeight(lineWidth);
        second.setWidth(cellSize - 2 * margin);
        second.setRotate(-45);

        first.setVisible(true);
        second.setVisible(true);
        crossPointer++;
    }

    public void showCircle(final int y, final int x) {
        if (circles.length < circlePointer) {
            throw new IllegalArgumentException("Circle cache is empty.");
        }
        final Circle circleView = circles[circlePointer];
        final javafx.scene.shape.Circle circle = circleView.getCircle();

        final int _x = borderSize + (x - 1) * cellSize + (x - 1) * lineWidth + cellSize / 2;
        final int _y = borderSize + (y - 1) * cellSize + (y - 1) * lineWidth + cellSize / 2;

        circle.setCenterX(_x);
        circle.setCenterY(_y);
        circle.setRadius(((cellSize / 7) * 3) - (2 * margin));

        circle.setVisible(true);
        circlePointer++;
    }

    public void reset() {
        for (int i = 0; i < COUNT_CROSS; i++) {
            final Cross cross = crosses[i];
            cross.getFirst().setVisible(false);
            cross.getSecond().setVisible(false);
        }

        for (int i = 0; i < COUNT_CIRCLE; i++) {
            final Circle circle = circles[i];
            circle.getCircle().setVisible(false);
        }

        crossPointer = 0;
        circlePointer = 0;
    }

    @Data
    @AllArgsConstructor
    private class Cross {
        private final Rectangle first;
        private final Rectangle second;
    }

    @Data
    @AllArgsConstructor
    private class Circle {
        private final javafx.scene.shape.Circle circle;
    }
}
