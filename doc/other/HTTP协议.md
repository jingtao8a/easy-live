# Request
### 1.Request Line
METHOD URI VERSION 
> 也可以携带参数
```angular2html
localhost:7090/api/getAvatar/{userId}
localhost:7090/api/checkCode?type=0
```

### 2. Reuqest Header
- User-Agent:
- Accept:
- Cookie:

### 3. Request Body
##### 3.1 application/x-www-form-urlencoded
> 适用于简单的表单数据，比如常见的文本输入框、单选按钮、复选框组成的表单
```angular2html
username=user1&password=pass1
```

##### 3.2 multipart/form-data
> 主要用于包含文件上传的表单，是处理文件上传的标准编码格式，上传单个或多个文件，都可以很好地处理
```angular2html
--Boundary
Content - Disposition: form - data; name="user-name"
Content - Type: text/plain

John
--Boundary
Content - Disposition: form - data; name="user-age"
Content - Type: text/plain

30
--Boundary
Content - Disposition: form - data; name="user-avatar"; filename="avatar.jpg"
Content - Type: image/jpeg

[二进制文件数据部分]
--Boundary--
```

##### 3.3 application-json
```
{
    "user": {
        "name": "john",
        "password": "123456",
        "address": {
            "street": "123 Main St",
            "city": "Anytown",
            "state": "CA",
            "zip": "12345"
        }
    }
}
```

# Response
### 1. Response Line
VERSION CODE MESSAGE
- 1xx: 表示服务器已接收请求，客户端可以继续发送请求的其余部分
- 2xx: 表示请求被服务器成功接收
- 3xx: 301-请求资源已经永久移动到新位置,302-请求资源临时移动到新位置
- 4xx: 请求资源不在服务器上
- 5xx: 服务器内部错误

### 2. Response Header
- Content-Type:
- Content-Length:
- Set-Cookies:

### 3. Response Body
##### 3.1 HTML文档
##### 3.2 JSON 数据
##### 3.3 二进制数据(如图像、音频、视频) 
