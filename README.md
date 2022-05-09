<<<<<<< HEAD
#API
返回消息体
{
"code":0,
"data":{"address": "",
"sex": "",
"token": "",
"age": ,
"email": "",
"username": ""},
}

=======

# Api
## 返回消息体
```
{
"code":0,
"data":{},
}
```

>>>>>>> 193790be675fbfd80cc26d644c35b68f77ba3acf
## /chat/pub
- uid: 用户id
- cid: 会话id
- message: 会话内容
<<<<<<< HEAD
##/user/login
##/user/register
=======

## /user/login

## /user/register

>>>>>>> 193790be675fbfd80cc26d644c35b68f77ba3acf

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
```
# SpringChat

