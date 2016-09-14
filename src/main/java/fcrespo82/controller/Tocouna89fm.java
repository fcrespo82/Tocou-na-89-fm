package fcrespo82.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fcrespo82.model.RadioRockModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

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
    private ListView<String> listaMusicas;


    @FXML
    void getMusic(ActionEvent event) throws IOException {

        button.setText("Clicado");

        URL url = new URL("http://players.gc2.com.br/cron/89fm/results.json");

        String json = new Scanner(url.openStream()).useDelimiter("\\Z").next();

        System.out.println(json);

        RadioRockModel playing = new ObjectMapper().readValue(url.openStream(), RadioRockModel.class);

        System.out.println(playing);

        ObservableList musicas = listaMusicas.getItems();
        if (musicas == null) {
            musicas = FXCollections.observableArrayList();
        }

        musicas.add(playing.getMusicas().get(0).getTocando().get(0).getSong());

        listaMusicas.setItems(musicas);

        //listaMusicas.setCellFactory(ComboBoxListCell.forListView(musicas));

    }

}
