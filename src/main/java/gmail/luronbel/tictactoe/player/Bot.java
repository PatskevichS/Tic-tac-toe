package gmail.luronbel.tictactoe.player;

import gmail.luronbel.tictactoe.component.Game;
import gmail.luronbel.tictactoe.component.Position;

/**
 * Bot.
 *
 * @author Stas_Patskevich
 */
public class Bot implements Player {
    private Game game;

    @Override
    public void yourTurn() {
        final boolean currentPlayer = game.isCurrentPlayer();
        final Position winPosition = game.findWinPosition(currentPlayer);
        if (winPosition != null) {
            game.makeTurn(winPosition.getY(), winPosition.getX());
            game.unblock();
            return;
        }
        final Position enemyPosition = game.findWinPosition(!currentPlayer);
        if (enemyPosition != null) {
            game.makeTurn(enemyPosition.getY(), enemyPosition.getX());
            game.unblock();
            return;
        }

        final Position randomPosition = game.getRandomPosition();
        game.makeTurn(randomPosition.getY(), randomPosition.getX());
    }

    @Override
    public void setGame(final Game game) {
        this.game = game;
    }
}
