

# Api
## 返回消息体
```json
{
"code":0,
"data":
    {
        "address": "",
        "sex": "",
        "token": "",
        "age": ,
        "email": "",
        "username": ""
   },
}
```


## /chat/pub
- uid: 用户id
- cid: 会话id
- message: 会话内容
##/chat/pub/historyMessage
-按页查询历史信息

##/user/login
##/user/register
=======





## controller
- User 用戶管理
- Chat 聊天

#### UserController
- 註冊
- 登錄
- 退出

- 狀態保持

#### ChatController
- 發佈私聊

## 表
- user
- user_chat_cid
- user_chat_message
#### user
```sql
create table user (
    id int nul
    email
    sex
    passwrod            
 
)

# SpringChat

