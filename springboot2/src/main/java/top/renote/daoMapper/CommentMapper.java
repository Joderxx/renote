package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.SharedNoteComment;

import java.util.List;

/**
 * Created by Joder_X on 2017/12/2.
 */
@Mapper
public interface CommentMapper {

    /**
     * 返回某用户的评论
     * @param userId
     * @return
     */
    List<SharedNoteComment> getUserComments(Long userId);

    /**
     * 获得评论子评论数
     * @param commentId
     * @return
     */
    int getCommentChildCount(Long commentId);

    /**
     * 删除一条记录
     * @param commentId
     * @return
     */
    int deleteOne(Long commentId);

    int isBelongUser(@Param("userId") Long userId, @Param("commentId") Long commentId);

    int isBelongNote(@Param("noteId") Long noteId, @Param("commentId") Long commentId);

    int addOne(SharedNoteComment comment);

    List<SharedNoteComment> getNoteComments(Long noteId);

    List<SharedNoteComment> getChildComments(Long commentId);

    int addDisLike(Long commentId);

    int addLike(Long commentId);

    SharedNoteComment getCommentById(Long commentId);
}
