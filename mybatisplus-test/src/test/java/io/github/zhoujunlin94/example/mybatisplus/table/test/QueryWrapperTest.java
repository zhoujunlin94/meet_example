package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.RoleMapper;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.UserMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class QueryWrapperTest {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Test
    public void tests() {
        // SELECT id,name,age,email,role_id FROM sys_user WHERE (role_id = 2)
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userMapper.selectList(new QueryWrapper<User>().eq("role_id", 2L));
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assertions.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        // SELECT id,name,age,email,role_id FROM sys_user WHERE (role_id IN (select id from role where id = 2))
        List<User> plainUsers2 = userMapper.selectList(new QueryWrapper<User>()
                .inSql("role_id", "select id from role where id = 2"));
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        Assertions.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        // SELECT id,name,age,email,role_id FROM sys_user WHERE ((role_id = 2 OR role_id = 3) AND (age >= 20))
        List<User> plainUsers3 = userMapper.selectList(new QueryWrapper<User>()
                .nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L))
                .and(i -> i.ge("age", 20)));
        List<User> lambdaUsers3 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assertions.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        // SELECT id,name,age,email,role_id FROM sys_user WHERE (role_id = 2)
        // 方式一
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = 2"));
/*        List<User> lambdaUsers4 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .apply("role_id = 2"));*/
        // 方式二
        List<User> plainUsers5 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = {0}", 2));
/*        List<User> lambdaUsers5 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .apply("role_id = {0}",2));*/
        print(plainUsers4);
        Assertions.assertEquals(plainUsers4.size(), plainUsers5.size());

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userMapper.update(new User(), uw);
        User u4 = userMapper.selectById(4);
        Assertions.assertNull(u4.getEmail());


    }

    @Test
    public void lambdaQueryWrapper() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getRoleId, 2L));
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assertions.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 = userMapper.selectList(new LambdaQueryWrapper<User>()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        Assertions.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 = userMapper.selectList(new LambdaQueryWrapper<User>()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        List<User> lambdaUsers3 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assertions.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = 2"));
        print(plainUsers4);

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userMapper.update(new User(), uw);
        User u4 = userMapper.selectById(4);
        Assertions.assertNull(u4.getEmail());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

    /**
     * SELECT id,name,age,email,role_id FROM user
     * WHERE ( 1 = 1 ) AND ( ( name = ? AND age = ? ) OR ( name = ? AND age = ? ) )
     */
    @Test
    public void testSql() {
        QueryWrapper<User> w = new QueryWrapper<>();
        w.and(i -> i.eq("1", 1))
                .nested(i ->
                        i.and(j -> j.eq("name", "a").eq("age", 2))
                                .or(j -> j.eq("name", "b").eq("age", 2)));
        userMapper.selectList(w);
    }

    /**
     * SELECT id,name FROM user
     * WHERE (age BETWEEN ? AND ?) ORDER BY role_id ASC,id ASC
     */
    @Test
    public void testSelect() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("id", "name").between("age", 20, 25)
                .orderByAsc("role_id", "id");
        List<User> plainUsers = userMapper.selectList(qw);

        LambdaQueryWrapper<User> lwq = new LambdaQueryWrapper<>();
        lwq.select(User::getId, User::getName).between(User::getAge, 20, 25)
                .orderByAsc(User::getRoleId, User::getId);
        List<User> lambdaUsers = userMapper.selectList(lwq);

        print(plainUsers);
        Assertions.assertEquals(plainUsers.size(), lambdaUsers.size());
    }
}
