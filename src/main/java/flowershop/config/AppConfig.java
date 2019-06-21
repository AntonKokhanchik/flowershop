package flowershop.config;

import flowershop.backend.repository.FlowersDaoCustom;
import org.jooq.generated.tables.daos.OrderFlowersDao;
import org.jooq.generated.tables.daos.OrdersDao;
import org.jooq.generated.tables.daos.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ServletComponentScan(basePackages = {"flowershop.frontend.servlets"})
@ComponentScan(basePackages = {"flowershop"})
@PropertySource("classpath:database.properties")
public class AppConfig {
    // Jooq DAOs
    @Autowired
    org.jooq.Configuration configuration;
    @Bean
    FlowersDaoCustom flowersDao () {
        return new FlowersDaoCustom(configuration);
    }

    @Bean
    UsersDao usersDao () {
        return new UsersDao(configuration);
    }

    @Bean
    OrdersDao ordersDao () {
        return new OrdersDao(configuration);
    }

    @Bean
    OrderFlowersDao orderFlowersDao () {
        return new OrderFlowersDao(configuration);
    }

}
