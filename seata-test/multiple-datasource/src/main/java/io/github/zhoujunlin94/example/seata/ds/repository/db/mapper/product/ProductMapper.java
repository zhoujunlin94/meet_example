package io.github.zhoujunlin94.example.seata.ds.repository.db.mapper.product;

import io.github.zhoujunlin94.example.seata.ds.repository.db.entity.product.Product;
import io.github.zhoujunlin94.meet.tk_mybatis.mapper.TKMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper extends TKMapper<Product> {

    @Update("UPDATE product SET stock = stock - #{amount} WHERE id = #{productId} AND stock >= #{amount}")
    int reduceStock(@Param("productId") Integer productId, @Param("amount") Integer amount);

}