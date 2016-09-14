package fcrespo82.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by fxcrespo on 14/09/16.
 */
public class RadioRockModel {

    private List<Tocando> musicas;

    @JsonIgnore
    private Object promocao;

    @JsonIgnore
    private String messagepromo;

    public List<Tocando> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Tocando> musicas) {
        this.musicas = musicas;
    }

    public Object getPromocao() {
        return promocao;
    }

    public void setPromocao(Object promocao) {
        this.promocao = promocao;
    }

    public String getMessagepromo() {
        return messagepromo;
    }

    public void setMessagepromo(String messagepromo) {
        this.messagepromo = messagepromo;
    }
}
