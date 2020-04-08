package gmail.luronbel.tictactoe.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * PositionResolver.
 *
 * @author Stas_Patskevich
 */
@Component(PositionResolver.POSITION_RESOLVER_BEAN)
public class PositionResolver {
    public static final String POSITION_RESOLVER_BEAN = "positionResolver";

    private final int cellSize;
    private final int borderSize;
    private final int lineWidth;
    private final int margin;

    private final Position[][] positions;

    public PositionResolver(@Value("${cell_size}") final int cellSize,
                            @Value("${border_size}") final int borderSize,
                            @Value("${margin}") final int margin,
                            @Value("${line_width}") final int lineWidth) {
        this.borderSize = borderSize;
        this.cellSize = cellSize;
        this.margin = margin;
        this.lineWidth = lineWidth;

        positions = new Position[3][3];

        for (int y = 1; y <= 3; y++) {
            final int _y = borderSize + margin + (y - 1) * cellSize + (y - 1) * lineWidth;
            for (int x = 1; x <= 3; x++) {
                final int _x = borderSize + margin + (x - 1) * cellSize + (x - 1) * lineWidth;

                final Position position = new Position();
                position.leftX = _x;
                position.rightX = _x + cellSize - margin * 2;
                position.topY = _y;
                position.bottomY = _y + cellSize - margin * 2;
                positions[y - 1][x - 1] = position;
            }
        }
    }

    public int resolveX(final double positionX) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                final Position position = positions[y][x];
                if (position.leftX < positionX && positionX < position.rightX) {
                    return x + 1;
                }
            }
        }
        return 0;
    }

    public int resolveY(final double positionY) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                final Position position = positions[y][x];
                if (position.topY < positionY && positionY < position.bottomY) {
                    return y + 1;
                }
            }
        }
        return 0;
    }

    private class Position {
        private int leftX;
        private int rightX;
        private int topY;
        private int bottomY;
    }
}
