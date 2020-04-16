package gmail.luronbel.tictactoe.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
 * EngineTest.
 *
 * @author Stas_Patskevich
 */
public class EngineTest {

    final Engine engine = new Engine();

    @Test
    @Ignore
    public void checkResult() {
        final int[][] field = {{0, 0, 1},
                {0, 0, 0},
                {0, 0, 0}};

        final int result_1 = engine.checkResult(false, field, 0, 2, true);
        assertEquals(0, result_1);

        field[1][1] = 1;
        final int result_2 = engine.checkResult(false, field, 1, 1, true);
        assertEquals(0, result_2);

        field[2][0] = 1;
        final int result_3 = engine.checkResult(false, field, 2, 0, true);
        assertEquals(1, result_3);

    }

    @Test
    public void checkHorizontalLine() {
        final int[][] field = {{0, 0, 0},
                {0, 0, 0},
                {1, 1, 1}};

        final boolean result_1 = engine.checkHorizontalLine(field, 1, 2, 0);
        assertTrue(result_1);

        field[2][0] = 2;
        final boolean result_2 = engine.checkHorizontalLine(field, 1, 2, 0);
        assertFalse(result_2);
    }

    @Test
    public void checkVerticalLine() {
        final int[][] field = {{1, 0, 0},
                {1, 0, 0},
                {1, 0, 0}};

        final boolean result_1 = engine.checkVerticalLine(field, 1, 0, 0);
        assertTrue(result_1);

        field[0][0] = 2;
        final boolean result_2 = engine.checkVerticalLine(field, 1, 0, 0);
        assertFalse(result_2);
    }

    @Test
    public void checkRightCornerLine() {
        final int[][] field = {{1, 3, 1},
                {2, 1, 3},
                {1, 2, 1}};

        final boolean result_1 = engine.checkRightCornerLine(field, 1, 0, 0);
        assertTrue(result_1);

        final boolean result_2 = engine.checkRightCornerLine(field, 1, 1, 1);
        assertTrue(result_2);

        final boolean result_3 = engine.checkRightCornerLine(field, 1, 2, 2);
        assertTrue(result_3);

        final boolean result_4 = engine.checkRightCornerLine(field, 2, 1, 0);
        assertFalse(result_4);

        final boolean result_5 = engine.checkRightCornerLine(field, 3, 1, 2);
        assertFalse(result_5);
    }

    @Test
    public void checkLeftCornerLine() {
        final int[][] field = {{1, 3, 1},
                {3, 1, 2},
                {1, 2, 1}};

        final boolean result_1 = engine.checkLeftCornerLine(field, 1, 0, 2);
        assertTrue(result_1);

        final boolean result_2 = engine.checkLeftCornerLine(field, 1, 1, 1);
        assertTrue(result_2);

        final boolean result_3 = engine.checkLeftCornerLine(field, 1, 2, 0);
        assertTrue(result_3);

        final boolean result_4 = engine.checkLeftCornerLine(field, 2, 1, 2);
        assertFalse(result_4);

        final boolean result_5 = engine.checkLeftCornerLine(field, 3, 1, 0);
        assertFalse(result_5);
    }
}
