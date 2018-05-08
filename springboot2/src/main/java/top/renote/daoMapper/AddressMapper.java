package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.SharedNoteAddress;

/**
 * Created by Joder_X on 2017/12/4.
 */
@Mapper
public interface AddressMapper {

    /**
     * 插入一条记录
     * @param address
     * @return
     */
    int insertOneAddress(SharedNoteAddress address);

    /**
     * 查看是否有重复的地址
     * @param address
     * @return
     */
    int getRepeatAddress(String address);

    /**
     * 删除地址
     * @param userId
     * @param noteId
     * @return
     */
    int deleteAddress(@Param("userId") Long userId, @Param("noteId") Long noteId);

    /**
     * 查看分享地址
     * @return
     */
    SharedNoteAddress getAddressByNote(@Param("userId") Long userId, @Param("noteId") Long noteId);

}
