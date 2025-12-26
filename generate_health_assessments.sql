-- 为8位老人随机生成健康能力档案数据
-- Set character set
SET NAMES utf8mb4;
USE elderlycare;

-- 张桂兰 - 健康状况良好
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (1, '2025-12-20', 10, 5, 5, 10, 10, 10, 10, 15, 15, 10, 4, 4, 4, 4, 'stable', 'good', 'active', '高血压', '降压药', 'healthy', '保持良好生活习惯，定期复查', '', 100, 85, '李医生', '高血压', '降压药', '良好', '良好', '低');

-- 李建国 - 健康状况基本健康
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (2, '2025-12-18', 10, 5, 5, 10, 10, 10, 10, 15, 10, 10, 4, 4, 4, 4, 'stable', 'good', 'average', '糖尿病,心脏病', '降糖药,心脏病药', 'basically_healthy', '注意饮食控制，按时服药', '', 95, 80, '王护士', '糖尿病,心脏病', '降糖药,心脏病药', '良好', '一般', '中');

-- 王秀英 - 健康状况亚健康
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (3, '2025-12-22', 10, 5, 5, 10, 10, 10, 10, 10, 10, 5, 3, 3, 4, 3, 'anxious', 'average', 'less', '骨质疏松,关节炎', '钙片,止痛药', 'sub_healthy', '增加户外活动，注意防跌倒', '', 85, 70, '张医生', '骨质疏松,关节炎', '钙片,止痛药', '一般', '一般', '高');

-- 赵德福 - 健康状况需重点关注
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (4, '2025-12-19', 10, 5, 5, 5, 10, 10, 10, 10, 5, 5, 3, 3, 3, 3, 'frequent_anxious', 'poor', 'less', '脑血管疾病,高血压', '降压药,脑血管药', 'attention_needed', '需要家属陪同，定期康复训练', '', 75, 60, '李医生', '脑血管疾病,高血压', '降压药,脑血管药', '一般', '较差', '高');

-- 刘淑芬 - 健康状况良好
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (5, '2025-12-21', 10, 5, 5, 10, 10, 10, 10, 15, 15, 10, 5, 5, 5, 5, 'stable', 'good', 'active', '无', '无', 'healthy', '继续保持健康生活方式', '', 100, 90, '王护士', '无', '无', '良好', '良好', '低');

-- 陈国华 - 健康状况基本健康
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (6, '2025-12-17', 10, 5, 5, 10, 10, 10, 10, 15, 15, 10, 4, 4, 4, 4, 'stable', 'good', 'average', '慢性阻塞性肺病', '支气管扩张剂', 'basically_healthy', '避免感冒，注意保暖', '', 100, 75, '张医生', '慢性阻塞性肺病', '支气管扩张剂', '良好', '一般', '中');

-- 杨玉梅 - 健康状况亚健康
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (7, '2025-12-23', 10, 5, 5, 10, 10, 10, 10, 10, 10, 5, 3, 3, 4, 3, 'anxious', 'average', 'less', '高血压,糖尿病', '降压药,降糖药', 'sub_healthy', '注意血压血糖监测', '', 90, 65, '李医生', '高血压,糖尿病', '降压药,降糖药', '一般', '一般', '中');

-- 黄志强 - 健康状况需医疗干预
INSERT INTO health_assessment (elderly_id, assessment_date, eating, bathing, grooming, dressing, bowel_control, bladder_control, toilet, transfer, walking, stairs, memory, attention, language, judgment, mood, sleep, social, diseases, medication, overall_result, suggestions, notes, adl_score, iadl_score, assessor_name, chronic_diseases, medication_list, mental_status, nutrition_status, fall_risk)
VALUES (8, '2025-12-15', 5, 0, 5, 5, 10, 5, 5, 5, 5, 0, 2, 2, 3, 2, 'depressed', 'poor', 'lack', '心脏病,脑血管疾病,关节炎', '心脏病药,脑血管药,止痛药', 'medical_intervention', '建议住院治疗，需要专业护理', '', 45, 40, '王医生', '心脏病,脑血管疾病,关节炎', '心脏病药,脑血管药,止痛药', '较差', '较差', '高');

SELECT '健康能力档案数据生成完成！' AS message;
SELECT COUNT(*) AS assessment_count FROM health_assessment;
