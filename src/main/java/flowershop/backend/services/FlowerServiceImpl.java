package flowershop.backend.services;

import flowershop.backend.dao.FlowerDAO;
import flowershop.frontend.dto.Flower;
import flowershop.backend.entity.FlowerEntity;
import flowershop.backend.exception.FlowerValidationException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);
    @Autowired
    private FlowerDAO flowerDAO;

    @Autowired
    private Mapper mapper;

    @PostConstruct
    public void initialize() {
        LOG.info("Flower service on");
    }

    @Override
    public void create(Flower flower) throws FlowerValidationException {
        validate(flower);

        flowerDAO.create(mapper.map(flower, FlowerEntity.class));
    }

    @Override
    @Transactional
    public void update(Flower flower) throws FlowerValidationException {
        validate(flower);
        flowerDAO.update(mapper.map(flower, FlowerEntity.class));
    }

    @Override
    @Transactional
    public void delete(Flower flower) {
        flowerDAO.delete(mapper.map(flower, FlowerEntity.class));
    }

    @Override
    public Flower find(Long id) {
        return mapper.map(flowerDAO.find(id), Flower.class);
    }

    @Override
    public List<Flower> getAll() {
        List<FlowerEntity> entities = flowerDAO.getAll();
        List<Flower> flowers = new LinkedList<>();

        for (FlowerEntity e : entities)
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
