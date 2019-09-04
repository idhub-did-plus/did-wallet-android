package com.idhub.wallet.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TransactionRecordEntity {
    @Id(autoincrement = true)
    private Long id;

}
