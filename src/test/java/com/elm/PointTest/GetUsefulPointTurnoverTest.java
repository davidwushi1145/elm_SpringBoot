package com.elm.PointTest;

import com.elm.exception.BusinessException;
import com.elm.mapper.PointMapper;
import com.elm.mapper.PointTurnoverMapper;
import com.elm.service.impl.PointServiceImpl;
import com.elm.model.bo.Point;
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
public class GetUsefulPointTurnoverTest {
    @Mock
    private PointMapper pointMapper;

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    private PointTurnoverMapper pointTurnoverMapper;

    @Test
    void testGetUsefulPointTurnoverWhenAccountExists() throws SQLException {
        // Arrange
        String userId = "110";
        Point point = new Point();
        point.setId(1L);
        point.setUserId(userId);
        List<PointTurnover> pointTurnovers = new ArrayList<>();
        pointTurnovers.add(new PointTurnover()); // 添加一些示例数据

        when(pointMapper.getPoint(userId)).thenReturn(point);
        when(pointTurnoverMapper.getUsefulPointTurnover(point.getId(), userId)).thenReturn(pointTurnovers);

        // Act
        List<PointTurnover> result = pointService.getUsefulPointTurnover(userId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty()); // 预期返回非空列表
    }

    @Test
    void testGetUsefulPointTurnoverWhenAccountDoesNotExist() throws SQLException {
        // Arrange
        String userId = "1001";
        when(pointMapper.getPoint(userId)).thenReturn(null); // 模拟积分账户不存在的情况

        // Act & Assert
        assertThrows(BusinessException.class, () -> pointService.getUsefulPointTurnover(userId));
    }

    @Test
    void testGetUsefulPointTurnoverThrowsSQLException() throws SQLException {
        // Arrange
        String userId = "110";
        when(pointMapper.getPoint(userId)).thenThrow(SQLException.class); // 模拟数据库操作失败的情况

        // Act & Assert
        assertThrows(RuntimeException.class, () -> pointService.getUsefulPointTurnover(userId));
    }

}
