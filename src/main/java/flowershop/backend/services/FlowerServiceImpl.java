package flowershop.backend.services;

import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.repository.FlowersDaoCustom;
import flowershop.frontend.dto.Flower;
import org.dozer.Mapper;
import org.jooq.generated.tables.pojos.Flowers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);
    @Autowired
    private FlowersDaoCustom flowersDao;

    @Autowired
    private Mapper mapper;

    @PostConstruct
    public void initialize() {
        LOG.info("Flower service on");
    }

    @Override
    public void create(Flower flower) throws FlowerValidationException {
        validate(flower);

        flowersDao.insert(mapper.map(flower, Flowers.class));
        LOG.info("Flower created: {}", flower);
    }

    @Override
    public void update(Flower flower) throws FlowerValidationException {
        validate(flower);

        flowersDao.update(mapper.map(flower, Flowers.class));
        LOG.info("Flower updated: {}", flower);
    }

    @Override
    public void delete(Long id) {
        flowersDao.deleteById(id);
        LOG.info("Flower with id {} deleted", id);
    }

    @Override
    public Flower find(Long id) {
        return mapper.map(flowersDao.fetchOneById(id), Flower.class);
    }

    public List<Flower> getAll() {
        List<Flower> flowers = new LinkedList<>();

        for (Flowers e : flowersDao.findAll())
            flowers.add(mapper.map(e, Flower.class));

        return flowers;
    }

    @Override
    public List<Flower> getAll(String name, BigDecimal priceMin, BigDecimal priceMax, String sort, String order) {

        List<Flowers> entities = flowersDao.findAll(name, priceMin, priceMax, sort, order);

        List<Flower> flowers = new LinkedList<>();

        for (Flowers e : entities)
            flowers.add(mapper.map(e, Flower.class));

        return flowers;
    }

    private void validate(Flower flower) throws FlowerValidationException {
        if (flower.getName() == null)
            flower.setName("Unnamed");

        if (flower.getPrice() == null)
            flower.setPrice(BigDecimal.ZERO);
        if (flower.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new FlowerValidationException(FlowerValidationException.NEGATIVE_PRICE);

        if (flower.getCount() == null)
            flower.setCount(0);
        if (flower.getCount() < 0)
            throw new FlowerValidationException(FlowerValidationException.NEGATIVE_COUNT);
    }
}
