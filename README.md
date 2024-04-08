## 快速入门：演示Spring Security表单登录

1. 访问GET /，提示需要登录，重定向到/login
2. 访问GET /login，获取到登录页面，里面存在一个用于提交用户名和密码的表单
3. 访问POST /login，根据提交的用户名和密码完成认证，认证成功后重定向到/index
4. 访问GET /index，获取到首页资源
5. 访问POST /logout，退出系统，清空回话，重定向到/index
6. 访问GET /index，由于没有认证，重定向到/login