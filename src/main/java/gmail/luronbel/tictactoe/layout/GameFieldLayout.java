package gmail.luronbel.tictactoe.layout;

import static gmail.luronbel.tictactoe.component.Grid.GRID_BEAN;
import static gmail.luronbel.tictactoe.layout.Background.BACKGROUND_BEAN;
import static gmail.luronbel.tictactoe.layout.GameElementsGroup.GAME_ELEMENTS_BEAN;

import gmail.luronbel.tictactoe.component.Grid;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * GameFieldLayout.
 *
 * @author Stas_Patskevich
 */
@Component(GameFieldLayout.GAME_FIELD_LAYOUT_BEAN)
public class GameFieldLayout extends Pane {
    public static final String GAME_FIELD_LAYOUT_BEAN = "gameFieldLayout";

    public GameFieldLayout(@Qualifier(BACKGROUND_BEAN) final Background background,
                           @Qualifier(GAME_ELEMENTS_BEAN) final GameElementsGroup gameElementsGroup,
                           @Qualifier(GRID_BEAN) final Grid grid) {
        getChildren().add(background);
        getChildren().add(grid);
        getChildren().add(gameElementsGroup);
    }
}
