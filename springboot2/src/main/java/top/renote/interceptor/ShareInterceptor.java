package top.renote.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.service.note.NoteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Joder_X on 2018/2/13.
 * 拦截/user/notes/{noteId}/share 判断noteId是否为自己所有
 */
public class ShareInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private NoteService noteService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long userId = null;
        if (request.getAttribute("userId")!=null)
            userId = (Long) request.getAttribute("userId");
        Long noteId = null;
        if (map.containsKey("noteId")){
            noteId = Long.valueOf((String) map.get("noteId"));
        }else {
            InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
            return false;
        }
        if (!noteService.isBelongUser(userId,noteId)){
            InterceptionUtil.errorRedirect(response,"URL错误或资源不存在...",404);
            return false;
        }
        return true;
    }
}
