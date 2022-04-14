package gmail.dankim0124.datacallingdemo.interceptor;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

@Component
public class JpaTableNameInterceptor extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql){
        System.out.println("interceptor :  " +   sql);
        return sql;
    }
}
