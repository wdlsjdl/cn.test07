package cn.test07.dao;

import cn.test07.po.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by VNT on 2017/4/19.
 */
public interface SeckillDao {
    public int reduceNumber(@Param("seckillId") long seckilledId,@Param("killedTime") Date killedTime);
    public Seckill queryById(long seckillId);
    public List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
