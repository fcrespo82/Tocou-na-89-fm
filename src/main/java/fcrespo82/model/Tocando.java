package fcrespo82.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by fxcrespo on 14/09/16.
 */
public class Tocando {

    private List<Musica> tocando;

    @JsonIgnore
    private Object jatocou;

    public List<Musica> getTocando() {
        return tocando;
    }

    public void setTocando(List<Musica> tocando) {
        this.tocando = tocando;
    }

    public Object getJatocou() {
        return jatocou;
    }

    public void setJatocou(Object jatocou) {
        this.jatocou = jatocou;
    }
}
