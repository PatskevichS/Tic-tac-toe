package gmail.luronbel.tictactoe;

import static gmail.luronbel.tictactoe.configuration.Core.CORE_BEAN;

import gmail.luronbel.tictactoe.configuration.ContextConfig;
import gmail.luronbel.tictactoe.configuration.Core;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TicTacToeApp extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ContextConfig.class);
        context.getBean(CORE_BEAN, Core.class).start(primaryStage);
    }

}
