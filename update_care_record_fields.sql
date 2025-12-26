-- 更新关爱档案表，添加新字段
ALTER TABLE care_record ADD COLUMN care_level VARCHAR(50) COMMENT '护理等级' AFTER notes;
ALTER TABLE care_record ADD COLUMN care_duration DOUBLE COMMENT '关爱时长（小时）' AFTER care_level;
ALTER TABLE care_record ADD COLUMN life_care_items TEXT COMMENT '生活照料项目' AFTER care_duration;
ALTER TABLE care_record ADD COLUMN life_care_record TEXT COMMENT '生活照料记录' AFTER life_care_items;
ALTER TABLE care_record ADD COLUMN blood_pressure VARCHAR(20) COMMENT '血压' AFTER life_care_record;
ALTER TABLE care_record ADD COLUMN heart_rate INT COMMENT '心率（次/分）' AFTER blood_pressure;
ALTER TABLE care_record ADD COLUMN temperature DOUBLE COMMENT '体温（℃）' AFTER heart_rate;
ALTER TABLE care_record ADD COLUMN blood_sugar DOUBLE COMMENT '血糖（mmol/L）' AFTER temperature;
ALTER TABLE care_record ADD COLUMN medication TEXT COMMENT '用药情况' AFTER blood_sugar;
ALTER TABLE care_record ADD COLUMN health_monitor_record TEXT COMMENT '健康监测记录' AFTER medication;
ALTER TABLE care_record ADD COLUMN mental_state VARCHAR(50) COMMENT '心理状态' AFTER health_monitor_record;
ALTER TABLE care_record ADD COLUMN mental_care_content TEXT COMMENT '心理关怀内容' AFTER mental_state;
ALTER TABLE care_record ADD COLUMN mental_care_record TEXT COMMENT '心理关怀记录' AFTER mental_care_content;
ALTER TABLE care_record ADD COLUMN care_summary TEXT COMMENT '关爱总结' AFTER mental_care_record;

SELECT '关爱档案表字段更新成功！' AS message;
DESCRIBE care_record;
