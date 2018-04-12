-- bank  脚本

--用户表
CREATE TABLE user
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100),
    password VARCHAR(100),
    email VARCHAR(100),
    enabled INT, --1 有效  0 禁止
    last_password_reset_date DATETIME,
    login_time DATETIME
);

--角色表
CREATE TABLE AUTHORITY
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    descn VARCHAR(100)
);

--用户表 - 角色表
CREATE TABLE USER_AUTHORITY
(
    user_id BIGINT,
    authority_id BIGINT,
    CONSTRAINT UA_USER_fk FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT UA_AUTHORITY_fk FOREIGN KEY (authority_id) REFERENCES authority (id)
);

--初始化数据
--角色表
INSERT INTO employee.authority (id, name, descn) VALUES (1, 'ROLE_ADMIN', '系统管理员');
INSERT INTO employee.authority (id, name, descn) VALUES (2, 'ROLE_MANAGER', '经理');
INSERT INTO employee.authority (id, name, descn) VALUES (3, 'ROLE_CLERK', '办事员');

--用户表
INSERT INTO employee.user (id, username, password, email, enabled, last_password_reset_date, login_time) VALUES (1, 'admin', '$2a$10$ZZ95yVjI9Ogz05ML5ZC41.q3nITCXavXCORkrD83JzdGjlVW1pil.', 'admin@email.com', 1, '2018-04-10 07:05:49', '2018-04-12 07:05:59');
INSERT INTO employee.user (id, username, password, email, enabled, last_password_reset_date, login_time) VALUES (2, 'king', '$2a$10$mtlQye7vy2LIwaH6GUWKNeWSAprtw0pZhTH/iJjBnuppTi5CPySFq', 'king@email.com', 1, '2018-04-11 07:07:36', '2018-04-12 07:07:42');
INSERT INTO employee.user (id, username, password, email, enabled, last_password_reset_date, login_time) VALUES (3, 'smith', '$2a$10$GzNUDCDKBEaNz.wXPNmzseUDvOxX4Rkn6mTV/R62qyz7N9QqU2vga', 'smith@email.com', 0, '2018-04-10 07:08:11', '2018-04-12 07:08:21');
INSERT INTO employee.user (id, username, password, email, enabled, last_password_reset_date, login_time) VALUES (4, 'jones', '$2a$10$i7Li.loblS6V1DRM.is6huOUZeajBiSKRSLx15y814YtuTdoZiRqy', 'jones@email.com', 1, '2018-04-09 07:08:46', '2018-04-12 07:08:52');
INSERT INTO employee.user (id, username, password, email, enabled, last_password_reset_date, login_time) VALUES (5, 'james', '$2a$10$SjktWci0SyMjIpP7hDiQTO47mUVDP8pOQQFmanPg6z0qV2BlyAe2S', 'james@email.com', 1, '2018-04-10 07:09:42', '2018-04-12 07:09:45');

--用户-角色表
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (4, 2);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (5, 3);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (1, 3);
INSERT INTO employee.user_authority (user_id, authority_id) VALUES (4, 3);