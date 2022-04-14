package gmail.dankim0124.datacallingdemo.repository;

import gmail.dankim0124.datacallingdemo.model.SocketTick;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocketTickRepository extends JpaRepository<SocketTick,Long> {

}
