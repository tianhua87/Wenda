package com.wen.wenda.dao;

import com.wen.wenda.model.Comment;
import com.wen.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by wen on 2017/4/12.
 */
@Mapper
public interface CommentDao {

    String TABLE_NAME =" comment ";
    String INSERT_FIELD = " content,user_id,entry_id,entry_type,create_date,status ";
    String SELECT_FIELD = " id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FIELD," ) values( ",
    "#{content},#{userId},#{entryId},#{entryType},#{createDate},#{status})"})
    int addComment(Comment comment);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME, "where entry_id=#{entryId} " +
            "and entry_type=#{entryType}"})
    List<Comment> selectByEntry(int entryId, String entryType);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME, "where id=#{id}"})
    Comment selectById(int id);

    @Update({"update ",TABLE_NAME," set status=#{status} where id=#{id}"})
    int updateStatus(int status,int id);
}
