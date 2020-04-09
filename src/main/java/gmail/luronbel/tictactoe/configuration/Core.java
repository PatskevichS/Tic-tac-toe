package gmail.luronbel.tictactoe.configuration;

import static gmail.luronbel.tictactoe.component.Game.GAME_BEAN;
import static gmail.luronbel.tictactoe.layout.GameFieldLayout.GAME_FIELD_LAYOUT_BEAN;
import static gmail.luronbel.tictactoe.layout.Menu.MENU_BEAN;
import static gmail.luronbel.tictactoe.utils.PositionResolver.POSITION_RESOLVER_BEAN;
import static javafx.scene.input.KeyCode.ESCAPE;

import gmail.luronbel.tictactoe.component.Game;
import gmail.luronbel.tictactoe.layout.GameFieldLayout;
import gmail.luronbel.tictactoe.layout.Menu;
import gmail.luronbel.tictactoe.player.ActivePlayer;
import gmail.luronbel.tictactoe.utils.PositionResolver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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

    @Value("${header_size}")
    private int headerSize;

    @Autowired
    @Qualifier(GAME_FIELD_LAYOUT_BEAN)
    private GameFieldLayout gameFieldLayout;

    @Autowired
    @Qualifier(MENU_BEAN)
    private Menu menu;

    @Autowired
    @Qualifier(GAME_BEAN)
    private Game game;

    @Autowired
    @Qualifier(POSITION_RESOLVER_BEAN)
    private PositionResolver positionResolver;

    private double xOffset = 0;
    private double yOffset = 0;

    public void start(@NonNull final Stage primaryStage) {
        final Scene mainScene = new Scene(gameFieldLayout, windowWidth, windowHeight + headerSize);
        primaryStage.setTitle(appName + " " + appVersion);
        primaryStage.setMaxWidth(windowWidth + 5);
        primaryStage.setMaxHeight(windowHeight + 25 + headerSize);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        mainScene.setOnKeyPressed(event -> {
            final KeyCode k = event.getCode();
            if (k.equals(ESCAPE)) {
                if (!game.hasGameBeenStarted()) {
                    return;
                }

                if (gameFieldLayout.isModalViewShown()) {
                    gameFieldLayout.hideModalView();
                    menu.hide();
                } else {
                    gameFieldLayout.showModalView();
                    menu.show();
                }
            }
        });

        gameFieldLayout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            processClick();
        });
        gameFieldLayout.setOnMouseReleased(event -> {
            if (!menu.isShown()) {
                gameFieldLayout.hideModalView();
            }
        });
        gameFieldLayout.setOnMouseDragged(event -> {
            if (!gameFieldLayout.isModalViewShown()) {
                gameFieldLayout.showModalView();
            }
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        menu.setPlayerVsPlayerButtonAction(event ->
        {
            menu.hide();
            gameFieldLayout.hideModalView();
            game.reset();
            game.setBluePlayer(new ActivePlayer());
            game.setRedPlayer(new ActivePlayer());
            game.start();
        });
        menu.setExitButtonAction(event ->
        {
            primaryStage.close();
        });

        menu.show();
        gameFieldLayout.showModalView();
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
