package flowershop.backend.services;

import flowershop.backend.dao.FlowerDAO;
import flowershop.backend.dto.Flower;
import flowershop.backend.entity.FlowerEntity;
import flowershop.backend.exception.FlowerValidationException;
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

    @PostConstruct
    public void initialize() {
        LOG.info("Flower service on");
    }

    @Override
    public void create(Flower flower) throws FlowerValidationException {
        validate(flower);
        flowerDAO.create(flower.toEntity());
    }

    @Override
    @Transactional
    public void update(Flower flower) throws FlowerValidationException {
        validate(flower);
        flowerDAO.update(flower.toEntity());
    }

    @Override
    @Transactional
    public void delete(Flower flower) {
        flowerDAO.delete(flower.toEntity());
    }

    @Override
    public Flower find(Long id) {
        return new Flower(flowerDAO.find(id));
    }

    @Override
    public List<Flower> getAll() {
        List<FlowerEntity> entities = flowerDAO.getAll();
        List<Flower> flowers = new LinkedList<>();

        for (FlowerEntity e : entities)
            flowers.add(new Flower(e));

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
