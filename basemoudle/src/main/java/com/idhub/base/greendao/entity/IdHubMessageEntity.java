package com.idhub.base.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class IdHubMessageEntity {
    @Id(autoincrement = true)
    private Long id;
    private String type;
    private String address;
    private String recoverAddress;
    private String defaultAddress;
    private String ein;
    private String time;
    @Generated(hash = 1419893984)
    public IdHubMessageEntity(Long id, String type, String address,
            String recoverAddress, String defaultAddress, String ein, String time) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.recoverAddress = recoverAddress;
        this.defaultAddress = defaultAddress;
        this.ein = ein;
        this.time = time;
    }
    @Generated(hash = 274278171)
    public IdHubMessageEntity() {
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
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRecoverAddress() {
        return this.recoverAddress;
    }
    public void setRecoverAddress(String recoverAddress) {
        this.recoverAddress = recoverAddress;
    }
    public String getDefaultAddress() {
        return this.defaultAddress;
    }
    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
    public String getEin() {
        return this.ein;
    }
    public void setEin(String ein) {
        this.ein = ein;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }


}
