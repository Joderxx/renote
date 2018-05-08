package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.Advice;

import java.util.List;

/**
 * Created by Joder_X on 2018/4/17.
 * 通知advice表
 */
@Mapper
public interface AdviceMapper {

    /**
     * int insertNoteAdvice();
     * int insertCommentAdvice();
     * int insertLikeNoteAdvice();
     * int insertLikeCommentAdvice();
     * 根据属性添加
     * @param advice
     * @return
     */
    int insertAdvice(Advice advice);

    /**
     * 多少条未通知消息
     * @param userId
     * @return
     */
    int countUnAdvice(Long userId);


    /**
     * 已通知，修改状态
     * @param userId
     * @return
     */
    int updateAdvice(Long userId);

    List<Advice> selectAllAdvice(Long userId);


}
