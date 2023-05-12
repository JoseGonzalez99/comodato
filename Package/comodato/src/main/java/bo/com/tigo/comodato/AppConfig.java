package bo.com.tigo.comodato;

import bo.com.tigo.comodato.shared.util.AppConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Autowired
    private Environment env;


    /*
    * DataSource para la conexion de a base de datos
    * */
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("as400.db.driver"));
        dataSource.setUrl(env.getProperty("as400.db.url"));
        dataSource.setUsername(env.getProperty("as400.db.username"));
        dataSource.setPassword(env.getProperty("as400.db.password"));
        dataSource.setMaxActive(Integer.parseInt(Objects.requireNonNull(env.getProperty("as400.db.pool.max"))));
        dataSource.setMinIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("as400.db.pool.min"))));
        dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("as400.db.initialsize"))));
        dataSource.setMaxWait(Integer.parseInt(Objects.requireNonNull(env.getProperty("as400.db.timeout"))));
        dataSource.setValidationQueryTimeout(Integer.parseInt(Objects.requireNonNull(env.getProperty("as400.db.validation.query.timeout"))));
        dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(Objects.requireNonNull(env.getProperty("as400.db.timetolive"))));
        dataSource.setValidationQuery(env.getProperty("as400.db.script"));
        return dataSource;
    }

    @Bean
    public AppConfiguration getAppConfiguration(){
        AppConfiguration config = new AppConfiguration();
        config.setApiUrl(env.getProperty("api.url"));
        config.setAs400Ip(env.getProperty("as400.plex.ip"));
        config.setAs400User(env.getProperty("as400.plex.user"));
        config.setAs400Password(env.getProperty("as400.plex.pass"));
        config.setAs400SoTimeout(Integer.valueOf(Objects.requireNonNull(env.getProperty("as400.plex.socketTimeout"))));
        config.setAs400MaxInactivity(Integer.valueOf(Objects.requireNonNull(env.getProperty("as400.plex.maxInactivity"))));
        config.setAs400MaxConnections(Integer.valueOf(env.getProperty("as400.plex.maxConnections")));
        config.setAs400Message(env.getProperty("as400.db.driver"));
        config.setAs400ConnectionTimeout(Integer.valueOf(env.getProperty("as400.plex.connectionTimeout")));
        config.setAs400PretestConnection(Objects.equals(env.getProperty("as400.plex.pretestConnection"), "true"));
        /**/
        config.setInvoise_completionMethod(env.getProperty("invoice.completionMethod"));
        config.setInvoise_isMandatory(Boolean.valueOf(env.getProperty("invoice.isMandatory")));
        config.setInvoise_relatedEntity_href(env.getProperty("invoice.relatedEntity.href"));
        config.setInvoise_relatedEntity_name(env.getProperty("invoice.relatedEntity.name"));
        config.setInvoise_relatedEntity_role(env.getProperty("invoice.relatedEntity.role"));
        config.setInvoise_relatedEntity_type(env.getProperty("invoice.relatedEntity.type"));
        config.setInvoise_type(env.getProperty("invoice.type"));
        config.setInvoise_type(env.getProperty("invoice.baseType"));

       return config;
    }

}


