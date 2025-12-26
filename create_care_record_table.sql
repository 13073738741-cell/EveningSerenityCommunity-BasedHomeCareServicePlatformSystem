-- 创建关爱档案表
CREATE TABLE IF NOT EXISTS care_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    elderly_id BIGINT NOT NULL COMMENT '老人ID',
    elderly_name VARCHAR(100) NOT NULL COMMENT '老人姓名',
    care_date DATE NOT NULL COMMENT '关爱日期',
    care_type VARCHAR(50) NOT NULL COMMENT '关爱类型：phone_call-电话关怀, home_visit-上门探访, festival_visit-节日慰问, emergency_visit-应急探访, medical_accompany-就医陪同',
    care_content TEXT NOT NULL COMMENT '关爱内容',
    caregiver_name VARCHAR(100) NOT NULL COMMENT '关爱人员姓名',
    caregiver_phone VARCHAR(20) COMMENT '关爱人员联系电话',
    elderly_feedback TEXT COMMENT '老人反馈',
    care_effect VARCHAR(50) COMMENT '关爱效果：excellent-优秀, good-良好, average-一般, poor-较差',
    next_care_date DATE COMMENT '下次关爱日期',
    notes TEXT COMMENT '备注',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_care_date (care_date),
    INDEX idx_care_type (care_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关爱档案表';

-- 插入测试数据
INSERT INTO care_record (elderly_id, elderly_name, care_date, care_type, care_content, caregiver_name, caregiver_phone, elderly_feedback, care_effect, next_care_date, notes) VALUES
(1, '张德福', '2025-12-20', 'phone_call', '电话问候老人身体状况，了解近期生活情况', '李护士', '13800138001', '老人表示身体状况良好，感谢关心', 'good', '2025-12-27', '老人血压稳定，按时服药'),
(1, '张德福', '2025-12-15', 'home_visit', '上门探访，检查老人居住环境，了解生活需求', '王社工', '13800138002', '老人对探访很满意，希望多来探望', 'excellent', '2025-12-22', '老人居住环境良好，无安全隐患'),
(2, '李淑芬', '2025-12-18', 'phone_call', '电话关怀，提醒按时服药和测量血糖', '张护士', '13800138003', '老人表示会按时服药，感谢提醒', 'good', '2025-12-25', '老人血糖控制良好'),
(2, '李淑芬', '2025-12-10', 'festival_visit', '冬至节日慰问，送去饺子和保暖用品', '刘社工', '13800138004', '老人非常感动，感谢社区的关怀', 'excellent', '2025-12-17', '老人精神状态良好'),
(3, '王秀英', '2025-12-19', 'home_visit', '上门探访，帮助老人打扫卫生，整理房间', '陈社工', '13800138005', '老人表示感谢，房间整理得很干净', 'excellent', '2025-12-26', '老人行动不便，需要定期上门服务'),
(3, '王秀英', '2025-12-12', 'phone_call', '电话关怀，询问老人用药情况', '赵护士', '13800138006', '老人表示药快吃完了，需要帮忙购买', 'average', '2025-12-19', '已安排社工帮忙购买药品'),
(4, '赵德福', '2025-12-17', 'medical_accompany', '陪同老人去医院复查，帮助办理手续', '周社工', '13800138007', '老人表示很感谢，陪同很周到', 'excellent', '2025-12-24', '医生建议继续康复训练'),
(4, '赵德福', '2025-12-08', 'emergency_visit', '老人突发头晕，应急上门探访', '吴医生', '13800138008', '老人情况稳定，已及时处理', 'good', '2025-12-15', '老人血压偏高，需要密切监测'),
(5, '刘国强', '2025-12-21', 'phone_call', '电话关怀，了解老人近期生活情况', '郑护士', '13800138009', '老人表示生活正常，身体健康', 'good', '2025-12-28', '老人精神状态良好'),
(5, '刘国强', '2025-12-14', 'home_visit', '上门探访，与老人聊天，了解心理状态', '孙社工', '13800138010', '老人表示很开心，有人陪伴聊天', 'excellent', '2025-12-21', '老人心理状态良好'),
(6, '陈玉珍', '2025-12-16', 'festival_visit', '圣诞节日慰问，送去节日礼物和祝福', '马社工', '13800138011', '老人非常高兴，感谢社区关怀', 'excellent', '2025-12-23', '老人喜欢节日氛围'),
(6, '陈玉珍', '2025-12-09', 'phone_call', '电话关怀，提醒注意保暖，预防感冒', '林护士', '13800138012', '老人表示会注意保暖', 'good', '2025-12-16', '老人身体状况稳定'),
(7, '杨玉梅', '2025-12-22', 'home_visit', '上门探访，检查老人用电用气安全', '黄社工', '13800138013', '老人表示很关心安全问题', 'good', '2025-12-29', '老人家中安全设施良好'),
(7, '杨玉梅', '2025-12-13', 'phone_call', '电话关怀，询问老人饮食情况', '何护士', '13800138014', '老人表示饮食正常，营养均衡', 'good', '2025-12-20', '老人饮食情况良好'),
(8, '黄志强', '2025-12-11', 'emergency_visit', '老人摔倒受伤，应急上门处理', '朱医生', '13800138015', '老人情况紧急，已及时送医', 'good', '2025-12-18', '老人已住院治疗，情况稳定'),
(8, '黄志强', '2025-12-04', 'phone_call', '电话关怀，了解老人康复情况', '许护士', '13800138016', '老人表示康复中，感谢关心', 'average', '2025-12-11', '老人需要继续康复治疗');

SELECT '关爱档案表创建成功！' AS message;
SELECT COUNT(*) AS care_record_count FROM care_record;
