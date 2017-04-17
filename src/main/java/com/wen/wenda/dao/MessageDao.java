package com.wen.wenda.dao;

import com.wen.wenda.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by wen on 2017/4/17.
 */
@Mapper
public interface MessageDao {

    final String TABLE_NAME=" message ";
    final String INSERT_FIELD=" from_id,to_id,create_date,has_read,content,conversation_id ";
    final String SELECT_FIELD="id,"+INSERT_FIELD;


    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELD," ) ","values( #{fromId},#{toId},#{createDate},#{hasRead},",
    "#{content},#{conversationId} )"})
    public int addMessage(Message message);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where conversation_id=#{conversationId} " +
            "order by create_date desc limit #{offset},#{limit}"})
    public List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                               @Param("offset") int offset,
                                               @Param("limit") int limit);

    //select *,count(id) as id from (select * from message order by create_date desc) tt group by tt.conversation_id order by create_date desc;
    @Select({"select ",INSERT_FIELD,", count(id) as id from (select * from message where from_id=#{userId} or to_id=#{userId} " +
            "order by create_date desc) tt " +
            "group by tt.conversation_id order by create_date desc limit #{offset},#{limit}"})
    public List<Message> getConversationList(@Param("userId") int userId,
                                             @Param("offset") int offset,
                                             @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME," where to_id=#{userId} and conversation_id=#{conversationId}" +
            " and has_read=0"})
    public int getUnreadMessageCount(@Param("userId") int userId,
                                          @Param("conversationId") String conversationId);

    @Update({"update ",TABLE_NAME," set has_read=1 where conversation_id=#{conversationId} " +
            "and to_id=#{userId}"})
    public int updateUnreadMessageStatus(@Param("conversationId") String conversationId,
                                         @Param("userId") int userId);
}
