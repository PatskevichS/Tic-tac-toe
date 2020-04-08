package gmail.luronbel.tictactoe.configuration;

import static gmail.luronbel.tictactoe.component.Game.GAME_BEAN;
import static gmail.luronbel.tictactoe.layout.GameFieldLayout.GAME_FIELD_LAYOUT_BEAN;
import static gmail.luronbel.tictactoe.utils.PositionResolver.POSITION_RESOLVER_BEAN;

import gmail.luronbel.tictactoe.component.Game;
import gmail.luronbel.tictactoe.layout.GameFieldLayout;
import gmail.luronbel.tictactoe.utils.PositionResolver;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Core.
 *
 * @author Stas_Patskevich
 */
@Component(Core.CORE_BEAN)
public class Core {
    public static final String CORE_BEAN = "core";

    @Value("${app_name}")
    private String appName;

    @Value("${app_version}")
    private String appVersion;

    @Value("${window_height}")
    private int windowHeight;

    @Value("${window_width}")
    private int windowWidth;

    @Autowired
    @Qualifier(GAME_FIELD_LAYOUT_BEAN)
    private GameFieldLayout gameFieldLayout;

    @Autowired
    @Qualifier(GAME_BEAN)
    private Game game;

    @Autowired
    @Qualifier(POSITION_RESOLVER_BEAN)
    private PositionResolver positionResolver;

    private double xOffset = 0;
    private double yOffset = 0;

    public void start(@NonNull final Stage primaryStage) {
        final Scene mainScene = new Scene(gameFieldLayout, windowWidth, windowHeight);
        primaryStage.setTitle(appName + " " + appVersion);
        primaryStage.setMaxWidth(windowWidth + 5);
        primaryStage.setMaxHeight(windowHeight + 25);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        gameFieldLayout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            processClick();
        });
        gameFieldLayout.setOnMouseReleased(event -> {
            gameFieldLayout.hideModalView();
        });
        gameFieldLayout.setOnMouseDragged(event -> {
            if (!gameFieldLayout.isModalViewShown()) {
                gameFieldLayout.showModalView();
            }
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    private void processClick() {
        final int x = positionResolver.resolveX(xOffset);
        if (x != 0) {
            final int y = positionResolver.resolveY(yOffset);
            if (y != 0) {
                game.clickOnCell(y, x);
            }
        }
    }
}
