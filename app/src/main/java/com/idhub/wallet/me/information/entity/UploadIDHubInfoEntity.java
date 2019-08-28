package com.idhub.wallet.me.information.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UploadIDHubInfoEntity {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String value;
    @Generated(hash = 1455862514)
    public UploadIDHubInfoEntity(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
    @Generated(hash = 1575712821)
    public UploadIDHubInfoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}
