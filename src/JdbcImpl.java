import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class JdbcImpl {
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("qwer");
        dataSource.setDatabaseName("elearning");
        //dataSource.setServerNames(new String[]{"127.0.0.1"});
        //dataSource.setPortNumbers(new int[] {6666});
        return dataSource;
    }
}
