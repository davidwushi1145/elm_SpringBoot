package com.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.TransactionFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface TransactionFlowMapper extends BaseMapper<TransactionFlow> {
    @Insert("insert into transactionFlow values (?,#{virtualWalletId},#{userId},#{state},#{amount},#{createTime})")
    public int saveTransactionFlow(String userId, Long virtualWalletId, String state, Integer amount, String createTime) throws SQLException;

    @Select("select id,state,amount,createTime from transactionFlow where userId = #{userId} and virtualWalletId = #{virtualWalletId}")
    public List<TransactionFlow> getTransactionFlow(String userId, Long virtualWalletId) throws SQLException;
}
