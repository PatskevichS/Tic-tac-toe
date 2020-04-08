package gmail.luronbel.tictactoe.component;

import static gmail.luronbel.tictactoe.component.ViewManager.VIEW_MANAGER_BEAN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Game.
 *
 * @author Stas_Patskevich
 */
@Component(Game.GAME_BEAN)
public class Game {
    public static final String GAME_BEAN = "game";

    @Autowired
    @Qualifier(VIEW_MANAGER_BEAN)
    private ViewManager viewManager;

    // 0 - empty, 1 - X, 2 - O
    private final int[][] field = new int[3][3];
    // true - X, false = O
    private boolean currentPlayer;

    @PostConstruct
    public void init() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                field[y][x] = 0;
            }
        }
        currentPlayer = true;
    }

    public void clickOnCell(final int y, final int x) {
        if (field[y - 1][x - 1] != 0) {
            return;
        }

        final int mark;
        if (currentPlayer) {
            viewManager.showCross(y, x);
            mark = 1;
        } else {
            viewManager.showCircle(y, x);
            mark = 2;
        }
        field[y - 1][x - 1] = mark;
        currentPlayer = !currentPlayer;
    }
}
