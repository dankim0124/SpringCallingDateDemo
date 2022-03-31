package gmail.dankim0124.datacallingdemo.repository;


import gmail.dankim0124.datacallingdemo.model.RestTick;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TickResRepository extends JpaRepository<RestTick,Long> {
    RestTick findBySequentialId(long l);
}
