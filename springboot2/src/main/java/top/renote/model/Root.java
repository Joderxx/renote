package top.renote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table: root
 */
public class Root {

    public static final Root PUBLIC = new Root(1L,"public"),
            PRIVATE = new Root(2L,"private"),
            COLLECT = new Root(3L,"collect"),
            SHARED = new Root(4L,"shared");

    private Long rootId;//根id
    private String rootName;//根名
    @JsonIgnore
    private List<Notebook> notebooks;

    public Root() {
    }

    public Root(Long rootId, String rootName) {
        this.rootId = rootId;
        this.rootName = rootName;
    }

    public List<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    @Override
    public boolean equals(Object obj) {
        Root root = (Root) obj;
        if (obj==null||!(obj instanceof Root)||root.getRootName()==null)return false;
        return root.getRootName().equals(rootName);
    }
}

