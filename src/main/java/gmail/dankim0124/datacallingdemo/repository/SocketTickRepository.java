package gmail.dankim0124.datacallingdemo.repository;

import gmail.dankim0124.datacallingdemo.model.SocketTick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketTickRepository extends JpaRepository<SocketTick,Long> {
}
