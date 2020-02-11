package com.idhub.base.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class IdentityEntity {
    @Id(autoincrement = true)
    private Long id;
    private String node;
    private String identityAddress;
    private String recoveryAddress;
    private String EIN;
    private boolean isAssociate;
    private boolean isDefaultAddress;

    @Generated(hash = 1492672445)
    public IdentityEntity(Long id, String node, String identityAddress,
            String recoveryAddress, String EIN, boolean isAssociate,
            boolean isDefaultAddress) {
        this.id = id;
        this.node = node;
        this.identityAddress = identityAddress;
        this.recoveryAddress = recoveryAddress;
        this.EIN = EIN;
        this.isAssociate = isAssociate;
        this.isDefaultAddress = isDefaultAddress;
    }
    @Generated(hash = 693220522)
    public IdentityEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNode() {
        return this.node;
    }
    public void setNode(String node) {
        this.node = node;
    }
    public String getRecoveryAddress() {
        return this.recoveryAddress;
    }
    public void setRecoveryAddress(String recoveryAddress) {
        this.recoveryAddress = recoveryAddress;
    }
    public String getEIN() {
        return this.EIN;
    }
    public void setEIN(String EIN) {
        this.EIN = EIN;
    }
    public String getIdentityAddress() {
        return this.identityAddress;
    }
    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }
    public boolean getIsAssociate() {
        return this.isAssociate;
    }
    public void setIsAssociate(boolean isAssociate) {
        this.isAssociate = isAssociate;
    }
    public boolean getIsDefaultAddress() {
        return this.isDefaultAddress;
    }
    public void setIsDefaultAddress(boolean isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

}
