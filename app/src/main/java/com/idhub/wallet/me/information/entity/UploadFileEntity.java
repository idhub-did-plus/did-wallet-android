package com.idhub.wallet.me.information.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UploadFileEntity {
    @Id(autoincrement = true)
    private Long id;
    private String type = "";
    private String name = "";
    private String filePath = "";
    @Generated(hash = 1785219618)
    public UploadFileEntity(Long id, String type, String name, String filePath) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.filePath = filePath;
    }
    @Generated(hash = 1994279916)
    public UploadFileEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFilePath() {
        return this.filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
}
