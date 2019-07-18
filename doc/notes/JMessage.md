# 使用JMessage实现好友聊天功能

## [Android IM SDK 集成指南](https://docs.jiguang.cn/jmessage/client/jmessage_android_guide/)

[注册信息](https://www.jiguang.cn/dev/#/app/ff4a6be471a248b2325698d3/info)

appkey: ff4a6be471a248b2325698d3

## 使用的接口

### SDK初始化

```java
JMessageClient.init(Context context);
```

在Application类中调用

### 注册（可以放在后端进行）

```java
JMessageClient.register(String userName, String password, RegisterOptionalUserInfo optionalUserInfo, BasicCallback callback);
```

参数说明

+ ```String username``` 用户名
+ ```String password``` 用户密码
+ ```RegisterOptionalUserInfo optionalUserInfo``` 注册时的用户其他信息
+ ```BasicCallback callback``` 结果回调

### 登录

```java
JMessageClient.login(String username, String password, BasicCallback callback);
```

### 登出

```java
JMessageClient.logout();
```

### 修改密码

应该需要在后端进行

```java
JMessageClient.updateUserPassword(String oldPassword, String newPassword, BasicCallback callback);
```
