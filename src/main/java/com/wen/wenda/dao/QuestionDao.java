package com.wen.wenda.dao;

import com.wen.wenda.model.Question;
import com.wen.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by wen on 2017/4/10.
 */
@Mapper
public interface QuestionDao {

    String TABLE_NAME =" question ";
    String INSERT_FIELD = " title,content,create_date,user_id,comment_count ";
    String SELECT_FIELD = " id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FIELD," ) values( ",
            "#{title},#{content},#{createDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME ,"where id=#{questionId}"})
    Question selectQuestionById(int questionId);

    List<Question> selectLatesQuestions(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Update({"update ",TABLE_NAME, " set comment_count=#{commentCount} ",
            "where id=#{questionId}",})
    public int updateQuetionComment(@Param("questionId") int questionId,
                                    @Param("commentCount") int commentCount);

}
