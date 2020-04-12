package gmail.luronbel.tictactoe.component;

import static gmail.luronbel.tictactoe.component.ViewManager.VIEW_MANAGER_BEAN;
import static gmail.luronbel.tictactoe.layout.Header.HEADER_BEAN;
import static gmail.luronbel.tictactoe.layout.Indication.INDICATION_BEAN;
import static gmail.luronbel.tictactoe.utils.Engine.ENGINE_BEAN;

import gmail.luronbel.tictactoe.layout.Header;
import gmail.luronbel.tictactoe.layout.Indication;
import gmail.luronbel.tictactoe.player.Player;
import gmail.luronbel.tictactoe.utils.Engine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    public static final int RESET_TIMEOUT = 1300;

    @Autowired
    @Qualifier(VIEW_MANAGER_BEAN)
    private ViewManager viewManager;

    @Autowired
    @Qualifier(INDICATION_BEAN)
    private Indication indication;

    @Autowired
    @Qualifier(ENGINE_BEAN)
    private Engine engine;

    @Autowired
    @Qualifier(HEADER_BEAN)
    private Header header;

    @Setter
    private Player redPlayer;
    @Setter
    private Player bluePlayer;

    // 0 - NONE_PLAYER, 1 - RED_PLAYER, 2 - BLUE_PLAYER
    private final int[][] field = new int[3][3];
    private List<Position> positions = new ArrayList<>();
    // true - RED_PLAYER, false = BLUE_PLAYER
    @Getter
    private boolean currentPlayer = true;
    private final Random random = new Random();

    private boolean active = false;
    private boolean blocked = false;
    private boolean gameBeenStarted = false;
    private int winner = NONE_PLAYER;

    @PostConstruct
    public void init() {
        positions = new ArrayList<>();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                field[y][x] = NONE_PLAYER;
                positions.add(new Position(y + 1, x + 1));
            }
        }
        Collections.shuffle(positions);
    }

    public void start() {
        if (redPlayer == null || bluePlayer == null) {
            throw new IllegalStateException("Players are not initialized");
        }
        if (active) {
            throw new IllegalStateException("Game has been already started");
        } else {
            gameBeenStarted = true;
            redPlayer.setGame(this);
            bluePlayer.setGame(this);
            active = true;
            currentPlayer = true;
            indication.showRedInd();
            redPlayer.yourTurn();
        }
    }

    public void reset() {
        active = false;
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

    public void clickOnCell(final int y, final int x) {
        if (blocked) {
            return;
        }
        if (field[y - 1][x - 1] != NONE_PLAYER) {
            return;
        }
        makeTurn(y, x);
    }

    public void makeTurn(final int y, final int x) {
        blocked = true;
        if (!active) {
            return;
        }
        final int mark;
        if (currentPlayer) {
            viewManager.showCross(y, x);
            indication.showBlueInd();
            mark = RED_PLAYER;
        } else {
            viewManager.showCircle(y, x);
            indication.showRedInd();
            mark = BLUE_PLAYER;
        }
        field[y - 1][x - 1] = mark;

        final Position position = new Position(y, x);
        if (positions.contains(position)) {
            positions.remove(position);
        } else {
            throw new IllegalStateException(
                    "Position data does not contain this value {" + (y - 1) + "," + (x - 1) + "}");
        }

        currentPlayer = !currentPlayer;

        checkResult(y - 1, x - 1);
        checkWinner();

        if (currentPlayer) {
            redPlayer.yourTurn();
        } else {
            bluePlayer.yourTurn();
        }
    }

    private void checkResult(final int y, final int x) {
        winner = engine.checkResult(field, y, x);
    }

    private int checkWinner() {
        if (winner != NONE_PLAYER || positions.isEmpty()) {
            active = false;
            if (winner == RED_PLAYER) {
                System.out.println("Red player is winner!");
                header.addScoreTo(true);
                resetSession();
                return RED_PLAYER;
            } else if (winner == BLUE_PLAYER) {
                System.out.println("Blue player is winner!");
                header.addScoreTo(false);
                resetSession();
                return BLUE_PLAYER;
            } else {
                System.out.println("Draw!");
                resetSession();
            }
        }
        return NONE_PLAYER;
    }

    public void resetSession() {
        new Thread(() -> {
            try {
                Thread.sleep(RESET_TIMEOUT);
            } catch (final InterruptedException e) {
            }
            active = true;
            refresh();
        }).start();
    }

    public Position findWinPosition(final boolean player) {
        if (player) {
            return engine.findWinPosition(field, RED_PLAYER);
        } else {
            return engine.findWinPosition(field, BLUE_PLAYER);
        }
    }

    public Position getRandomPosition() {
        final int i = random.nextInt(positions.size());
        return positions.get(i);
    }

    public void unblock() {
        blocked = false;
    }

    public boolean hasGameBeenStarted() {
        return gameBeenStarted;
    }
}
