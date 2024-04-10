package io.github.zhoujunlin94.auto.code;

import cn.hutool.db.DbUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;

/**
 * @author zhoujunlin
 * @date 2024年03月22日 17:28
 * @desc
 */
public class TestMeta {

    public static void main(String[] args) {
        Table table = MetaUtil.getTableMeta(DbUtil.getDs(), "test");

    }

}
