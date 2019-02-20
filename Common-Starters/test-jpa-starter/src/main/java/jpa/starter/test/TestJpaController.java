package jpa.starter.test;

import com.jpa.starter.repository.CommonRepository;
import com.jpa.starter.repository.GeneralService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jpa.starter.test.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@Api(value = "",tags = {""})
public class TestJpaController {
    @Autowired
    private GeneralService generalService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    @Transactional
    public Object add(){
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setAge(1+i);
            userEntity.setPassword("ABCDEF"+i);
            userEntity.setSalt(UUID.randomUUID().toString());
            userEntity.setUsername(i % 2 == 0?"张三"+i:"李四"+i);
            users.add(userEntity);
        }
        return userRepository.saveAll(users);
    }
}
