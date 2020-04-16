package gmail.luronbel.tictactoe.utils;

import static gmail.luronbel.tictactoe.component.Game.NONE_PLAYER;
import static gmail.luronbel.tictactoe.component.ViewManager.VIEW_MANAGER_BEAN;

import gmail.luronbel.tictactoe.component.Position;
import gmail.luronbel.tictactoe.component.ViewManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.IntUnaryOperator;

/**
 * Engine.
 *
 * @author Stas_Patskevich
 */
@Component(Engine.ENGINE_BEAN)
@RequiredArgsConstructor
public class Engine {
    public static final String ENGINE_BEAN = "engine";

    @Autowired
    @Qualifier(VIEW_MANAGER_BEAN)
    private ViewManager viewManager;

    public int checkResult(final boolean player, final int[][] field, final int y, final int x, final boolean drawLine) {
        final int mark = field[y][x];
        if (checkVerticalLine(field, mark, 0, x)) {
            if (drawLine) {
                viewManager.showVerticalLine(player, x + 1);
            }
            return mark;
        } else if (checkHorizontalLine(field, mark, y, 0)) {
            if (drawLine) {
                viewManager.showHorizontalLine(player, y + 1);
            }
            return mark;
        } else if (checkRightCornerLine(field, mark, y, x)) {
            if (drawLine) {
                viewManager.showRightCornerLine(player);
            }
            return mark;
        } else if (checkLeftCornerLine(field, mark, y, x)) {
            if (drawLine) {
                viewManager.showLeftCornerLine(player);
            }
            return mark;
        }
        return NONE_PLAYER;
    }

    public Position findWinPosition(final int[][] field, final int mark) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (field[y][x] == NONE_PLAYER) {
                    field[y][x] = mark;
                    final int result = checkResult(false, field, y, x, false);
                    field[y][x] = NONE_PLAYER;
                    if (result != NONE_PLAYER) {
                        return new Position(y + 1, x + 1);
                    }
                }
            }
        }
        return null;
    }


    boolean checkHorizontalLine(final int[][] field, final int mark, final int y, final int x) {
        if (x < 0 || x > 2) {
            return true;
        }
        return field[y][x] == mark && checkHorizontalLine(field, mark, y, x + 1);
    }

    boolean checkVerticalLine(final int[][] field, final int mark, final int y, final int x) {
        if (y < 0 || y > 2) {
            return true;
        }
        return field[y][x] == mark && checkVerticalLine(field, mark, y + 1, x);
    }

    boolean checkRightCornerLine(final int[][] field, final int mark, final int y, final int x) {
        if ((y == 0 && x != 0) || (y == 1 && x != 1) || (y == 2 && x != 2)) {
            return false;
        }
        return checkCorner(field, mark, y, x, xP -> xP - 1, yP -> yP - 1)
                && checkCorner(field, mark, y, x, xP -> xP + 1, yP -> yP + 1);
    }

    boolean checkLeftCornerLine(final int[][] field, final int mark, final int y, final int x) {
        if ((y == 0 && x != 2) || (y == 1 && x != 1) || (y == 2 && x != 0)) {
            return false;
        }
        return checkCorner(field, mark, y, x, xP -> xP + 1, yP -> yP - 1)
                && checkCorner(field, mark, y, x, xP -> xP - 1, yP -> yP + 1);
    }

    private boolean checkCorner(final int[][] field, final int mark, final int y, final int x,
                                final IntUnaryOperator xF, final IntUnaryOperator yF) {
        if (y < 0 || y > 2 || x < 0 || x > 2) {
            return true;
        }
        return field[y][x] == mark && checkCorner(field, mark, yF.applyAsInt(y), xF.applyAsInt(x), xF, yF);
    }
}
