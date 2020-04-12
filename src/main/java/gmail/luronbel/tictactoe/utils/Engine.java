package gmail.luronbel.tictactoe.utils;

import static gmail.luronbel.tictactoe.component.Game.NONE_PLAYER;

import gmail.luronbel.tictactoe.component.Position;
import org.springframework.stereotype.Component;

import java.util.function.IntUnaryOperator;

/**
 * Engine.
 *
 * @author Stas_Patskevich
 */
@Component(Engine.ENGINE_BEAN)
public class Engine {
    public static final String ENGINE_BEAN = "engine";

    public int checkResult(final int[][] field, final int y, final int x) {
        final int mark = field[y][x];
        if (checkVerticalLine(field, mark, 0, x)
                || checkHorizontalLine(field, mark, y, 0)
                || checkRightCornerLine(field, mark, y, x)
                || checkLeftCornerLine(field, mark, y, x)) {
            return mark;
        }
        return NONE_PLAYER;
    }

    public Position findWinPosition(final int[][] field, final int mark) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (field[y][x] == NONE_PLAYER) {
                    field[y][x] = mark;
                    final int result = checkResult(field, y, x);
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
