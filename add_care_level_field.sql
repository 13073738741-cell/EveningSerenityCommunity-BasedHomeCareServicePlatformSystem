-- 添加缺失的care_level字段
ALTER TABLE care_record ADD COLUMN care_level VARCHAR(50) COMMENT '护理等级' AFTER notes;

SELECT 'care_level字段添加成功！' AS message;
DESCRIBE care_record;
