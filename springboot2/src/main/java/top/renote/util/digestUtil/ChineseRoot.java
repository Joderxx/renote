package top.renote.util.digestUtil;

/**
 * Created by Joder_X on 2018/2/12.
 */
public enum  ChineseRoot {
    PUBLIC("公开笔记"),PRIVATE("私有笔记"),COLLECT("收藏笔记"),SHARED("分享笔记");

    private String value;

    ChineseRoot(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
