package gmail.luronbel.tictactoe.component;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Position.
 *
 * @author Stas_Patskevich
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Position {
    final int y;
    final int x;
}