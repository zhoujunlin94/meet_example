package io.github.zhoujunlin94.example.seata.ds.repository.db.handler.product;

import io.github.zhoujunlin94.example.seata.ds.config.ProductMybatisConfig;
import io.github.zhoujunlin94.example.seata.ds.repository.db.entity.product.Product;
import io.github.zhoujunlin94.example.seata.ds.repository.db.mapper.product.ProductMapper;
import io.github.zhoujunlin94.meet.tk_mybatis.handler.TKHandler;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author zhoujunlin
 * @date 2023年09月21日 22:39
 * @desc
 */
@Slf4j
@Repository
public class ProductHandler extends TKHandler<ProductMapper, Product> {

    // 开启新事务
    @Transactional(transactionManager = ProductMybatisConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void reduceStock(Integer productId, Integer amount) throws Exception {
        log.info("[reduceStock] 当前 XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(productId, amount);

        log.info("[reduceStock] 开始扣减 {} 库存", productId);
        // 扣减库存
        int updateCount = this.baseMapper.reduceStock(productId, amount);

        if (updateCount == 0) {
            log.warn("[reduceStock] 扣除 {} 库存失败", productId);
            throw new Exception("库存不足");
        }
        // 扣除成功
        log.info("[reduceStock] 扣除 {} 库存成功", productId);
    }

    private void checkStock(Integer productId, Integer requiredAmount) throws Exception {
        log.info("[checkStock] 检查 {} 库存", productId);
        Integer stock = Optional.ofNullable(this.baseMapper.selectByPrimaryKey(productId)).map(Product::getStock).orElse(0);
        if (stock < requiredAmount) {
            log.warn("[checkStock] {} 库存不足，当前库存: {}", productId, stock);
            throw new Exception("库存不足");
        }
    }


}
