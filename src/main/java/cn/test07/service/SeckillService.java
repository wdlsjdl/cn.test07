package cn.test07.service;

import cn.test07.dto.Exposer;
import cn.test07.dto.SeckillExecution;
import cn.test07.exception.RepeatkillException;
import cn.test07.exception.SeckillCloseException;
import cn.test07.exception.SeckillException;
import cn.test07.po.Seckill;

import java.util.List;

/**
 * Created by VNT on 2017/4/19.
 */
public interface SeckillService {
    public Seckill getById(long seckillId);
    public List<Seckill> getSeckillList();
    public Exposer exportSeckillUrl(long seckillId);
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatkillException,SeckillCloseException;
}
