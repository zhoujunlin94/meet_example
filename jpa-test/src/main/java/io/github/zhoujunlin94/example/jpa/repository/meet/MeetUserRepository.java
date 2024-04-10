package io.github.zhoujunlin94.example.jpa.repository.meet;

import io.github.zhoujunlin94.example.jpa.model.meet.MeetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoujunlin
 * @date 2023年04月23日 16:00
 * @desc
 */
@Repository
public interface MeetUserRepository extends JpaRepository<MeetUser, Long> {

    MeetUser findFirstByUserId(Integer userId);

    @Modifying
    @Transactional(rollbackFor = Exception.class, transactionManager = "meetTransactionManager")
    @Query("update MeetUser u set u.userName = :userName where u.userId = :userId")
    int updateByUserId(@Param("userName") String userName, @Param("userId") Integer userId);

}
