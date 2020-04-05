package gmail.luronbel.tictactoe.component;

import static gmail.luronbel.tictactoe.layout.GameElementsGroup.GAME_ELEMENTS_BEAN;

import gmail.luronbel.tictactoe.layout.GameElementsGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ViewManager.
 *
 * @author Stas_Patskevich
 */
@Component(ViewManager.VIEW_MANAGER_BEAN)
public class ViewManager {
    public static final String VIEW_MANAGER_BEAN = "viewManager";

    @Value("${cell_size}")
    private int cellSize;

    @Value("${border_size}")
    private int borderSize;

    private final int margin = 5;

    private final Queue<Cross> crosses = new LinkedList<>();
    private final Queue<Circle> circles = new LinkedList<>();

    @Autowired
    public ViewManager(@Qualifier(GAME_ELEMENTS_BEAN) final GameElementsGroup gameElementsGroup) {
        for (int i = 0; i < 5; i++) {
            final Line first = new Line();
            final Line second = new Line();
            first.setStroke(Color.RED);
            second.setStroke(Color.RED);
            final Cross cross = new Cross(first, second);
            crosses.add(cross);
            first.setVisible(false);
            second.setVisible(false);
            gameElementsGroup.getChildren().addAll(first, second);
        }

        for (int i = 0; i < 4; i++) {
            final javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();
            circle.setStroke(Color.BLUE);
            final Circle circleView = new Circle(circle);
            circles.add(circleView);
            circle.setVisible(false);
            gameElementsGroup.getChildren().add(circle);
        }

    }

    public void showCross(final int x, final int y) {
        final Cross cross = crosses.poll();
        if (cross == null) {
            throw new IllegalArgumentException("Cross cache is empty.");
        }
        final Line first = cross.getFirst();
        final Line second = cross.getSecond();

        final int _x = borderSize + margin + (x - 1) * cellSize;
        first.setStartX(_x);
        second.setStartX(_x);

        first.setEndX(_x + cellSize - 2 * margin);
        second.setEndX(_x + cellSize - 2 * margin);

        final int _y = borderSize + margin + (y - 1) * cellSize;
        first.setStartY(_y);
        second.setEndY(_y);

        first.setEndY(_y + cellSize - 2 * margin);
        second.setStartY(_y + cellSize - 2 * margin);

        first.setVisible(true);
        second.setVisible(true);
    }

    @Data
    @AllArgsConstructor
    private class Cross {
        private final Line first;
        private final Line second;
    }

    @Data
    @AllArgsConstructor
    private class Circle {
        private final javafx.scene.shape.Circle circle;
    }
}
