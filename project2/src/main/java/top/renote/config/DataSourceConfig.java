package top.renote.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.sql.JDBCRatingDAO;
import org.grouplens.lenskit.data.sql.JDBCRatingDAOBuilder;
import org.grouplens.lenskit.knn.NeighborhoodSize;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.knn.user.UserUserItemScorer;
import org.grouplens.lenskit.transform.normalize.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Joder_X on 2018/3/14.
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String host;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    @Value("${recommend.neighborhood}")
    private int neighborhood;



    @Bean
    public Connection connection(){
        try {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setUser(username);
            dataSource.setJdbcUrl(host);
            dataSource.setPassword(password);
            dataSource.setDriverClass(driverClass);
            return dataSource.getConnection();
        }catch (SQLException e) {
            return null;
        }catch (PropertyVetoException e) {
            return null;
        }
    }

    @Bean
    public JDBCRatingDAO jdbcRatingDAO(){
        return builder().build(connection());
    }

    @Bean
    public JDBCRatingDAOBuilder builder(){
        JDBCRatingDAOBuilder builder = JDBCRatingDAO.newBuilder()
                .setTableName("ratings")
                .setUserColumn("user_id")
                .setItemColumn("note_id")
                .setRatingColumn("preference")
                .setTimestampColumn("create_time");
        return builder;
    }

    @Bean("itemConfig")
    public LenskitConfiguration itemConfig(){
        LenskitConfiguration config = new LenskitConfiguration();
        config.addComponent(jdbcRatingDAO());
        config.bind(ItemScorer.class).to(ItemItemScorer.class);
        config.bind (BaselineScorer.class, ItemScorer.class).to(UserMeanItemScorer.class);
        config.bind(UserMeanBaseline.class,ItemScorer.class).to(ItemMeanRatingItemScorer.class);
        config.bind(UserVectorNormalizer.class).to(BaselineSubtractingUserVectorNormalizer.class);
        config.set(NeighborhoodSize.class).to(neighborhood);
        return config;
    }

    @Bean("itemBuild")
    public LenskitRecommender itemBuild(){
        try {
            return LenskitRecommender.build(itemConfig());
        } catch (RecommenderBuildException e) {
            return null;
        }
    }

    @Bean("itemCF")
    public ItemRecommender itemCF(){
        return itemBuild().getItemRecommender();
    }

    @Bean("userConfig")
    public LenskitConfiguration userConfig(){
        LenskitConfiguration config = new LenskitConfiguration();
        config.addComponent(jdbcRatingDAO());
        config.bind(ItemScorer.class).to(UserUserItemScorer.class);
        config.bind (BaselineScorer.class, ItemScorer.class).to(UserMeanItemScorer.class);
        config.bind(UserMeanBaseline.class,ItemScorer.class).to(ItemMeanRatingItemScorer.class);
        config.within(UserVectorNormalizer.class).bind(UserVectorNormalizer.class).to(DefaultUserVectorNormalizer.class);
        config.within(UserVectorNormalizer.class).bind(VectorNormalizer.class).to(MeanVarianceNormalizer.class);
        config.set(NeighborhoodSize.class).to(neighborhood);
        return config;
    }

    @Bean("userBuild")
    public LenskitRecommender userBuild(){
        try {
            return LenskitRecommender.build(userConfig());
        } catch (RecommenderBuildException e) {
            return null;
        }
    }


    @Bean("userCF")
    public ItemRecommender userCF(){
        return userBuild().getItemRecommender();
    }
}
