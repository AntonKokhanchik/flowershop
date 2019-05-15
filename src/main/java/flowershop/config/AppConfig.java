package flowershop.config;

import flowershop.backend.services.XMLConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.castor.CastorMarshaller;

import java.util.Arrays;

@Configuration
public class AppConfig {
    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapper mapper() {
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(Arrays.asList("dozerJdk8Converters.xml"));
        return dozerBean;
    }

    @Bean
    public CastorMarshaller castorMarshaller() {
        CastorMarshaller castorMarshaller = new CastorMarshaller();
        castorMarshaller.setMappingLocation(new ClassPathResource("mapping.xml", getClass().getClassLoader()));
        return castorMarshaller;
    }

    @Bean
    public XMLConverter XMLConverter() {
        XMLConverter convertor = new XMLConverter();
        CastorMarshaller marshaller = castorMarshaller();
        convertor.setMarshaller(marshaller);
        convertor.setUnmarshaller(marshaller);
        return convertor;
    }
}
