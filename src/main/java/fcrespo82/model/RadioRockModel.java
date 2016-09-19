package fcrespo82.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.java.Log;

import javax.persistence.*;
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
@EqualsAndHashCode(exclude = {"id", "coverURL"})
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RadioRockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String cantor;

    @Column
    private String musica;

    @Column
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
