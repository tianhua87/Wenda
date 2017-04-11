package com.wen.wenda;

import com.wen.wenda.dao.QuestionDao;
import com.wen.wenda.dao.UserDao;
import com.wen.wenda.model.Question;
import com.wen.wenda.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by wen on 2017/4/10.
 */

@SpringApplicationConfiguration(classes = DemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql("/createTable.sql")
public class InitDatabasesTest {

    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;

    @Test
    public void initDatabases(){

        Random random=new Random();
        for(int i=0;i<10;i++){
            User user=new User();
            user.setName("User"+i);
            user.setHeaderUrl("http://images.nowcoder.com/head/"+random.nextInt(1000)+"t.png");
            user.setPassword("wenjibin");
            user.setSalt("xx");
            userDao.addUser(user);
            //user.setPassword("sbsbsb");
            //userDao.updatePassword(user);
            Question question=new Question();
            question.setTitle("Title"+i);
            question.setContent("sbsbsbsbsbbsi"+i*i);
            Date date=new Date();
            date.setTime(date.getTime()+3600*1000*i);
            question.setCreateDate(date);
            question.setCommentCount(i);
            question.setUserId(i+1);
            questionDao.addQuestion(question);
        }
        //userDao.deleteById(3);
        //Assert.assertEquals("sbsbsb",userDao.selectById(4).getPassword());
    }

}
