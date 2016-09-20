package fcrespo82.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fcrespo82.model.RadioRockModel;
import fcrespo82.repository.RadioRockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Created by fxcrespo on 20/09/16.
 */
@Service
public class RadioRockService {

    private static final String DONWLOAD_URL = "http://players.gc2.com.br/cron/89fm/results.json";

    @Autowired
    RadioRockRepository repository;

    public RadioRockModel downloadSong() {
        try {

            URL url = new URL(DONWLOAD_URL);

            String json = new Scanner(url.openStream()).useDelimiter("\\Z").next();

            RadioRockModel playing = new ObjectMapper().readValue(url.openStream(), RadioRockModel.class);
            playing.setDataTocada(LocalDateTime.now());

            List<RadioRockModel> found = repository.findAll();

            if (found != null && !found.contains(playing) && !"A RÁDIO ROCK".equals(playing.getCantor())) {
                repository.save(playing);
            }
            return playing;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RadioRockModel> findAll() {
        return repository.findAll();
    }

    public void save(RadioRockModel model) {
        repository.save(model);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
