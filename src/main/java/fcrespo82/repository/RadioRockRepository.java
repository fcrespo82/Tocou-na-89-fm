package fcrespo82.repository;

import fcrespo82.model.RadioRockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fxcrespo on 19/09/16.
 */
@Repository
public interface RadioRockRepository extends JpaRepository<RadioRockModel, Long> {

    //List<RadioRockModel> findByMusica(String Musica);

}
