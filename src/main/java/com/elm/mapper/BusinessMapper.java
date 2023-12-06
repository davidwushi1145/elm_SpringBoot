package com.elm.mapper;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.Business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BusinessMapper extends BaseMapper<Business> {
    @Select("select * from business where orderTypeId=#{orderTypeId} order by businessId")
    public List<Business> listBusinessByOrderTypeId(Integer orderTypeId) throws SQLException;

    @Select("select * from business where businessId=#{businessId}")
    public Business getBusinessById(Integer businessId) throws SQLException;

    @Select("select * from business order by businessId")
    public List<Business> listBusiness() throws SQLException;

    @Select("SELECT * FROM business WHERE businessName LIKE CONCAT('%', #{businessName}, '%')")
    List<Business> listBusinessByBusinessName(String businessName) throws SQLException;
}
