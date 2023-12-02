package com.elm.VirtualWalletTest;

import com.elm.mapper.VirtualWalletMapper;
import com.elm.service.impl.VirtualWalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SaveWalletTest {

    @Mock
    private VirtualWalletMapper virtualWalletMapper;

    @InjectMocks
    private VirtualWalletServiceImpl virtualWalletService;

    @Test
    void testSaveWalletSuccess() throws SQLException {
        // Arrange
        String userId = "110";
        when(virtualWalletMapper.saveVirtualWallet(anyString(), anyString())).thenReturn(1);

        // Act
        int result = virtualWalletService.saveWallet(userId);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testSaveWalletThrowsException() throws SQLException {
        // Arrange
        String userId = "1101";
        when(virtualWalletMapper.saveVirtualWallet(anyString(), anyString())).thenThrow(SQLException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> virtualWalletService.saveWallet(userId));
    }
}
