package fcrespo82.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by fxcrespo on 15/09/16.
 */
@Getter
@Setter
@NoArgsConstructor
public class MusicCellController {

    @FXML
    private ImageView image;

    @FXML
    private Label singer;

    @FXML
    private Label music;
}
