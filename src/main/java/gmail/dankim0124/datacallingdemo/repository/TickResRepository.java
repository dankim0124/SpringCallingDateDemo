package gmail.dankim0124.datacallingdemo.repository;


import gmail.dankim0124.datacallingdemo.model.TickRes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickResRepository extends JpaRepository<TickRes,Long> {
    TickRes findBySequentialId(long l);
}
