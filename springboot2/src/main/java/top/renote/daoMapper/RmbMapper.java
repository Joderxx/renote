package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.Rmb;

/**
 * Created by Joder_X on 2018/2/9.
 */
@Mapper
public interface RmbMapper {

    /**
     * 根据uuid和hashId获取rmb对象
     * @param hash
     * @param uuid
     * @return
     */
    Rmb getRmbWithHU(@Param("rmbHashId") String hash,
                     @Param("rmbUUID") String uuid);

    /**
     * 删除一条记录，可能是过期的记录删除
     * @param hash
     * @param uuid
     * @return
     */
    int deleteWithHU(@Param("rmbHashId") String hash,
                     @Param("rmbUUID") String uuid);

    int addOne(Rmb rmb);
}
