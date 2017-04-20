package cn.test07.service.impl;

import cn.test07.dao.SeckillDao;
import cn.test07.dao.SuccesskilledDao;
import cn.test07.dto.Exposer;
import cn.test07.dto.SeckillExecution;
import cn.test07.exception.RepeatkillException;
import cn.test07.exception.SeckillCloseException;
import cn.test07.exception.SeckillException;
import cn.test07.po.Seckill;
import cn.test07.po.SeckillStateEnum;
import cn.test07.po.SuccessKilled;
import cn.test07.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by VNT on 2017/4/19.
 */
@Service
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccesskilledDao successkilledDao;

    private final String slat="9786b5789%B&*%^@)#(*N&W``!fd";
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }


    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=seckillDao.queryById(seckillId);
        if(seckill==null)
            return new Exposer(false, seckillId);

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime())
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());

        String md5=getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatkillException, SeckillCloseException {
        if(md5==null||!md5.equals(getMD5(seckillId)))
            throw new SeckillException("seckill data rewrite");

        Date now = new Date();
        try {
            int insertCount=successkilledDao.insertSuccesskilled(seckillId,userPhone);
            if(insertCount<=0)
                throw new RepeatkillException("seckill repeated");
            else{
                int updateCount = seckillDao.reduceNumber(seckillId, now);
                if(updateCount<=0)
                    throw new SeckillCloseException("seckill is closed");
                else{
                    SuccessKilled successKilled = successkilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (RepeatkillException e1) {
            throw e1;
        } catch (SeckillCloseException e2) {
            throw e2;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new RuntimeException("seckill inner error" + e.getMessage());
        }

    }
}
