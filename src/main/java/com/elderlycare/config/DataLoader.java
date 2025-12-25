package com.elderlycare.config;

import com.elderlycare.entity.ElderlyInfo;
import com.elderlycare.entity.User;
import com.elderlycare.repository.ElderlyInfoRepository;
import com.elderlycare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ElderlyInfoRepository elderlyInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createDefaultAdmin();
        if (elderlyInfoRepository.count() == 0) {
            loadSampleElderlyData();
        }
    }

    private void createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("111111");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("成功创建默认管理员用户: admin/111111");
        } else {
            System.out.println("管理员用户已存在，跳过创建");
        }
    }

    private void loadSampleElderlyData() {
        ElderlyInfo elderly1 = new ElderlyInfo();
        elderly1.setName("张桂兰");
        elderly1.setGender("女");
        elderly1.setBirthDate("1945-03-15");
        elderly1.setIdCard("110101194503150024");
        elderly1.setPhone("13812345678");
        elderly1.setAddress("北京市东城区和平里街道1号楼");
        elderly1.setEmergencyContact("张明");
        elderly1.setEmergencyPhone("13987654321");
        elderly1.setLivingArrangement("与子女同住");
        elderly1.setMedicalInsurance("城镇职工基本医疗保险");
        elderlyInfoRepository.save(elderly1);

        ElderlyInfo elderly2 = new ElderlyInfo();
        elderly2.setName("李建国");
        elderly2.setGender("男");
        elderly2.setBirthDate("1948-07-22");
        elderly2.setIdCard("110101194807220036");
        elderly2.setPhone("13723456789");
        elderly2.setAddress("北京市西城区德胜街道2号楼");
        elderly2.setEmergencyContact("李华");
        elderly2.setEmergencyPhone("13698765432");
        elderly2.setLivingArrangement("与配偶同住");
        elderly2.setMedicalInsurance("城乡居民基本医疗保险");
        elderlyInfoRepository.save(elderly2);

        ElderlyInfo elderly3 = new ElderlyInfo();
        elderly3.setName("王秀英");
        elderly3.setGender("女");
        elderly3.setBirthDate("1950-11-08");
        elderly3.setIdCard("110101195011080048");
        elderly3.setPhone("13634567890");
        elderly3.setAddress("北京市朝阳区建国路街道3号楼");
        elderly3.setEmergencyContact("王强");
        elderly3.setEmergencyPhone("13587654321");
        elderly3.setLivingArrangement("独居");
        elderly3.setMedicalInsurance("城镇职工基本医疗保险");
        elderlyInfoRepository.save(elderly3);

        ElderlyInfo elderly4 = new ElderlyInfo();
        elderly4.setName("赵德福");
        elderly4.setGender("男");
        elderly4.setBirthDate("1947-05-30");
        elderly4.setIdCard("110101194705300059");
        elderly4.setPhone("13545678901");
        elderly4.setAddress("北京市海淀区中关村街道4号楼");
        elderly4.setEmergencyContact("赵丽");
        elderly4.setEmergencyPhone("13498765432");
        elderly4.setLivingArrangement("养老院");
        elderly4.setMedicalInsurance("公费医疗");
        elderlyInfoRepository.save(elderly4);

        ElderlyInfo elderly5 = new ElderlyInfo();
        elderly5.setName("刘淑芬");
        elderly5.setGender("女");
        elderly5.setBirthDate("1952-09-12");
        elderly5.setIdCard("110101195209120061");
        elderly5.setPhone("13456789012");
        elderly5.setAddress("北京市丰台区方庄街道5号楼");
        elderly5.setEmergencyContact("刘伟");
        elderly5.setEmergencyPhone("13387654321");
        elderly5.setLivingArrangement("与其他亲属同住");
        elderly5.setMedicalInsurance("商业保险");
        elderlyInfoRepository.save(elderly5);

        ElderlyInfo elderly6 = new ElderlyInfo();
        elderly6.setName("陈国华");
        elderly6.setGender("男");
        elderly6.setBirthDate("1946-01-25");
        elderly6.setIdCard("110101194601250072");
        elderly6.setPhone("13367890123");
        elderly6.setAddress("北京市石景山区鲁谷街道6号楼");
        elderly6.setEmergencyContact("陈敏");
        elderly6.setEmergencyPhone("13287654321");
        elderly6.setLivingArrangement("与配偶同住");
        elderly6.setMedicalInsurance("城镇职工基本医疗保险");
        elderlyInfoRepository.save(elderly6);

        ElderlyInfo elderly7 = new ElderlyInfo();
        elderly7.setName("杨玉梅");
        elderly7.setGender("女");
        elderly7.setBirthDate("1953-04-18");
        elderly7.setIdCard("110101195304180083");
        elderly7.setPhone("13278901234");
        elderly7.setAddress("北京市门头沟区大峪街道7号楼");
        elderly7.setEmergencyContact("杨勇");
        elderly7.setEmergencyPhone("13187654321");
        elderly7.setLivingArrangement("独居");
        elderly7.setMedicalInsurance("城乡居民基本医疗保险");
        elderlyInfoRepository.save(elderly7);

        ElderlyInfo elderly8 = new ElderlyInfo();
        elderly8.setName("黄志强");
        elderly8.setGender("男");
        elderly8.setBirthDate("1949-08-05");
        elderly8.setIdCard("110101194908050094");
        elderly8.setPhone("13189012345");
        elderly8.setAddress("北京市房山区良乡街道8号楼");
        elderly8.setEmergencyContact("黄婷");
        elderly8.setEmergencyPhone("13087654321");
        elderly8.setLivingArrangement("与子女同住");
        elderly8.setMedicalInsurance("城镇职工基本医疗保险");
        elderlyInfoRepository.save(elderly8);

        System.out.println("成功加载8条老人档案示例数据");
    }
}