package fcrespo82.commandline;

import fcrespo82.model.RadioRockModel;
import fcrespo82.service.RadioRockService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fxcrespo on 20/09/16.
 */
@Log
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    RadioRockService service;

    Timer timer;

    @Override
    public void run(String... strings) throws Exception {

        if (!Arrays.asList(strings).contains("--gui")) {
            log.info("Command line running");

            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {

                            RadioRockModel song = service.downloadSong();
                            log.info(song.toString());
                        }
                    }, 0, 10 * 1000); // 10 segundos
        }
    }
}
