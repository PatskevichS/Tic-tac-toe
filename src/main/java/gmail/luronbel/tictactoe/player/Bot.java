package gmail.luronbel.tictactoe.player;

import gmail.luronbel.tictactoe.component.Game;
import gmail.luronbel.tictactoe.component.Position;

/**
 * Bot.
 *
 * @author Stas_Patskevich
 */
public class Bot implements Player {
    private static final int WAIT_TIMEOUT = 1000;
    private Game game;

    @Override
    public void yourTurn() {
        new Thread(() -> {
            try {
                Thread.sleep(WAIT_TIMEOUT);
            } catch (final InterruptedException e) {
            }
            final boolean currentPlayer = game.isCurrentPlayer();
            final Position winPosition = game.findWinPosition(currentPlayer);
            if (winPosition != null) {
                game.unblock();
                game.makeTurn(winPosition.getY(), winPosition.getX());
                return;
            }
            final Position enemyPosition = game.findWinPosition(!currentPlayer);
            if (enemyPosition != null) {
                game.unblock();
                game.makeTurn(enemyPosition.getY(), enemyPosition.getX());
                return;
            }

            final Position randomPosition = game.getRandomPosition();
            game.unblock();
            game.makeTurn(randomPosition.getY(), randomPosition.getX());
        }).start();

    }

    @Override
    public void setGame(final Game game) {
        this.game = game;
    }
}
