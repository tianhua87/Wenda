package com.wen.wenda.dao;

import com.wen.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by wen on 2017/4/11.
 */
@Mapper
public interface LoginTicketDao {

    String TABLE_NAME =" login_ticket ";
    String INSERT_FIELD = " user_id,ticket,expired,status ";
    String SELECT_FIELD = " id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELD,") values (",
    "#{userId},#{ticket},#{expired},#{status} )"})
    public int addLoginTicket(LoginTicket loginTicket);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME,"where ticket=#{ticket}"})
    public LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
