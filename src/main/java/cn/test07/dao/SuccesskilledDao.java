package cn.test07.dao;

import cn.test07.po.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by VNT on 2017/4/19.
 */
public interface SuccesskilledDao {
    public SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
    public int insertSuccesskilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
