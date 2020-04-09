package gmail.luronbel.tictactoe.player;

import gmail.luronbel.tictactoe.component.Game;

/**
 * PLayer.
 *
 * @author Stas_Patskevich
 */
public interface Player {

    void yourTurn();

    void setGame(Game game);
}
