package gmail.dankim0124.datacallingdemo.repository;

import gmail.dankim0124.datacallingdemo.model.TickRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest // slice test : db 관련한 것만 가져옴
public class TickResRepositoryTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TickResRepository tickResRepository;

    @Test
    public void di(){

    }

    @Test
    public void readMetaDataFromDataSource(){
        try(Connection connection = dataSource.getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getURL());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void jpaTest(){
        TickRes tickRes = new TickRes();
        tickRes.setSequentialId(21321321313L);
        tickRes.setMarket("dummy market");

        TickRes newTick = tickResRepository.save(tickRes);

        TickRes getTick = tickResRepository.findBySequentialId(21321321313L);

        System.out.println(getTick);

        assertThat(21321321313L).isEqualTo(getTick.getSequentialId());
        assertThat(newTick).isNotNull();

    }



    /* hibernate sql
    create table btc_ticks (sequential_id bigint not null, ask_bid varchar(255), change_price double, market varchar(255), prev_closing_price double, timestamp bigint, trade_date_utc varchar(255), trade_price double, trade_time_utc varchar(255), trade_volume double, primary key (sequential_id))
     */

}