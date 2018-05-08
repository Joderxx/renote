package top.renote.service.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.NoteContentMapper;

/**
 * Created by Joder_X on 2018/2/12.
 */
@Service
public class NoteContentService {

    @Autowired
    private NoteContentMapper noteContentMapper;

}
