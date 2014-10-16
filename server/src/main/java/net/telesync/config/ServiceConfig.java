package net.telesync.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;


@Configuration
@ComponentScan(basePackages = "net.telesync.service")
@EnableTransactionManagement
public class ServiceConfig {

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.sqlite.JDBC");
        dataSource.setJdbcUrl("jdbc:sqlite:database");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws IOException {
        InputStream schemaStream = getClass().getResourceAsStream("/db/init_schema.sql");
        String schema = IOUtils.toString(schemaStream);

        String[] queries = schema.split(";\n");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        for (String query : queries) {
            jdbcTemplate.execute(query);
        }

        return jdbcTemplate;
    }
}
