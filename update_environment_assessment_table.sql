-- 更新环境评估表结构
USE elderlycare;

-- 修改列名以匹配新的实体类字段
ALTER TABLE environment_assessment 
CHANGE COLUMN housing_type house_type VARCHAR(255);

ALTER TABLE environment_assessment 
CHANGE COLUMN floor_level has_elevator BOOLEAN;

ALTER TABLE environment_assessment 
CHANGE COLUMN safety_score floor_safety VARCHAR(255);

ALTER TABLE environment_assessment 
CHANGE COLUMN bathroom_safety handrails VARCHAR(255);

ALTER TABLE environment_assessment 
CHANGE COLUMN kitchen_safety overall_result VARCHAR(255);

ALTER TABLE environment_assessment 
CHANGE COLUMN lighting problems TEXT;

ALTER TABLE environment_assessment 
CHANGE COLUMN ventilation suggestions TEXT;

-- 删除不再使用的列
ALTER TABLE environment_assessment 
DROP COLUMN accessibility;

ALTER TABLE environment_assessment 
DROP COLUMN hazards;

ALTER TABLE environment_assessment 
DROP COLUMN recommendations;

SELECT '环境评估表结构更新完成！' AS message;
