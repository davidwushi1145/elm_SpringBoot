package com.elm.PointTest;

import com.elm.mapper.PointMapper;
import com.elm.mapper.PointTurnoverMapper;
import com.elm.service.impl.PointServiceImpl;
import com.elm.model.bo.Point;
import com.elm.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SavePointTurnoverTest {
    @Mock
    private PointMapper pointMapper;
    @Mock
    private PointTurnoverMapper pointTurnoverMapper;

    @InjectMocks
    private PointServiceImpl pointService;

    @Test
    void testSavePointTurnoverSuccess() throws SQLException {
        // Arrange
        String userId = "110";
        Integer amount = 50;
        Point point = new Point();
        point.setId(1L);
        point.setUserId(userId);

        when(pointMapper.getPoint(userId)).thenReturn(point);
        when(pointTurnoverMapper.savePointTurnover(point.getId(), userId, "A1", amount, DateUtil.getTodayString())).thenReturn(1);

        // Act
        Integer result = pointService.savePointTurnover(userId, amount);

        // Assert
        assertNotNull(result);
        assertEquals(1, result); // 预期返回值为 1，表示成功
    }

}
