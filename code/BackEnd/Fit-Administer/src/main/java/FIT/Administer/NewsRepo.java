package FIT.Administer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepo extends JpaRepository<News,Integer> {

    Iterable<News> findAllByActive(Integer active);

}
