package flowershop.backend.repository;

import flowershop.backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <UserEntity, String> {
    @Override
    <S extends UserEntity> S save(S s);

    @Override
    Optional<UserEntity> findById(String s);

    @Override
    List<UserEntity> findAll();

    @Override
    void deleteById(String s);
}
