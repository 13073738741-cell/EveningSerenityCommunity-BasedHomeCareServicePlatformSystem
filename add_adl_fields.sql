-- 为health_assessment表添加ADL分项得分字段
-- 使用说明：
-- 1. 登录MySQL: mysql -u root -p
-- 2. 选择数据库: USE elderlycare;
-- 3. 执行此脚本: source add_adl_fields.sql;

USE elderlycare;

-- 添加ADL分项得分字段
ALTER TABLE health_assessment 
ADD COLUMN eating INT DEFAULT NULL COMMENT '进食得分',
ADD COLUMN bathing INT DEFAULT NULL COMMENT '洗澡得分',
ADD COLUMN grooming INT DEFAULT NULL COMMENT '修饰得分',
ADD COLUMN dressing INT DEFAULT NULL COMMENT '穿衣得分',
ADD COLUMN bowel_control INT DEFAULT NULL COMMENT '控制大便得分',
ADD COLUMN bladder_control INT DEFAULT NULL COMMENT '控制小便得分',
ADD COLUMN toilet INT DEFAULT NULL COMMENT '如厕得分',
ADD COLUMN transfer INT DEFAULT NULL COMMENT '床椅转移得分',
ADD COLUMN walking INT DEFAULT NULL COMMENT '平地行走得分',
ADD COLUMN stairs INT DEFAULT NULL COMMENT '上下楼梯得分';

-- 添加认知能力评估字段
ALTER TABLE health_assessment 
ADD COLUMN memory INT DEFAULT NULL COMMENT '记忆力得分',
ADD COLUMN attention INT DEFAULT NULL COMMENT '注意力得分',
ADD COLUMN language INT DEFAULT NULL COMMENT '语言能力得分',
ADD COLUMN judgment INT DEFAULT NULL COMMENT '判断力得分';

-- 添加心理健康评估字段
ALTER TABLE health_assessment 
ADD COLUMN mood VARCHAR(50) DEFAULT NULL COMMENT '情绪状态',
ADD COLUMN sleep VARCHAR(50) DEFAULT NULL COMMENT '睡眠质量',
ADD COLUMN social VARCHAR(50) DEFAULT NULL COMMENT '社交情况';

-- 慢性疾病情况字段（已存在medication_list，添加diseases）
ALTER TABLE health_assessment 
ADD COLUMN diseases TEXT DEFAULT NULL COMMENT '主要疾病',
ADD COLUMN medication TEXT DEFAULT NULL COMMENT '用药情况';

-- 添加评估总结字段
ALTER TABLE health_assessment 
ADD COLUMN overall_result VARCHAR(100) DEFAULT NULL COMMENT '综合评估结果',
ADD COLUMN suggestions TEXT DEFAULT NULL COMMENT '评估建议';

SELECT 'ADL分项字段添加完成！' AS message;
