package top.renote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.renote.recommend.RenoteRecommend;

import java.sql.SQLException;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Project2ApplicationTests {

	@Autowired
	private RenoteRecommend renoteRecommend;


	@Test
	public void test1(){

	}

}
