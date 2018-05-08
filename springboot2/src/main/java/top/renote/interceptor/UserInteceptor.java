package top.renote.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.service.note.NoteService;
import top.renote.service.notebook.NoteBookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Joder_X on 2018/1/16.
 * 拦截/[notebooks/{nbId}/[notes/{noteId}/**]]],
 * 即/user/**
 */
@Slf4j
public class UserInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private NoteBookService noteBookService;
    @Autowired
    private NoteService noteService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getAttribute("user")==null)return false;
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long nbId = null,noteId=null,anotherNb = null;

        if (map.containsKey("nbId"))
            nbId = Long.valueOf((String) map.get("nbId"));
        if (map.containsKey("noteId"))
            noteId = Long.valueOf((String) map.get("noteId"));
        if (map.containsKey("anotherNb"))
            anotherNb = Long.valueOf((String) map.get("anotherNb"));
        Long userId = (Long) request.getAttribute("userId");
        if(nbId!=null){
            if (!validateNbId(userId,nbId)) {
                InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
                return false;
            }
            if(noteId!=null){
                if (!validateNoteId(nbId,noteId)) {
                    InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
                    return false;
                }
            }
        }else if (noteId!=null&&anotherNb!=null){
            if (!validateNUId(userId,nbId)) {
                InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
                return false;
            }
            if (!validateNbId(userId,anotherNb)){
                InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
                return false;
            }
        }
        return true;
    }


    /**
     * note是否属于user
     * @param userId
     * @param noteId
     * @return
     */
    private boolean validateNUId(Long userId, Long noteId) {
        return noteService.isBelongUser(userId,noteId);
    }

    /**
     * 判断nbId是否拥有这个笔记noteId
     * @param nbId
     * @param noteId
     * @return
     */
    private boolean validateNoteId(Long nbId, Long noteId) {
        return noteService.isBelong(nbId,noteId);
    }

    /**
     * 判断hashId对应的account的用户是否拥有这个笔记本
     * @param userId
     * @param nbId
     * @return
     */
    private boolean validateNbId(Long userId,Long nbId) {
        return noteBookService.isBelong(userId,nbId);
    }


}
