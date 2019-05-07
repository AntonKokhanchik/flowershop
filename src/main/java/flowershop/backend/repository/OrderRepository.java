package flowershop.backend.repository;

import flowershop.backend.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository <OrderEntity, Long> {
    @Override
    <S extends OrderEntity> S save(S s);

    @Override
    Optional<OrderEntity> findById(Long aLong);

    @Override
    List<OrderEntity> findAll();

    @Override
    void deleteById(Long aLong);
}
