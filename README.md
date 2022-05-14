# controller
- User 用戶管理
- Chat 聊天

## UserController
- 註冊
- 登錄
- 退出
- 狀態保持

###/user/login

###/user/register


## ChatController
- 發佈私聊
- 查看历史记录
- 查看未读信息

### /chat/pub

- uid: 用户id
- cid: 会话id
- message: 会话内容

###/chat/pub/historyMessage
-按页查询历史信息

###/chat/pub/unreadMessage
-查看未读信息


## 表
- user
- user_chat_cid
- user_chat_message
###user
```
{
    private Integer id;
    private String email;
    private String sex;
    private String username;
    private String password;
    private String address;
    private Integer age;
    private String token;
}
```
###user_chat_cid
```
{
@TableId(value = "cid",type = IdType.AUTO)
private Integer cid;
private Integer uid_a;
private Integer uid_b;
private String create_time;

}
```
###user_chat_message
```
 {
    private Integer id;
    private Integer fromUid;
    private Integer toUid;
    private Integer cid;
    private String text;
    private String sendTime;
    private Integer readState;


}
```
# Api

### 登录返回消息体
```
{
"code":0,
"data":{"address": "",
"sex": "",
"token": "",
"age": ,
"email": "",
"username": ""},
}
```
###翻页传入信息
```
{
"fromUid":"",
"toUid":"",
"current":"",
"size":""

}
```
### 翻页传回信息
```
{
    "records": [
    ],
    "total": ,
    "size": ,
    "current": ,
    "orders": [],
    "optimizeCountSql": true,
    "hitCount": false,
    "countId": null,
    "maxLimit": null,
    "searchCount": true,
    "pages": 
}
```


# SpringChat

