package jpa.starter.test;

import com.jpa.starter.repository.GeneralService;
import io.swagger.annotations.Api;
import jpa.starter.test.entity.BookEntity;
import jpa.starter.test.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@Api(value = "", tags = {""})
public class TestJpaController {
    @Autowired
    private GeneralService generalService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    @Transactional
    public Object add() {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setAge(1 + i);
            userEntity.setPassword("ABCDEF" + i);
            userEntity.setSalt(UUID.randomUUID().toString());
            userEntity.setUsername(i % 2 == 0 ? "张三" + i : "李四" + i);
            List<BookEntity> books = new ArrayList<>();
            books.add(new BookEntity("三国演义" + i, "罗贯中" + i, "三国大战", 10.0));
            books.add(new BookEntity("西游记" + i, "吴承恩" + i, "反对封建", 10.0));
            books.add(new BookEntity("水浒传" + i, "施耐庵" + i, "落草为寇", 10.0));
            books.add(new BookEntity("红楼梦" + i, "曹雪芹" + i, "情商剧", 10.0));
            userEntity.setBooks(books);
            users.add(userEntity);
        }
        return userRepository.saveAll(users);
    }

    @PostMapping("/updateAllById")
    @Transactional
    public Object updateById(@RequestBody UserEntity userEntity) throws Exception{
        if (userEntity.getId() == null){
            throw new Exception("id 不能为空");
        }
        UserEntity user = userRepository.save(userEntity);
        return user;
    }

    @PostMapping("/updateFieldsById")
    @Transactional
    public Object updateFieldsById(@RequestBody UserEntity userEntity) throws Exception{
        if (userEntity.getId() == null){
            throw new Exception("id 不能为空");
        }
        return generalService.updateFieldsById(userEntity,UserEntity.class,userEntity.getId());
    }

}
