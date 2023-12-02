package com.elm.PointTest;

import com.elm.exception.BusinessException;
import com.elm.mapper.PointMapper;
import com.elm.mapper.PointTurnoverMapper;
import com.elm.service.impl.PointServiceImpl;
import com.elm.model.bo.PointTurnover;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsePointTest {
    @Mock
    private PointMapper pointMapper;

    @Mock
    private PointTurnoverMapper pointTurnoverMapper;
    @InjectMocks
    private PointServiceImpl pointService;

    @Test
    void testUsePointSuccess() throws SQLException, ParseException {
        // Arrange
        List<PointTurnover> pointTurnovers = new ArrayList<>();
        PointTurnover pt = new PointTurnover();
        pt.setCreateTime("2023-01-01 00:00:00");
        pt.setBalance(110);
        pt.setId(1L);
        pt.setPointId(10005L);
        pt.setUserId("110");
        pointTurnovers.add(pt);

        when(pointTurnoverMapper.updateBalance(pt.getId(), pt.getPointId(), pt.getUserId(), 10)).thenReturn(1);
        when(pointTurnoverMapper.updateState(pt.getId(), pt.getPointId(), pt.getUserId(), "A2")).thenReturn(1);

        // Act
        List<PointTurnover> result = pointService.usePoint(pointTurnovers, 100);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() == 1); // 应该仍然有一个积分记录
    }

    @Test
    void testUsePointAllPointsUsed() throws SQLException, ParseException {
        // Arrange
        List<PointTurnover> pointTurnoverList = new ArrayList<>();
        PointTurnover pt1 = new PointTurnover();
        pt1.setId(1L);
        pt1.setPointId(10005L);
        pt1.setUserId("110");
        pt1.setBalance(50); // 50 points
        pt1.setCreateTime("2023-01-01 00:00:00");

        PointTurnover pt2 = new PointTurnover();
        pt2.setId(2L);
        pt2.setPointId(10005L);
        pt2.setUserId("110");
        pt2.setBalance(20); // 20 points
        pt2.setCreateTime("2023-01-02 00:00:00");

        pointTurnoverList.add(pt1);
        pointTurnoverList.add(pt2);

        int amountToUse = 100; // Trying to use 100 points, but only 70 available

        // No need to stub 'updateBalance' and 'updateState' methods, as they should not be called

        when(pointTurnoverMapper.updateBalance(pt1.getId(), pt1.getPointId(), pt1.getUserId(), 0)).thenReturn(1);
        when(pointTurnoverMapper.updateState(pt1.getId(), pt1.getPointId(), pt1.getUserId(), "B")).thenReturn(1);

        when(pointTurnoverMapper.updateBalance(pt2.getId(), pt2.getPointId(), pt2.getUserId(), 0)).thenReturn(1);
        when(pointTurnoverMapper.updateState(pt2.getId(), pt2.getPointId(), pt2.getUserId(), "B")).thenReturn(1);

        // Act
        assertThrows(BusinessException.class, () -> pointService.usePoint(pointTurnoverList, amountToUse));
    }
}

