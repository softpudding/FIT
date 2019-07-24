package FIT.user.Repository;

import FIT.user.Entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record,Integer> {

    Iterable<Record> findAllByTel(String tel);
}
