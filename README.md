# IDHUB-基于以太坊开发的数字身份钱包
数字身份采用ERC1056和ERC1484聚合数字身份，通过钱包，用户可自主实现声明管理。  
IDHUB不存储我们客户的任何钱包数据或私钥。私钥存储在移动设备的安全存储器中，如果用户从钱包中删除，则私钥会自行删除。为了保护资金，创建了keystore加密私钥并进行备份。可以输入密码查看。
# 安装教程
1.[下载](https://developer.android.com/studio/) 并安装 Android Studio 

2.克隆代码  
>git clone https://github.com/idhub-did-plus/did-wallet-android.git  

3.添加配置文件 
>1.在did-wallet-android/config/src/main/ 目录下新建assets文件夹  
>2.在assets文件夹下新建config.properties文件  
>3.添加配置  
```
notes = ["*","*"]    //以太坊节点配置，默认app使用第一个配置的节点  
baseUrl = **       //网络请求的baseUrl  
buglyAppId = **  //配置bugly的appid  
tokens = [{"name": "*","symbol": "*","decimals": "*","type": "*","mainContractAddress":"*","ropstenContractAddress": ""},{*}]  //配置app中推荐的资产列表  (type:ERC20/ERC1400)
```
>（注意：配置文件如果某一项的配置出现换行的情况 需要在结尾加\\ ,可参考（https://blog.csdn.net/yin_jw/article/details/27107355）  
```
eg:  
notes = ["https://ropsten.infura.io","https://ropsten.infura.io"]  
baseUrl = http://127.0.0.1  
buglyAppId = 123456  
tokens = [{"name": "Tether USD","symbol": "USDT","decimals": "6","type": "ERC20","mainContractAddress":"0xdac17f958d2ee523a2206206994597c13d831ec7","ropstenContractAddress": ""},\  
 {"name": "Tether USD","symbol": "USDT","decimals": "6","type": "ERC20","mainContractAddress":"0xdac17f958d2ee523a2206206994597c13d831ec7","ropstenContractAddress": ""}]
```

4.Android Studio打开修改好配置的项目即可
# APK下载地址
http://api.stplatform.idhub.network/v1/did/wallet/idhub-v0.0.2.apk
# 版权说明  
该项目签署了MIT 授权许可，详情请参阅 LICENSE.md


