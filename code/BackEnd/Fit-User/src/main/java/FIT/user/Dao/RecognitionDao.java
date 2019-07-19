package FIT.user.Dao;


import FIT.user.Entity.Recognition;
import FIT.user.Repository.RecognitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecognitionDao {

    @Autowired
    private RecognitionRepository recognitionRepository;

    public Iterable<Recognition> findAllByTel(String tel) {
        return recognitionRepository.findAllByTel(tel);
    }

    public boolean save(Recognition recognition) {
        recognitionRepository.saveAndFlush(recognition);
        return true;
    }
}
