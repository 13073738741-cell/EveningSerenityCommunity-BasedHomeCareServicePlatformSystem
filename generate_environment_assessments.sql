-- 为老人生成家庭环境评估档案数据
-- 评估日期：2025-12-26

-- 张桂兰 (id=1) - 居住在东城区和平里街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(1, '2025-12-20', 'owned', 1, 'good', 'good', 'complete', 'safe', 'no obvious problems', 'keep good living environment, check handrails regularly', '黎英', NOW());

-- 李建国 (id=2) - 居住在西城区德胜街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(2, '2025-12-21', 'rented', 0, 'average', 'average', 'partial', 'needs improvement', 'no handrails in bathroom, slight water on floor', 'install bathroom handrails, improve floor anti-slip, add ventilation', '黎英', NOW());

-- 王秀英 (id=3) - 居住在朝阳区建国路街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(3, '2025-12-22', 'owned', 1, 'good', 'good', 'complete', 'safe', 'no obvious problems', 'check electrical safety regularly, keep good ventilation', '黎英', NOW());

-- 赵德福 (id=4) - 居住在海淀区中关村街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(4, '2025-12-23', 'owned', 1, 'good', 'good', 'complete', 'safe', 'no obvious problems', 'keep good living environment, pay attention to winter warmth', '黎英', NOW());

-- 刘淑芬 (id=5) - 居住在丰台区方庄街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(5, '2025-12-24', 'rented', 0, 'poor', 'average', 'missing', 'needs improvement', 'no elevator, no bathroom handrails, poor ventilation, floor clutter', 'recommend: 1. apply for elevator installation or consider moving; 2. install bathroom handrails; 3. add ventilation equipment; 4. clear floor clutter to prevent falls', '黎英', NOW());

-- 陈国华 (id=6) - 居住在石景山区鲁谷街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(6, '2025-12-25', 'owned', 1, 'good', 'good', 'complete', 'safe', 'no obvious problems', 'check fire safety equipment regularly, keep passages clear', '黎英', NOW());

-- 杨玉梅 (id=7) - 居住在门头沟区大峪街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(7, '2025-12-26', 'owned', 0, 'average', 'average', 'partial', 'needs improvement', 'no elevator, steep stairs, loose bathroom handrails', 'recommend: 1. check and reinforce bathroom handrails; 2. install handrails on both sides of stairs; 3. improve indoor ventilation', '黎英', NOW());

-- 黄志强 (id=8) - 居住在房山区良乡街道
INSERT INTO environment_assessment (elderly_id, assessment_date, house_type, has_elevator, ventilation, floor_safety, handrails, overall_result, problems, suggestions, assessor_name, created_at) VALUES
(8, '2025-12-26', 'rented', 1, 'good', 'good', 'complete', 'safe', 'no obvious problems', 'check electrical safety regularly, keep indoor clean', '黎英', NOW());
