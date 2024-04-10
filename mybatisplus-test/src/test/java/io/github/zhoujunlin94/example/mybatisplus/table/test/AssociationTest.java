package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.handler.meet.IUser4Service;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.CompanyMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.Company;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User4;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class AssociationTest {
    @Resource
    private CompanyMapper companyMapper;
    @Autowired
    private IUser4Service user4Service;

    @Test
    public void testSelectList() {
        user4Service.list().forEach(t -> System.out.println(t.getCompany()));
    }

    @Test
    public void testInsert() {
        List<User4> userList = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            Company cmp = new Company();
            cmp.setId(1L);
            User4 user = new User4();
            user.setId(100L + i);
            user.setCompany(cmp);
            user.setName("Han Meimei" + i);
            user.setEmail(user.getName() + "@baomidou.com");
            user.setAge(18);
            userList.add(user);
        }
        user4Service.saveBatch(userList);
        user4Service.list().forEach(System.out::println);
        testSelect();
        testUpdate();
    }


    private void testSelect() {
        QueryWrapper<User4> wrapper = new QueryWrapper<>();
        wrapper.eq("t.company_id", 1);
        int pageSize = 5;
        IPage<User4> page = new Page<User4>(1, pageSize);
        List<User4> userList = user4Service.selectUserPage(page, wrapper);
        for (int i = 1; i <= page.getPages(); ++i) {
            page = new Page<User4>(i, pageSize);
            userList = user4Service.selectUserPage(page, wrapper);
            System.out.printf("==========================>共%d条数据,当前显示第%d页，每页%d条，共%d页====================================>\n", page.getTotal(), page.getCurrent(), page.getSize(), page.getPages());
            userList.forEach(System.out::println);
        }
    }

    private void testUpdate() {
        System.out.println("=======================开始更新==================================================>");
        UpdateWrapper<User4> wrapper = new UpdateWrapper<>();
        wrapper.eq("company_id", 1);
        User4 user = new User4();
        user.setName(new Date().getTime() + "");
        user4Service.update(user, wrapper);
        testSelect();
    }

    @Test
    public void testResultMapCollection() {
        Company company = companyMapper.testResultMapCollection();
        System.out.println(company.getUserList().size());
    }
}
