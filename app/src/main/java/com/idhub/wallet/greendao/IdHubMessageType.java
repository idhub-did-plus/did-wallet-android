package com.idhub.wallet.greendao;

public class IdHubMessageType {
    public static final String CREATE_1056_Identity = "create_1056_identity";
    public static final String UPGRADE_1484_IDENTITY = "upgrade_1484_identity";
    public static final String IMPORT_1056_IDENTITY = "import_1056_identity";
    public static final String IMPORT_1484_IDENTITY = "import_1484_identity";//导入的时候无法直接存储recoveryAddress和ein.在历史页需要进行sharedPreference查询
    public static final String UPGRADE_ASSOCIATION_IDENTITY = "upgrade_association_identity";
    public static final String RECOVERY_IDENTITY = "recovery_identity";

}
