package gmail.luronbel.tictactoe.player;

import gmail.luronbel.tictactoe.component.Game;

/**
 * Bot.
 *
 * @author Stas_Patskevich
 */
public class Bot implements Player {
    private Game game;

    @Override
    public void yourTurn() {

    }

    @Override
    public void setGame(final Game game) {
        this.game = game;
    }
}
