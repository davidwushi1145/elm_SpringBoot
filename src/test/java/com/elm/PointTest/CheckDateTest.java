package com.elm.PointTest;

import com.elm.mapper.PointMapper;
import com.elm.mapper.PointTurnoverMapper;
import com.elm.service.impl.PointServiceImpl;
import com.elm.model.bo.PointTurnover;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CheckDateTest {
    @Mock
    private PointMapper pointMapper;

    @Mock
    private PointTurnoverMapper pointTurnoverMapper;
    @InjectMocks
    private PointServiceImpl pointService;

    @Test
    void testCheckDateWithValidPoints() throws SQLException {
        // Arrange
        List<PointTurnover> pointTurnovers = new ArrayList<>();
        PointTurnover pt = new PointTurnover();
        pt.setCreateTime("2023-10-02 00:00:00"); // 设置一个有效的日期
        pt.setId(1L);
        pt.setPointId(1L);
        pt.setUserId("110");
        pointTurnovers.add(pt);

        // Act
        List<PointTurnover> result = pointService.checkDate(pointTurnovers);

        // Assert
        assertFalse(result.isEmpty()); // 预期有效的积分未被移除
    }

    @Test
    void testCheckDateWithExpiredPoints() throws SQLException {
        // Arrange
        List<PointTurnover> pointTurnovers = new ArrayList<>();
        PointTurnover pt = new PointTurnover();
        pt.setCreateTime("2021-01-02 00:00:00"); // 设置一个过期的日期
        pt.setId(1L);
        pt.setPointId(1L);
        pt.setUserId("110");
        pointTurnovers.add(pt);

        when(pointTurnoverMapper.updateState(pt.getId(), pt.getPointId(), pt.getUserId(), "C")).thenReturn(1);

        // Act
        List<PointTurnover> result = pointService.checkDate(pointTurnovers);

        // Assert
        assertTrue(result.isEmpty()); // 预期过期的积分已被移除
    }
}
