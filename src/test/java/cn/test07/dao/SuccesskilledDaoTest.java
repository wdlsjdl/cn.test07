package cn.test07.dao;

import cn.test07.po.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by VNT on 2017/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/spring-dao.xml")
public class SuccesskilledDaoTest {

    @Autowired
    private SuccesskilledDao successkilledDao;


    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1003;
        long phone = 13631971234L;
        SuccessKilled successKilled = successkilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

    @Test
    public void insertSuccesskilled() throws Exception {
        long id = 1003;
        long phone = 13631971234L;
        int insertCount = successkilledDao.insertSuccesskilled(id, phone);
        System.out.println("insertCount=" + insertCount);
    }

}