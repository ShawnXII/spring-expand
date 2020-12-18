import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.spring.expand.ExpandApplication;
import com.github.spring.expand.data.search.Searchable;
import com.github.spring.expand.login.LoginConfigAdapter;
import com.github.spring.expand.login.config.LoginConfig;
import com.github.spring.expand.test.TestEntity;
import com.github.spring.expand.test.TestRepository;
import com.github.spring.expand.util.SpringBeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wx
 * @date 2020/12/11 16:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExpandApplication.class})
public class TestSearch {

    @Autowired
    private TestRepository testRepository;


    @Test
    public void search() throws JsonProcessingException {
        Searchable searchable = Searchable.of(TestEntity.class).eq("key1", "1").or().eq("key2", "2").or().eq("name", "").and().eq("key3", "3").eq("testBEntity.name", "k2");
        List<TestEntity> list = testRepository.findAll(searchable.build().toSpecification());
        System.out.println(list);
        LoginConfigAdapter adapter = LoginConfigAdapter.getInstance().protocol().token().end().addRoot("wx","wangxiang386");
        System.out.println(adapter);
        System.out.println(SpringBeanUtils.getBean(LoginConfig.class));
    }

}
