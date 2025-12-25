-- 桑榆晚情社区居家养老服务平台系统 - 数据库初始化脚本
-- 此脚本会在程序启动时自动执行（如果数据库为空）

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建老人信息表
CREATE TABLE IF NOT EXISTS elderly_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    birth_date VARCHAR(50) NOT NULL,
    id_card VARCHAR(255) UNIQUE,
    phone VARCHAR(50),
    address VARCHAR(500),
    emergency_contact VARCHAR(255),
    emergency_phone VARCHAR(50),
    living_arrangement VARCHAR(255),
    medical_insurance VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    INDEX idx_id_card (id_card),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建健康评估表
CREATE TABLE IF NOT EXISTS health_assessment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elderly_id BIGINT NOT NULL,
    assessment_date VARCHAR(50) NOT NULL,
    adl_score INT,
    iadl_score INT,
    chronic_diseases TEXT,
    medication_list TEXT,
    mental_status VARCHAR(255),
    nutrition_status VARCHAR(255),
    fall_risk VARCHAR(255),
    assessor_name VARCHAR(255),
    notes TEXT,
    created_at DATETIME,
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_assessment_date (assessment_date),
    FOREIGN KEY (elderly_id) REFERENCES elderly_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建环境评估表
CREATE TABLE IF NOT EXISTS environment_assessment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elderly_id BIGINT NOT NULL,
    assessment_date VARCHAR(50) NOT NULL,
    housing_type VARCHAR(255),
    floor_level INT,
    safety_score INT,
    bathroom_safety VARCHAR(255),
    kitchen_safety VARCHAR(255),
    lighting VARCHAR(255),
    ventilation VARCHAR(255),
    accessibility VARCHAR(255),
    hazards TEXT,
    recommendations TEXT,
    assessor_name VARCHAR(255),
    created_at DATETIME,
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_assessment_date (assessment_date),
    FOREIGN KEY (elderly_id) REFERENCES elderly_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建关爱档案表
CREATE TABLE IF NOT EXISTS care_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elderly_id BIGINT NOT NULL,
    create_date VARCHAR(50) NOT NULL,
    care_level VARCHAR(255),
    daily_care_needs TEXT,
    medical_care_needs TEXT,
    social_support VARCHAR(255),
    emergency_plan TEXT,
    caregiver_name VARCHAR(255),
    caregiver_phone VARCHAR(50),
    next_review_date VARCHAR(50),
    notes TEXT,
    created_at DATETIME,
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_create_date (create_date),
    FOREIGN KEY (elderly_id) REFERENCES elderly_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入默认管理员用户
INSERT INTO users (username, password, role, created_at, updated_at)
VALUES ('admin', '111111', 'ADMIN', NOW(), NOW())
ON DUPLICATE KEY UPDATE username=username;

-- 插入老人档案示例数据
INSERT INTO elderly_info (name, gender, birth_date, id_card, phone, address, emergency_contact, emergency_phone, living_arrangement, medical_insurance, created_at, updated_at)
VALUES 
('张桂兰', '女', '1945-03-15', '110101194503150024', '13812345678', '北京市东城区和平里街道1号楼', '张明', '13987654321', '与子女同住', '城镇职工基本医疗保险', NOW(), NOW()),
('李建国', '男', '1948-07-22', '110101194807220036', '13723456789', '北京市西城区德胜街道2号楼', '李华', '13698765432', '与配偶同住', '城乡居民基本医疗保险', NOW(), NOW()),
('王秀英', '女', '1950-11-08', '110101195011080048', '13634567890', '北京市朝阳区建国路街道3号楼', '王强', '13587654321', '独居', '城镇职工基本医疗保险', NOW(), NOW()),
('赵德福', '男', '1947-05-30', '110101194705300059', '13545678901', '北京市海淀区中关村街道4号楼', '赵丽', '13498765432', '养老院', '公费医疗', NOW(), NOW()),
('刘淑芬', '女', '1952-09-12', '110101195209120061', '13456789012', '北京市丰台区方庄街道5号楼', '刘伟', '13387654321', '与其他亲属同住', '商业保险', NOW(), NOW()),
('陈国华', '男', '1946-01-25', '110101194601250072', '13367890123', '北京市石景山区鲁谷街道6号楼', '陈敏', '13287654321', '与配偶同住', '城镇职工基本医疗保险', NOW(), NOW()),
('杨玉梅', '女', '1953-04-18', '110101195304180083', '13278901234', '北京市门头沟区大峪街道7号楼', '杨勇', '13187654321', '独居', '城乡居民基本医疗保险', NOW(), NOW()),
('黄志强', '男', '1949-08-05', '110101194908050094', '13189012345', '北京市房山区良乡街道8号楼', '黄婷', '13087654321', '与子女同住', '城镇职工基本医疗保险', NOW(), NOW())
ON DUPLICATE KEY UPDATE id_card=id_card;
