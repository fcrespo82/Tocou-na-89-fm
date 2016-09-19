package fcrespo82;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by fxcrespo on 14/09/16.
 */
@SpringBootApplication
@Log
@ComponentScan("fcrespo82")
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
public class RadioRockApplication extends Application {

    static String[] savedArgs;

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = SpringApplication.run(RadioRockApplication.class, savedArgs);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.stop();
    }
    public static void main(String[] args) {
        savedArgs = args;
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tocou na 89 fm");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/radioRockMain.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });    }
}
