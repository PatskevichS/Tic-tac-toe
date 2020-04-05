package gmail.luronbel.tictactoe.configuration;


import javafx.stage.Stage;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Core.
 *
 * @author Stas_Patskevich
 */
@Component(Core.CORE_BEAN)
public class Core {
    public static final String CORE_BEAN = "core";

    public void start(@NonNull final Stage primaryStage) {

    }
}
