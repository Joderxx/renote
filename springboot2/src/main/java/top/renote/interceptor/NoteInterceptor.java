package top.renote.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.daoMapper.ScanMapper;
import top.renote.model.User;
import top.renote.service.note.NoteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Joder_X on 2018/2/26.
 * 检查笔记是否公开，/notes/{noteId}
 */
@Slf4j
public class NoteInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private NoteService noteService;
    @Autowired
    private ScanMapper scanMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        log.info("NoteInterceptor is null:{}",map==null);
        Long noteId = null;
        if (map.containsKey("noteId")){
            noteId = Long.valueOf((String) map.get("noteId"));
        }
        log.info("noteId:{}",noteId);
        if (noteId!=null) {
            if (!noteService.isVisit(noteId)) {
                InterceptionUtil.errorRedirect(response, "URL错误或资源不存在...", 404);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long noteId = null;
        if (map.containsKey("noteId")){
            noteId = Long.valueOf((String) map.get("noteId"));
        }
        log.info("noteId:{}",noteId);
        if (noteId!=null) {
            User user = null;
            if (request.getAttribute("user")!=null)
                user= (User) request.getAttribute("user");
            if (user!=null){
                int i = scanMapper.getHasData(user.getUserId(),noteId);
                if (i>0){
                    scanMapper.updateTime(user.getUserId(),noteId);
                }else {
                    scanMapper.insertData(user.getUserId(),noteId);
                }
            }
        }
    }
}
