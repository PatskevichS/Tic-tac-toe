package gmail.luronbel.tictactoe.component;

import static gmail.luronbel.tictactoe.component.ViewManager.VIEW_MANAGER_BEAN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * GameEngine.
 *
 * @author Stas_Patskevich
 */
@Component(GameEngine.GAME_ENGINE_BEAN)
public class GameEngine {
    public static final String GAME_ENGINE_BEAN = "gameEngine";

    @Autowired
    @Qualifier(VIEW_MANAGER_BEAN)
    private ViewManager viewManager;

    public void init() {
        viewManager.showCross(1, 1);
        viewManager.showCross(1, 3);
        viewManager.showCross(2, 2);
        viewManager.showCross(3, 1);
        viewManager.showCross(3, 3);
    }
}
