import mysql.connector
import random
from datetime import datetime, timedelta

# 数据库连接配置
config = {
    'user': 'root',
    'password': '123456',
    'host': 'localhost',
    'database': 'elderlycare',
    'charset': 'utf8mb4'
}

# 老人数据
elderly_data = [
    {'id': 1, 'name': '张桂兰', 'gender': '女'},
    {'id': 2, 'name': '李建国', 'gender': '男'},
    {'id': 3, 'name': '王秀英', 'gender': '女'},
    {'id': 4, 'name': '赵德福', 'gender': '男'},
    {'id': 5, 'name': '刘淑芬', 'gender': '女'},
    {'id': 6, 'name': '陈国华', 'gender': '男'},
    {'id': 7, 'name': '杨玉梅', 'gender': '女'},
    {'id': 8, 'name': '黄志强', 'gender': '男'},
]

# 健康评估模板数据
assessment_templates = [
    {
        'name': '健康状况良好',
        'overall_result': 'healthy',
        'eating': 10, 'bathing': 5, 'grooming': 5, 'dressing': 10,
        'bowel_control': 10, 'bladder_control': 10, 'toilet': 10,
        'transfer': 15, 'walking': 15, 'stairs': 10,
        'memory': 5, 'attention': 5, 'language': 5, 'judgment': 5,
        'mood': 'stable', 'sleep': 'good', 'social': 'active',
        'diseases': '高血压', 'medication': '降压药',
        'suggestions': '保持良好生活习惯，定期复查',
        'assessor_name': '李医生',
        'chronic_diseases': '高血压', 'medication_list': '降压药',
        'mental_status': '良好', 'nutrition_status': '良好', 'fall_risk': '低'
    },
    {
        'name': '健康状况基本健康',
        'overall_result': 'basically_healthy',
        'eating': 10, 'bathing': 5, 'grooming': 5, 'dressing': 10,
        'bowel_control': 10, 'bladder_control': 10, 'toilet': 10,
        'transfer': 15, 'walking': 10, 'stairs': 10,
        'memory': 4, 'attention': 4, 'language': 4, 'judgment': 4,
        'mood': 'stable', 'sleep': 'good', 'social': 'average',
        'diseases': '糖尿病,心脏病', 'medication': '降糖药,心脏病药',
        'suggestions': '注意饮食控制，按时服药',
        'assessor_name': '王护士',
        'chronic_diseases': '糖尿病,心脏病', 'medication_list': '降糖药,心脏病药',
        'mental_status': '良好', 'nutrition_status': '一般', 'fall_risk': '中'
    },
    {
        'name': '健康状况亚健康',
        'overall_result': 'sub_healthy',
        'eating': 10, 'bathing': 5, 'grooming': 5, 'dressing': 10,
        'bowel_control': 10, 'bladder_control': 10, 'toilet': 10,
        'transfer': 10, 'walking': 10, 'stairs': 5,
        'memory': 3, 'attention': 3, 'language': 4, 'judgment': 3,
        'mood': 'anxious', 'sleep': 'average', 'social': 'less',
        'diseases': '骨质疏松,关节炎', 'medication': '钙片,止痛药',
        'suggestions': '增加户外活动，注意防跌倒',
        'assessor_name': '张医生',
        'chronic_diseases': '骨质疏松,关节炎', 'medication_list': '钙片,止痛药',
        'mental_status': '一般', 'nutrition_status': '一般', 'fall_risk': '高'
    },
    {
        'name': '健康状况需重点关注',
        'overall_result': 'attention_needed',
        'eating': 10, 'bathing': 5, 'grooming': 5, 'dressing': 5,
        'bowel_control': 10, 'bladder_control': 10, 'toilet': 10,
        'transfer': 10, 'walking': 5, 'stairs': 5,
        'memory': 3, 'attention': 3, 'language': 3, 'judgment': 3,
        'mood': 'frequent_anxious', 'sleep': 'poor', 'social': 'less',
        'diseases': '脑血管疾病,高血压', 'medication': '降压药,脑血管药',
        'suggestions': '需要家属陪同，定期康复训练',
        'assessor_name': '李医生',
        'chronic_diseases': '脑血管疾病,高血压', 'medication_list': '降压药,脑血管药',
        'mental_status': '一般', 'nutrition_status': '较差', 'fall_risk': '高'
    },
    {
        'name': '健康状况需医疗干预',
        'overall_result': 'medical_intervention',
        'eating': 5, 'bathing': 0, 'grooming': 5, 'dressing': 5,
        'bowel_control': 10, 'bladder_control': 5, 'toilet': 5,
        'transfer': 5, 'walking': 5, 'stairs': 0,
        'memory': 2, 'attention': 2, 'language': 3, 'judgment': 2,
        'mood': 'depressed', 'sleep': 'poor', 'social': 'lack',
        'diseases': '心脏病,脑血管疾病,关节炎', 'medication': '心脏病药,脑血管药,止痛药',
        'suggestions': '建议住院治疗，需要专业护理',
        'assessor_name': '王医生',
        'chronic_diseases': '心脏病,脑血管疾病,关节炎', 'medication_list': '心脏病药,脑血管药,止痛药',
        'mental_status': '较差', 'nutrition_status': '较差', 'fall_risk': '高'
    }
]

# 为每位老人分配不同的健康状况
elderly_health_status = [
    0,  # 张桂兰 - 良好
    1,  # 李建国 - 基本健康
    2,  # 王秀英 - 亚健康
    3,  # 赵德福 - 需重点关注
    0,  # 刘淑芬 - 良好
    1,  # 陈国华 - 基本健康
    2,  # 杨玉梅 - 亚健康
    4,  # 黄志强 - 需医疗干预
]

def generate_assessment_date():
    """生成随机评估日期（最近30天内）"""
    days_ago = random.randint(1, 30)
    date = datetime.now() - timedelta(days=days_ago)
    return date.strftime('%Y-%m-%d')

def calculate_adl_score(template):
    """计算ADL总分"""
    return (template['eating'] + template['bathing'] + template['grooming'] +
            template['dressing'] + template['bowel_control'] + template['bladder_control'] +
            template['toilet'] + template['transfer'] + template['walking'] + template['stairs'])

def calculate_iadl_score(template):
    """计算IADL总分（基于认知能力）"""
    return (template['memory'] + template['attention'] +
            template['language'] + template['judgment']) * 5

def main():
    try:
        # 连接数据库
        conn = mysql.connector.connect(**config)
        cursor = conn.cursor()

        print("开始生成健康能力档案数据...")

        # 为每位老人生成健康评估记录
        for i, elderly in enumerate(elderly_data):
            template = assessment_templates[elderly_health_status[i]]
            adl_score = calculate_adl_score(template)
            iadl_score = calculate_iadl_score(template)
            assessment_date = generate_assessment_date()

            # 插入数据
            sql = """
            INSERT INTO health_assessment (
                elderly_id, assessment_date,
                eating, bathing, grooming, dressing,
                bowel_control, bladder_control, toilet,
                transfer, walking, stairs,
                memory, attention, language, judgment,
                mood, sleep, social,
                diseases, medication,
                overall_result, suggestions, notes,
                adl_score, iadl_score,
                assessor_name, chronic_diseases, medication_list,
                mental_status, nutrition_status, fall_risk
            ) VALUES (%s, %s,
                %s, %s, %s, %s,
                %s, %s, %s,
                %s, %s, %s,
                %s, %s, %s, %s,
                %s, %s, %s,
                %s, %s,
                %s, %s, %s,
                %s, %s,
                %s, %s, %s,
                %s, %s, %s
            )
            """

            values = (
                elderly['id'], assessment_date,
                template['eating'], template['bathing'], template['grooming'], template['dressing'],
                template['bowel_control'], template['bladder_control'], template['toilet'],
                template['transfer'], template['walking'], template['stairs'],
                template['memory'], template['attention'], template['language'], template['judgment'],
                template['mood'], template['sleep'], template['social'],
                template['diseases'], template['medication'],
                template['overall_result'], template['suggestions'], '',
                adl_score, iadl_score,
                template['assessor_name'], template['chronic_diseases'], template['medication_list'],
                template['mental_status'], template['nutrition_status'], template['fall_risk']
            )

            cursor.execute(sql, values)
            print(f"✓ 已为 {elderly['name']} 生成健康评估记录 - {template['name']} (ADL: {adl_score}分)")

        # 提交事务
        conn.commit()

        # 查询生成的记录数量
        cursor.execute("SELECT COUNT(*) FROM health_assessment")
        count = cursor.fetchone()[0]
        print(f"\n✓ 健康能力档案数据生成完成！共生成 {count} 条记录")

    except mysql.connector.Error as e:
        print(f"数据库错误: {e}")
        if conn:
            conn.rollback()
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()

if __name__ == '__main__':
    main()
