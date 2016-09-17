package fcrespo82.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fcrespo82.model.RadioRockModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by fxcrespo on 14/09/16.
 */
@Log
public class Tocouna89fm {

    @FXML
    private Button button;

    @FXML
    private ListView<RadioRockModel> listaMusicas;

    Timer timer;

    Boolean pegandoMusicas = false;

    @FXML
    void getMusic(ActionEvent event) throws IOException {

        if (!pegandoMusicas) {
            pegandoMusicas = true;
            button.setText("Parar de pegar música");
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {

                        @Override
                        public void run() {
                            carregaLista();
                            log.info("ping");
                        }
                    }, 0, 1*1000); // 2 minutos
        } else {
            button.setText("Pegar música");
            timer.cancel();
        }

    }

    private void carregaLista() {
        Task carregaMusica = new Task<RadioRockModel>() {

            @Override
            protected RadioRockModel call() throws Exception {
                URL url = new URL("http://players.gc2.com.br/cron/89fm/results.json");

                String json = new Scanner(url.openStream()).useDelimiter("\\Z").next();

                RadioRockModel playing = new ObjectMapper().readValue(url.openStream(), RadioRockModel.class);

                return playing;
            }

            @Override
            protected void succeeded() {
                RadioRockModel playing = getValue();

                ObservableList<RadioRockModel> musicas = listaMusicas.getItems();
                if (musicas == null) {
                    musicas = FXCollections.observableArrayList();
                }

                if (!"A RÁDIO ROCK".equals(playing.getCantor()) &&!musicas.contains(playing)) {

                    musicas.add(playing);
                    listaMusicas.setItems(musicas);


                }
                log.info(playing.toString());
            }
        };
        Thread t = new Thread(carregaMusica);
        t.setDaemon(true);
        t.start();


        listaMusicas.setCellFactory(lv -> new ListCell<RadioRockModel>() {
            private Node graphic;
            private MusicCellController controller;

            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/musicCell.fxml"));
                    graphic = loader.load();
                    controller = loader.getController();
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }


            @Override
            protected void updateItem(RadioRockModel model, boolean empty) {
                super.updateItem(model, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {

                    controller.getSinger().setText(model.getCantor());
                    controller.getMusic().setText(model.getMusica());
                    //controller.getImage().setImage(new Image(model.getCoverURL().toExternalForm()));

                    Task carregaImagem = new Task<Image>() {

                        @Override
                        protected Image call() throws Exception {
                            return new Image(model.getCoverURL().toExternalForm());
                        }

                        @Override
                        protected void succeeded() {
                            Image imagem = getValue();

                            controller.getImage().setImage(imagem);
                        }
                    };
                    Thread t = new Thread(carregaImagem);
                    t.setDaemon(true);
                    t.start();

                    setGraphic(graphic);
                }
            }
        });
    }

}
