package top.renote.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.renote.daoMapper.AdviceMapper;
import top.renote.daoMapper.CommentMapper;
import top.renote.daoMapper.NoteMapper;
import top.renote.daoMapper.UserMapper;
import top.renote.dto.AdviceDTO;
import top.renote.model.Advice;
import top.renote.model.Note;
import top.renote.model.SharedNoteComment;
import top.renote.model.User;
import top.renote.util.digestUtil.AdviceString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2018/4/17.
 */
@Service
public class AdviceService {

    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NoteMapper noteMapper;

    public void adviceAuthor(Long userId,Long noteId,Long commentId,Long cId,String type){
        User user = userMapper.getUserByNId(noteId);
        Advice advice = new Advice(user.getUserId(),noteId,commentId,userId,cId,type);
        adviceMapper.insertAdvice(advice);
    }

    public int countUnAdvice(Long userId){
        return adviceMapper.countUnAdvice(userId);
    }

    /**
     * 获得用户所有通知，并取消所有通知提示
     * @param userId
     * @return
     */
    @Transactional
    public List<AdviceDTO> getAllAdvice(Long userId){
        adviceMapper.updateAdvice(userId);
        List<Advice> list = adviceMapper.selectAllAdvice(userId);
        List<AdviceDTO> ls = new ArrayList<>(list.size());
        for (Advice advice : list){
            User user = userMapper.getUserById(advice.getuId());
            AdviceDTO adviceDTO = new AdviceDTO(advice.getNoteId(),user.getUserName()
                    ,user.getAvatar(),advice.getGmtCreate(),advice.getType());
            Note note = noteMapper.getNoteById(advice.getNoteId());
            adviceDTO.setNoteName(note.getNoteName());
            if (AdviceString.LIKECOMMENT.toString().equals(advice.getType())
                    ||AdviceString.LIKENOTE.toString().equals(advice.getType())){
                SharedNoteComment comment = commentMapper.getCommentById(advice.getCommentId());
                adviceDTO.setComment(comment.getContent());
            }else if (AdviceString.NOTE.toString().equals(advice.getType())){
                SharedNoteComment comment = commentMapper.getCommentById(advice.getcId());
                adviceDTO.setC(comment.getContent());
                adviceDTO.setNoteName(note.getNoteName());
            }else {
                SharedNoteComment comment1 = commentMapper.getCommentById(advice.getCommentId());
                SharedNoteComment comment2 = commentMapper.getCommentById(advice.getcId());
                adviceDTO.setComment(comment1.getContent());
                adviceDTO.setC(comment2.getContent());
            }
            ls.add(adviceDTO);
        }
        return ls;
    }
}
