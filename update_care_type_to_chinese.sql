-- 更新关爱档案表中的care_type字段为中文
UPDATE care_record SET care_type = '电话关怀' WHERE care_type = 'phone_call';
UPDATE care_record SET care_type = '上门探访' WHERE care_type = 'home_visit';
UPDATE care_record SET care_type = '节日慰问' WHERE care_type = 'festival_visit';
UPDATE care_record SET care_type = '应急探访' WHERE care_type = 'emergency_visit';
UPDATE care_record SET care_type = '就医陪同' WHERE care_type = 'medical_accompany';

-- 更新关爱效果字段为中文
UPDATE care_record SET care_effect = '优秀' WHERE care_effect = 'excellent';
UPDATE care_record SET care_effect = '良好' WHERE care_effect = 'good';
UPDATE care_record SET care_effect = '一般' WHERE care_effect = 'average';
UPDATE care_record SET care_effect = '较差' WHERE care_effect = 'poor';

-- 查看更新后的数据
SELECT id, elderly_name, care_date, care_type, care_effect FROM care_record LIMIT 10;

SELECT '关爱类型和关爱效果已更新为中文！' AS message;
