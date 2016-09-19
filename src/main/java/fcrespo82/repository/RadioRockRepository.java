package fcrespo82.repository;

import fcrespo82.model.RadioRockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by fxcrespo on 19/09/16.
 */
@Repository
public interface RadioRockRepository extends JpaRepository<RadioRockModel, Long> {

    //List<RadioRockModel> findByMusica(String Musica);

}
