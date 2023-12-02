package com.elm.PointTest;

import com.elm.mapper.PointMapper;
import com.elm.mapper.PointTurnoverMapper;
import com.elm.model.vo.PointTurnoverVo;
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
public class GetPointTurnoverVoListTest {
    @Mock
    private PointMapper pointMapper;

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    private PointTurnoverMapper pointTurnoverMapper;

    @Test
    void testGetPointTurnoverVoListSuccess() throws SQLException {
        // Arrange
        String userId = "user";
        Point point = new Point();
        point.setId(1L);
        point.setUserId(userId);
        List<PointTurnover> pointTurnovers = new ArrayList<>();

        when(pointMapper.getPoint(userId)).thenReturn(point);
        when(pointTurnoverMapper.getPointTurnover(point.getId(), userId)).thenReturn(pointTurnovers);

        // Act
        List<PointTurnoverVo> result = pointService.getPointTurnoverVoList(userId);

        // Assert
        assertNotNull(result);
    }

}
