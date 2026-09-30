# Heritage Sharing Platform

基于 Spring Boot、MySQL 和 Redis 的文化遗产分享网站。Docker Compose 项目名为 `heritage-sharing-platform-web`。

## 本地启动

需要 Docker Desktop（或 Docker Engine）和 Docker Compose。使用 Docker 运行时，电脑上无需另装 Java、Maven、MySQL 或 Redis。

1. 复制 `.env.example` 为 `.env`，将 `DB_PASSWORD`、`DB_ROOT_PASSWORD` 和 `JWT_SECRET` 改成各自独立的值。`JWT_SECRET` 至少 32 个字符。`.env` 只保存在本机，不要提交到 Git。
2. 在项目根目录运行：

   ```bash
   docker compose up --build -d
   ```

3. 打开 <http://localhost:8080/>，会跳转到登录页。若修改了 `.env` 中的 `APP_PORT`，访问相应端口。

首次创建数据库卷时，MySQL 会自动导入 `heritage-sharing-platform.sql` 建表，应用随后初始化演示账户、分类和标签。已有数据库卷不会再次导入 SQL。

## 测试账户

在登录页使用**邮箱**和密码登录；管理员请选择管理员登录入口。

| 角色 | 用户名 | 登录邮箱 | 初始密码 |
| --- | --- | --- | --- |
| 普通用户 | `user` | `test@user.com` | `12345678` |
| 贡献者 | `contributor` | `test@contributor.com` | `12345678` |
| 管理员 | `admin1` | `admin1@admin.com` | `admin1` |
| 管理员 | `admin2` | `admin2@admin.com` | `admin2` |
| 管理员 | `admin3` | `admin3@admin.com` | `admin3` |

这些仅是本地演示账户。若已在数据库中修改过密码，重启容器不会重置密码。

## 常用命令

| 操作 | 命令 |
| --- | --- |
| 查看服务状态 | `docker compose ps` |
| 查看应用日志 | `docker compose logs -f app` |
| 更新代码后重建并启动 | `docker compose up --build -d` |
| 停止服务并保留数据 | `docker compose down` |
| 删除所有本地数据并重新初始化 | `docker compose down -v` |

MySQL、Redis 和上传文件分别保存在 Docker 卷中。`docker compose down -v` 会删除这些卷及其中的数据。

## 常见问题

- **页面打不开：**确认 Docker 正在运行，执行 `docker compose ps` 查看服务状态；检查 `APP_PORT` 是否被其他程序占用。
- **登录失败：**确认使用邮箱登录，并在管理员入口登录管理员账户；查看 `docker compose logs -f app`。
- **修改数据库密码后无法连接：**已有 MySQL 数据卷不会自动使用 `.env` 中的新密码。请改回原密码，或在确认不需要保留数据后运行 `docker compose down -v` 再启动。
- **更换 `JWT_SECRET` 后需要重新登录：**旧登录令牌会失效。

## 运行测试

安装 Java 21 或更新版本后，可在 Windows 使用 `mvnw.cmd test`，在 macOS/Linux 使用 `./mvnw test`。测试使用独立的 H2 内存数据库，不访问 Compose 的 MySQL。

## 配置与数据安全

真实数据库密码和 JWT 密钥只放在本地 `.env` 中；该文件、构建产物 `target/` 和上传文件 `uploads/` 均被 Git 忽略。`.env.example` 是占位模板。仓库中的测试账户密码仅供本地演示，不应用于公开部署。
