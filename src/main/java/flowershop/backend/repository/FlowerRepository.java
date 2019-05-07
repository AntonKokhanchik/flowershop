package flowershop.backend.repository;

import flowershop.backend.entity.FlowerEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlowerRepository extends CrudRepository <FlowerEntity, Long> {
    @Override
    <S extends FlowerEntity> S save(S s);

    @Override
    Optional<FlowerEntity> findById(Long aLong);

    @Override
    List<FlowerEntity> findAll();

    @Override
    void deleteById(Long aLong);

    @Query("from FlowerEntity where lower(name) like lower(:name) and price < :price_max and price > :price_min")
    List<FlowerEntity> findByNameAndPriceSorted(@Param("name") String name,
                                                @Param("price_min") BigDecimal priceMin,
                                                @Param("price_max") BigDecimal priceMax,
                                                Sort sort);
}
