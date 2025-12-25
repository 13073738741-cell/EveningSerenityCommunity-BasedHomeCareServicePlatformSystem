-- 创建数据库
CREATE DATABASE IF NOT EXISTS elderlycare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE elderlycare;

-- 插入默认用户（管理员）
INSERT INTO users (username, password, role, created_at, updated_at) 
VALUES ('admin', '111111', 'ADMIN', NOW(), NOW())
ON DUPLICATE KEY UPDATE username=username;