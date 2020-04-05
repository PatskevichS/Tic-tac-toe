package gmail.luronbel.tictactoe.configuration;

import static gmail.luronbel.tictactoe.component.GameEngine.GAME_ENGINE_BEAN;
import static gmail.luronbel.tictactoe.layout.GameFieldLayout.GAME_FIELD_LAYOUT_BEAN;

import gmail.luronbel.tictactoe.component.GameEngine;
import gmail.luronbel.tictactoe.layout.GameFieldLayout;
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
    @Qualifier(GAME_ENGINE_BEAN)
    private GameEngine gameEngine;

    public void start(@NonNull final Stage primaryStage) {
        final Scene mainScene = new Scene(gameFieldLayout, windowWidth, windowHeight);
        primaryStage.setTitle(appName + " " + appVersion);
        primaryStage.setMaxWidth(windowWidth);
        primaryStage.setMaxHeight(windowHeight + 28);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        gameEngine.init();
    }
}
