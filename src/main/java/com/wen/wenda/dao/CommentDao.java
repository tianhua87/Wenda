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
    String INSERT_FIELD = " content,user_id,entity_id,entity_type,create_date,status ";
    String SELECT_FIELD = " id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FIELD," ) values( ",
    "#{content},#{userId},#{entityId},#{entityType},#{createDate},#{status})"})
    int addComment(Comment comment);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME, " where entity_id=#{entityId} " +
            "and entity_type=#{entityType}"})
    List<Comment> selectByEntity(@Param("entityId") int entityId,
                                 @Param("entityType") String entityType);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME, "where id=#{id}"})
    Comment selectById(int id);

    @Select({"select count(*) from ",TABLE_NAME, " where entity_id=#{entityId}",
    " and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,
                        @Param("entityType") String entityType);

    @Update({"update ",TABLE_NAME," set status=#{status} where id=#{id}"})
    int updateStatus(int status,int id);
}
