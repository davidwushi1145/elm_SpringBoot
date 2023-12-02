package com.elm.VirtualWalletTest;

import com.elm.mapper.VirtualWalletMapper;
import com.elm.model.bo.VirtualWallet;
import com.elm.model.vo.VirtualWalletVo;
import com.elm.service.impl.VirtualWalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GetWalletTest {
    @Mock
    private VirtualWalletMapper virtualWalletMapper;

    @InjectMocks
    private VirtualWalletServiceImpl virtualWalletService;

    @Test
    void testGetWalletSuccess() throws SQLException {

        // Arrange
        String userId = "110";
        VirtualWallet virtualWallet = new VirtualWallet();
        virtualWallet.setUserId(userId);
        when(virtualWalletMapper.getVirtualWallet(userId)).thenReturn(virtualWallet);

        // Act
        VirtualWalletVo result = virtualWalletService.getWallet(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
    }

    @Test
    void testGetWalletThrowsException() throws SQLException {
        // Arrange
        String userId = "1101";
        when(virtualWalletMapper.getVirtualWallet(userId)).thenThrow(SQLException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> virtualWalletService.getWallet(userId));
    }
}
