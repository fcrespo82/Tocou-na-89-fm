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

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by fxcrespo on 14/09/16.
 */
public class Tocouna89fm {

    @FXML
    private Button button;

    @FXML
    private ListView<RadioRockModel> listaMusicas;

    @FXML
    void getMusic(ActionEvent event) throws IOException {

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

                //!"A RÁDIO ROCK".equals(playing.getCantor()) &&
                if (!musicas.contains(playing)) {

                    musicas.add(playing);
                    listaMusicas.setItems(musicas);

                    System.out.println(playing);
                }
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
