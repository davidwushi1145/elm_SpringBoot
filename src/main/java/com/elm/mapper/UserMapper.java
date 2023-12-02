package com.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where userId=#{userId} and password=#{password}")
    public User getUserByIdByPass(String userId, String password) throws SQLException;
    //明显比JDBC的要方便很多，不需要额外的改了

    @Select("select count(*) from user where userId=#{userId}")
    public int getUserById(String userId) throws SQLException;

    @Insert("insert into user values(#{userId},#{password},#{userName},#{userSex},null,1,0)")
    public int saveUser(String userId, String password, String userName,Integer userSex) throws SQLException;

}