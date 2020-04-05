package gmail.luronbel.tictactoe.layout;

import javafx.scene.Group;
import org.springframework.stereotype.Component;

@Component(GameElementsGroup.GAME_ELEMENTS_BEAN)
public class GameElementsGroup extends Group {
    public static final String GAME_ELEMENTS_BEAN = "gameElements";
}
