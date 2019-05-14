package flowershop.backend.services;

import flowershop.backend.entity.QFlowerEntity;
import flowershop.backend.repository.FlowerRepository;
import flowershop.frontend.dto.Flower;
import flowershop.backend.entity.FlowerEntity;
import flowershop.backend.exception.FlowerValidationException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private FlowerRepository flowerRepository;

    @Autowired
    private Mapper mapper;

    @PostConstruct
    public void initialize() {
        LOG.info("Flower service on");
    }

    @Override
    public void create(Flower flower) throws FlowerValidationException {
        validate(flower);

        FlowerEntity savedFlower = flowerRepository.save(mapper.map(flower, FlowerEntity.class));
        LOG.info("Flower created: {}", savedFlower);
    }

    @Override
    @Transactional
    public void update(Flower flower) throws FlowerValidationException {
        validate(flower);

        FlowerEntity savedFlower = flowerRepository.save(mapper.map(flower, FlowerEntity.class));
        LOG.info("Flower updated: {}", savedFlower);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        flowerRepository.deleteById(id);
        LOG.info("Flower with id {} deleted", id);
    }

    @Override
    public Flower find(Long id) {
        return flowerRepository.findById(id).map(entity -> mapper.map(entity, Flower.class)).orElse(null);
    }

    public List<Flower> getAll() {
        List<Flower> flowers = new LinkedList<>();

        for (FlowerEntity e : flowerRepository.findAll())
            flowers.add(mapper.map(e, Flower.class));

        return flowers;
    }

    @Override
    public List<Flower> getAll(String name, BigDecimal priceMin, BigDecimal priceMax, String sort, String order) {
        QFlowerEntity flower = QFlowerEntity.flowerEntity;

        List<FlowerEntity> entities = (List<FlowerEntity>) flowerRepository.findAll(
                flower.name.containsIgnoreCase(name).and(flower.price.between(priceMin, priceMax)),
                Sort.by(Sort.Direction.fromString(order), sort)
        );

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
