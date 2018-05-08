package top.renote.recommend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecentRecommendTest {

    @Autowired
    private RecentRecommend recentRecommend;

    @Test
    public void recommend() throws Exception {
        System.out.println(recentRecommend.recommend(8L));
    }

    @Autowired
    private NeighborRecommend recommend;

    public void test1(){
        System.out.println(recommend.recommend(6978L));
    }

}