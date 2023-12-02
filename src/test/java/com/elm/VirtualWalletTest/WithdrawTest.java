package com.elm.VirtualWalletTest;

import com.elm.exception.BusinessException;
import com.elm.mapper.VirtualWalletMapper;
import com.elm.model.bo.VirtualWallet;
import com.elm.service.impl.VirtualWalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WithdrawTest {

    @Mock
    private VirtualWalletMapper virtualWalletMapper;

    @InjectMocks
    private VirtualWalletServiceImpl virtualWalletService;

    @Test
    void testWithdrawSuccess() throws SQLException {
        // Arrange
        String userId = "testUser";
        Integer amount = 50;
        String target = "WeChat";
        VirtualWallet virtualWallet = new VirtualWallet();
        virtualWallet.setBalance(100);
        when(virtualWalletMapper.getVirtualWallet(userId)).thenReturn(virtualWallet);
        when(virtualWalletMapper.updateVirtualWallet(userId, 50)).thenReturn(1);

        // Act
        int result = virtualWalletService.withdraw(userId, amount, target);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testWithdrawWalletNotFound() throws SQLException {
        // Arrange
        String userId = "testUser";
        Integer amount = 50;
        String target = "WeChat";
        when(virtualWalletMapper.getVirtualWallet(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(BusinessException.class, () -> virtualWalletService.withdraw(userId, amount, target));
    }

}
