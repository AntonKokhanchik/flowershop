package flowershop.starter;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.castor.CastorMarshaller;

import java.util.List;

@Configuration
@ConditionalOnClass(XMLConverter.class)
public class CustomAutoConfiguration {

    @Autowired
    ConfigurableApplicationContext applicationContext;

    @ConditionalOnMissingBean
    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapper mapper() {
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(List.of("classPath:dozer.xml"));
        return dozerBean;
    }

    @Bean
    public CastorMarshaller castorMarshaller() {
        CastorMarshaller castorMarshaller = new CastorMarshaller();
        castorMarshaller.setMappingLocation(applicationContext.getResource("classPath:mapping.xml"));
        return castorMarshaller;
    }

    @ConditionalOnMissingBean
    @Bean
    public XMLConverter XMLConverter() {
        XMLConverter convertor = new XMLConverter();
        CastorMarshaller marshaller = castorMarshaller();
        convertor.setMarshaller(marshaller);
        convertor.setUnmarshaller(marshaller);
        return convertor;
    }
}
