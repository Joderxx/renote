package top.renote.service.comment;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.CommentMapper;
import top.renote.daoMapper.RatingMapper;
import top.renote.daoMapper.UserMapper;
import top.renote.model.Note;
import top.renote.model.Rating;
import top.renote.model.SharedNoteComment;
import top.renote.model.User;
import top.renote.dto.CommentDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Joder_X on 2018/2/13.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RatingMapper ratingMapper;
    @Autowired
    private UserMapper userMapper;

    public List<CommentDTO> getUserComments(Long userId){
        List<SharedNoteComment> comments = commentMapper.getUserComments(userId);
        List<CommentDTO> list = new ArrayList<CommentDTO>(comments.size());
        for (SharedNoteComment comment:comments){
            CommentDTO commentDTO = new CommentDTO(comment,commentMapper.getCommentChildCount(comment.getCommentId()));
            list.add(commentDTO);
        }
        return list;
    }

    public boolean deleteComment(Long commentId){
        return commentMapper.deleteOne(commentId)>0;
    }

    public boolean isBelongUser(Long userId, Long commentId){
        return commentMapper.isBelongUser(userId,commentId)>0;
    }

    public boolean isBelongNote(Long noteId, Long commentId){
        return commentMapper.isBelongNote(noteId,commentId)>0;
    }

    public CommentDTO addComment(User user,Long noteId,Long parentId,String content){
        if (StringUtil.isEmpty(content))return null;
        Date date = new Date();
        SharedNoteComment comment = new SharedNoteComment(content,date,0,0,
                parentId,user,new Note(noteId));
        int num = commentMapper.addOne(comment);
        if (num>0) {
            CommentDTO commentDTO = new CommentDTO(comment, 0);
            commentDTO.setAvatar(user.getAvatar());
            commentDTO.setUsername(user.getUserName());
            addRating(user.getUserId(),noteId);
            return commentDTO;
        }
        return null;
    }

    public List<CommentDTO> getComments(Long noteId){
        List<SharedNoteComment> comments = commentMapper.getNoteComments(noteId);
        List<CommentDTO> list = new ArrayList<CommentDTO>(comments.size());
        for (SharedNoteComment comment:comments){
            list.add(new CommentDTO(comment,0));
        }
        return list;
    }

    public List<CommentDTO> getChildComments(Long commentId){
        List<SharedNoteComment> comments = commentMapper.getChildComments(commentId);
        List<CommentDTO> list = new ArrayList<CommentDTO>(comments.size());
        for (SharedNoteComment comment:comments){
            list.add(new CommentDTO(comment,0));
        }
        return list;
    }

    private void addRating(Long userId,Long noteId){
        Rating rating = ratingMapper.getOne(new Rating(null,null,userId,noteId));
        Double score = rating==null?3:rating.getPreference()+1;
        score = Math.min(5,score);
        if (rating==null){
            rating = new Rating(score,new Date().getTime(),userId,noteId);
            ratingMapper.insertOne(rating);
        }else {
            rating.setPreference(score);
            ratingMapper.updatePreference(rating);
        }
    }

    /**
     * 加1
     * @param like
     * @return
     */
    public int updateType(Integer like,Long commentId) {
        if (new Integer(1).equals(like)){
            return commentMapper.addLike(commentId);
        }else {
            return commentMapper.addDisLike(commentId);
        }
    }

}
