package jpa.starter.test;

import com.jpa.starter.repository.CommonRepository;
import jpa.starter.test.entity.UserEntity;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CommonRepository<UserEntity,Long> {
}
