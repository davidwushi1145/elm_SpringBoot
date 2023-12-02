package com.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.DeliveryAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress> {
    @Select("select * from deliveryaddress where userId=#{userId} and isDelete=0 order by daId")
    public List<DeliveryAddress> listDeliveryAddressByUserId(String userId) throws SQLException;

    @Select("select * from deliveryaddress where daId=#{daId} and isDelete=0")
    public DeliveryAddress getDeliveryAddressById(Integer daId) throws SQLException;

    @Insert("insert into deliveryaddress values(null,#{contactName},#{contactSex},#{contactTel},#{address},#{userId},0)")
    public int saveDeliveryAddress(String userId, String contactName, Integer contactSex, String contactTel, String address) throws SQLException;

    @Update("update deliveryaddress set contactName=#{contactName},contactSex=#{contactSex},contactTel=#{contactTel},address=#{address} where daId=#{daId}")
    public int updateDeliveryAddress(Integer daId, String contactName, Integer contactSex, String contactTel, String address) throws SQLException;

    /**
     * 新删除，保留元素
     *
     * @param daId
     * @throws SQLException
     * @returnand isDelete=0
     */
    @Update("update deliveryaddress set isDelete = 1 where daId=#{daid}")
    public int removeDeliveryAddress(Integer daId) throws SQLException;

}
