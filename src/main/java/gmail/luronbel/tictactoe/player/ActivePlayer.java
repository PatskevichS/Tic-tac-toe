package gmail.luronbel.tictactoe.player;

import gmail.luronbel.tictactoe.component.Game;

/**
 * ActivePlayer.
 *
 * @author Stas_Patskevich
 */
public class ActivePlayer implements Player {
    private Game game;

    @Override
    public void yourTurn() {
        game.unblock();
    }

    @Override
    public void setGame(final Game game) {
        this.game = game;
    }
}
