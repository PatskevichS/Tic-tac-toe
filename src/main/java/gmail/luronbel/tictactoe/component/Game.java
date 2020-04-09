package gmail.luronbel.tictactoe.component;

import static gmail.luronbel.tictactoe.component.ViewManager.VIEW_MANAGER_BEAN;
import static gmail.luronbel.tictactoe.layout.Indication.INDICATION_BEAN;

import gmail.luronbel.tictactoe.layout.Indication;
import gmail.luronbel.tictactoe.player.Player;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Game.
 *
 * @author Stas_Patskevich
 */
@Component(Game.GAME_BEAN)
public class Game {
    public static final String GAME_BEAN = "game";

    public static final int RED_PLAYER = 1;
    public static final int BLUE_PLAYER = 2;
    public static final int NONE_PLAYER = 0;
    @Autowired
    @Qualifier(VIEW_MANAGER_BEAN)
    private ViewManager viewManager;

    @Autowired
    @Qualifier(INDICATION_BEAN)
    private Indication indication;

    @Setter
    private Player redPlayer;
    @Setter
    private Player bluePlayer;

    // 0 - NONE_PLAYER, 1 - RED_PLAYER, 2 - BLUE_PLAYER
    private final int[][] field = new int[3][3];
    // true - RED_PLAYER, false = BLUE_PLAYER
    private boolean currentPlayer = true;

    private boolean isActive = false;
    private boolean isBlocked = false;
    private boolean gameBeenStarted = false;
    private int winner = NONE_PLAYER;

    @PostConstruct
    public void init() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                field[y][x] = NONE_PLAYER;
            }
        }
    }

    public void start() {
        if (redPlayer == null || bluePlayer == null) {
            throw new IllegalStateException("Players are not initialized");
        }
        if (isActive) {
            throw new IllegalStateException("Game has been already started");
        } else {
            gameBeenStarted = true;
            redPlayer.setGame(this);
            bluePlayer.setGame(this);
            isActive = true;
            currentPlayer = true;
            indication.showRedInd();
            redPlayer.yourTurn();
        }
    }

    public void reset() {
        isActive = false;
        redPlayer = null;
        bluePlayer = null;
        refresh();
    }

    public void refresh() {
        currentPlayer = true;
        winner = NONE_PLAYER;
        init();
        viewManager.reset();
    }

    public int clickOnCell(final int y, final int x) {
        if (isBlocked) {
            return NONE_PLAYER;
        }
        if (field[y - 1][x - 1] != NONE_PLAYER) {
            return NONE_PLAYER;
        }
        return makeTurn(y, x);
    }

    public int makeTurn(final int y, final int x) {
        isBlocked = true;
        if (!isActive) {
            return NONE_PLAYER;
        }
        final int mark;
        if (currentPlayer) {
            viewManager.showCross(y, x);
            indication.showBlueInd();
            mark = RED_PLAYER;
            bluePlayer.yourTurn();
        } else {
            viewManager.showCircle(y, x);
            indication.showRedInd();
            mark = BLUE_PLAYER;
            redPlayer.yourTurn();
        }
        field[y - 1][x - 1] = mark;
        currentPlayer = !currentPlayer;

        checkResult();
        return checkWinner();
    }

    private void checkResult() {
        if (field[0][0] != 0 && field[0][0] == field[0][1] && field[0][0] == field[0][2]) {
            winner = field[0][0];
        } else if (field[1][0] != 0 && field[1][0] == field[1][1] && field[1][0] == field[1][2]) {
            winner = field[1][0];
        } else if (field[2][0] != 0 && field[2][0] == field[2][1] && field[2][0] == field[2][2]) {
            winner = field[2][0];
        } else if (field[0][0] != 0 && field[0][0] == field[1][0] && field[0][0] == field[2][0]) {
            winner = field[0][0];
        } else if (field[0][1] != 0 && field[0][1] == field[1][1] && field[0][1] == field[2][1]) {
            winner = field[0][1];
        } else if (field[0][2] != 0 && field[0][2] == field[1][2] && field[0][2] == field[2][2]) {
            winner = field[0][2];
        } else if (field[0][0] != 0 && field[0][0] == field[1][1] && field[0][0] == field[2][2]) {
            winner = field[0][0];
        } else if (field[2][0] != 0 && field[2][0] == field[1][1] && field[2][0] == field[0][2]) {
            winner = field[2][0];
        }
    }

    private int checkWinner() {
        if (winner != NONE_PLAYER) {
            isActive = false;
            if (winner == RED_PLAYER) {
                System.out.println("Red player is winner!");
                return RED_PLAYER;
            } else {
                System.out.println("Blue player is winner!");
                return BLUE_PLAYER;
            }
        }
        return NONE_PLAYER;
    }

    public void unblock() {
        isBlocked = false;
    }

    public boolean hasGameBeenStarted() {
        return gameBeenStarted;
    }
}
