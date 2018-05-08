package top.renote.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.service.comment.CommentService;
import top.renote.service.note.NoteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Joder_X on 2018/2/13.
 * 拦截/notes/{noteId}/comments或/notes/{noteId}/comments/{commentId}/child_comments
 * /comments/*
 * 判断noteId是否分享或公开，commentId是否存在
 */
@Slf4j
public class CommentInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CommentService commentService;
    @Autowired
    private NoteService noteService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("{}执行CommentInterceptor",request.getRemoteAddr());
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        log.info("CommentInterceptor is :{}",map==null);
        Long userId = null;
        if (request.getAttribute("userId")!=null){
            userId = (Long) request.getAttribute("userId");
        }
        Long noteId = null,commentId = null;
        if (map.containsKey("noteId")){
            noteId = Long.valueOf((String) map.get("noteId"));
        }if (map.containsKey("commentId")){
            commentId = Long.valueOf((String) map.get("commentId"));
        }
        if (noteId!=null){
            if (!isNoteShared(noteId)){
                InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
                return false;
            }if (commentId!=null&&!isBelongNote(noteId,commentId)){
                InterceptionUtil.errorRedirect(response,"访问非法资源...",403);
                return false;
            }
            return true;
        }else {
            if (userId!=null&&commentId!=null&&isBelongUser(userId,commentId)){
                return true;
            }
        }
        InterceptionUtil.errorRedirect(response,"访问非法资源...",403);
        return false;
    }

    private boolean isBelongUser(Long userId, Long commentId) {
        return commentService.isBelongUser(userId,commentId);
    }

    /**
     * 判断commentId是否为noteId笔记
     * @param noteId
     * @param commentId
     * @return
     */
    private boolean isBelongNote(Long noteId, Long commentId) {
        return commentService.isBelongNote(noteId,commentId);
    }

    /**
     * 判断笔记是否分享或公开
     * @param noteId
     * @return
     */
    private boolean isNoteShared(Long noteId) {
        return noteService.isVisit(noteId);
    }

}
