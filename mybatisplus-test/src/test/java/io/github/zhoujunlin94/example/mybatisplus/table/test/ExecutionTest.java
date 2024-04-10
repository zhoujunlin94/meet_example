package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.StudentMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 执行分析测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class ExecutionTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void test() {
        studentMapper.selectList(new QueryWrapper<>());
        studentMapper.deleteById(1L);
        Student student = new Student();
        student.setName("test_update");
        studentMapper.insert(new Student(1L, "test", 12));
        studentMapper.update(student, new QueryWrapper<Student>().eq("id", 1L));
        try {
            studentMapper.update(new Student(), new QueryWrapper<>());
        } catch (MyBatisSystemException e) {
        }
        try {
            studentMapper.delete(new QueryWrapper<>());
        } catch (MyBatisSystemException e) {
            System.err.println("执行了全表删除拦截，删除无效！异常：" + e.getMessage());
        }
        Assertions.assertTrue(CollectionUtils.isNotEmpty(studentMapper.selectList(new QueryWrapper<>())), "数据都被删掉了.(┬＿┬)");
    }
}
