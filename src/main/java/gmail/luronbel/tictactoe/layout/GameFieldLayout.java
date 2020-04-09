package gmail.luronbel.tictactoe.layout;

import static gmail.luronbel.tictactoe.component.Grid.GRID_BEAN;
import static gmail.luronbel.tictactoe.layout.Background.BACKGROUND_BEAN;
import static gmail.luronbel.tictactoe.layout.GameElementsGroup.GAME_ELEMENTS_BEAN;
import static gmail.luronbel.tictactoe.layout.Header.HEADER_BEAN;
import static gmail.luronbel.tictactoe.layout.Indication.INDICATION_BEAN;
import static gmail.luronbel.tictactoe.layout.Menu.MENU_BEAN;

import gmail.luronbel.tictactoe.component.Grid;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * GameFieldLayout.
 *
 * @author Stas_Patskevich
 */
@Component(GameFieldLayout.GAME_FIELD_LAYOUT_BEAN)
public class GameFieldLayout extends Pane {
    public static final String GAME_FIELD_LAYOUT_BEAN = "gameFieldLayout";

    private final Rectangle modalView;
    private final Pane interactiveViews;

    public GameFieldLayout(@Qualifier(BACKGROUND_BEAN) final Background background,
                           @Qualifier(GAME_ELEMENTS_BEAN) final GameElementsGroup gameElementsGroup,
                           @Qualifier(GRID_BEAN) final Grid grid,
                           @Qualifier(INDICATION_BEAN) final Indication indication,
                           @Qualifier(MENU_BEAN) final Menu menu,
                           @Qualifier(HEADER_BEAN) final Header header,
                           @Value("${window_height}") final int windowHeight,
                           @Value("${window_width}") final int windowWidth,
                           @Value("${header_size}") final int headerSize) {
        modalView = new Rectangle(windowWidth, windowHeight + headerSize);
        modalView.setOpacity(0.7);
        modalView.setVisible(false);

        interactiveViews = new Pane();
        interactiveViews.getChildren().add(grid);
        interactiveViews.getChildren().add(indication);
        interactiveViews.getChildren().add(header);
        interactiveViews.getChildren().add(gameElementsGroup);

        getChildren().add(background);
        getChildren().add(interactiveViews);
        getChildren().add(modalView);
        getChildren().add(menu);
    }

    public void showModalView() {
        modalView.setVisible(true);
        interactiveViews.setEffect(new GaussianBlur());
    }

    public void hideModalView() {
        modalView.setVisible(false);
        interactiveViews.setEffect(null);
    }

    public boolean isModalViewShown() {
        return modalView.isVisible();
    }
}
