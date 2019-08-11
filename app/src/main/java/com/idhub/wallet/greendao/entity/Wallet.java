package com.idhub.wallet.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Wallet {
    @Id(autoincrement = true)
    private Long id;

    @Generated(hash = 1466299675)
    public Wallet(Long id) {
        this.id = id;
    }

    @Generated(hash = 1197745249)
    public Wallet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
