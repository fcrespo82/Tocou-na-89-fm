package fcrespo82.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by fxcrespo on 14/09/16.
 */
@Getter
@Setter
@ToString(includeFieldNames = true)
@Log
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RadioRockModel {

    private String cantor;

    private String musica;

    private URL coverURL;

    @JsonProperty("musicas")
    public void setMusicas(List<Map<String, Object>> musicas) {
        List<Map<String, String>> tocando = (List<Map<String, String>>) musicas.get(0).get("tocando");
        Map<String, String> firstResult = tocando.get(0);
        cantor = firstResult.get("singer");
        musica = firstResult.get("song");
        try {
            coverURL = new URL(firstResult.get("covermega"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
