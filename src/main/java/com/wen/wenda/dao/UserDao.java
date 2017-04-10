package com.wen.wenda.dao;

import com.wen.wenda.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by wen on 2017/4/10.
 */
@Mapper
public interface UserDao {

    String TABLE_NAME =" user ";
    String INSERT_FIELD = " name,password,salt,header_url ";
    String SELECT_FIELD = " id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FIELD," ) values( ",
    "#{name},#{password},#{salt},#{headerUrl})"})
    int addUser(User user);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    int deleteById(int id);

    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    int updatePassword(User user);
}
