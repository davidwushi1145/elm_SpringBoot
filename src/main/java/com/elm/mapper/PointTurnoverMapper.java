package com.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.PointTurnover;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface PointTurnoverMapper extends BaseMapper<PointTurnover> {

    @Insert("insert into pointturnover values (?,#{pointId},#{userId},#{state},0,#{total},#{total},#{createTime},0)")
    int savePointTurnover(Long pointId, String userId, String state, Integer total, String createTime) throws SQLException;

    @Insert("insert into pointturnover values (?,#{pointId},#{userId},#{state},#{amount},0,0,#{createTime},0)")
    int saveUsePointTurnover(Long pointId, String userId, String state, Integer amount, String createTime) throws SQLException;
    //todo 更新查询逻辑

    /**
     * 用于获取未使用积分
     *
     * @param pointId
     * @param userId
     * @return
     */
    @Select("SELECT * FROM pointturnover WHERE userId = #{userId} AND pointId = #{pointId} AND state LIKE 'A%'")
    List<PointTurnover> getUsefulPointTurnover(Long pointId, String userId) throws SQLException;

    /**
     * 用于查询流水
     *
     * @param pointId
     * @param userId
     * @return
     */
    @Select("SELECT * FROM pointturnover WHERE userId =#{userId} and pointId =#{pointId}")
    List<PointTurnover> getPointTurnover(Long pointId, String userId) throws SQLException;

    @Update("UPDATE pointturnover SET state =#{state} WHERE id = #{id} and userId =#{userId} and pointId =#{pointId}")
    int updateState(Long id, Long pointId, String userId, String state) throws SQLException;

    @Update("UPDATE pointturnover SET balance =#{balance} WHERE id = #{id} and userId =#{userId} and pointId =#{pointId}")
    int updateBalance(Long id, Long pointId, String userId, Integer balance) throws SQLException;
}
