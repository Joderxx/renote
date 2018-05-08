package top.renote.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.RmbMapper;
import top.renote.model.Rmb;

import java.util.Date;

/**
 * Created by Joder_X on 2018/2/11.
 */
@Service
public class RmbService {

    private static final long month = 2592000000L ;//一个月

    @Autowired
    private RmbMapper rmbMapper;

    /**
     * 根据hashId和uuid获得id,过期则删除
     * @param rmbHshId
     * @param rmbUUID
     * @return
     */
    public Long isExist(String rmbHshId,String rmbUUID){
        Rmb rmb = rmbMapper.getRmbWithHU(rmbHshId,rmbUUID);
        Date gmtCreate = new Date(rmb.getGmtCreate().getTime()+month);
        Date now = new Date();
        if (gmtCreate.after(now)){
            rmbMapper.deleteWithHU(rmbHshId,rmbUUID);
            return null;
        }
        return rmb.getUserId();
    }

    public boolean addRmb(Rmb rmb){
        return rmbMapper.addOne(rmb)>0;
    }
}
