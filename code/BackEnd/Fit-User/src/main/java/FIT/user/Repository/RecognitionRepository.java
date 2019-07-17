package FIT.user.Repository;

import FIT.user.Entity.Recognition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecognitionRepository extends JpaRepository<Recognition,Integer> {

    Iterable<Recognition> findAllByTel(String tel);
}
