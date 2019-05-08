package flowershop.backend.repository;

import com.querydsl.core.types.Predicate;
import flowershop.backend.entity.FlowerEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowerRepository extends CrudRepository <FlowerEntity, Long>, QuerydslPredicateExecutor<FlowerEntity>
{
    @Override
    <S extends FlowerEntity> S save(S s);

    @Override
    Optional<FlowerEntity> findById(Long aLong);

    @Override
    List<FlowerEntity> findAll();

    @Override
    void deleteById(Long aLong);

    @Override
    Iterable<FlowerEntity> findAll(Predicate predicate, Sort sort);
}
