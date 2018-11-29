package fcrespo82.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fcrespo82.model.RadioRockModel;
import fcrespo82.service.RadioRockService;
import javafx.application.Platform;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by fxcrespo on 14/09/16.
 */
@Log
@Component
public class MainController {

    @FXML
    private Button button;

    @FXML
    private ListView<RadioRockModel> listaMusicas;

    @FXML
    private TextField nowPlaying;

    @Autowired
    RadioRockService service;

    Timer timer;

    Boolean pegandoMusicas = false;

    @FXML
    public void initialize() {

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
                    Platform.runLater(() -> {

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
                    });
                }
            }
        });
    }

    @FXML
    public void getMusic(ActionEvent event) {

        if (!pegandoMusicas) {
            pegandoMusicas = true;
            button.setText("Parar de pegar música");
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {

                            RadioRockModel song = service.downloadSong();
                            atualizarLista(null);
                            nowPlaying.setText(song.musicaECantor());

                        }
                    }, 0, 10 * 1000); // 2 minutos
        } else {
            button.setText("Pegar música");
            pegandoMusicas = false;
            timer.cancel();
        }

    }

    private void downloadSong() throws IOException {
        URL url = new URL("http://players.gc2.com.br/cron/89fm/results.json");

        String json = new Scanner(url.openStream()).useDelimiter("\\Z").next();

        RadioRockModel playing = new ObjectMapper().readValue(url.openStream(), RadioRockModel.class);
        playing.setDataTocada(LocalDateTime.now());

        List<RadioRockModel> found = service.findAll();

        if (found != null && !found.contains(playing) && !"A RÁDIO ROCK".equals(playing.getCantor())) {
            service.save(playing);
        }
        atualizarLista(null);
        nowPlaying.setText(playing.musicaECantor());

    }

    @FXML
    public void atualizarLista(ActionEvent event) {

        ObservableList<RadioRockModel> musicas = listaMusicas.getItems();
        if (musicas == null) {
            musicas = FXCollections.observableArrayList();
        }

        List<RadioRockModel> list = new ArrayList<>();

        service.findAll().forEach(list::add);

        Collections.reverse(list);

        musicas.setAll(list);

    }

    @FXML
    public void dedupe(ActionEvent event) {

        Set<RadioRockModel> tempSet = new HashSet<RadioRockModel>();
        for (RadioRockModel t : service.findAll()) {
            if (!tempSet.add(t)) {
                service.delete(t.getId());
            }
        }
    }

}
