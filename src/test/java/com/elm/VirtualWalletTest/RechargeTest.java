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
public class RechargeTest {

    @Mock
    private VirtualWalletMapper virtualWalletMapper;

    @InjectMocks
    private VirtualWalletServiceImpl virtualWalletService;

    @Test
    void testRechargeSuccess() throws SQLException {
        // Arrange
        String userId = "testUser";
        Integer amount = 100;
        VirtualWallet virtualWallet = new VirtualWallet();
        virtualWallet.setBalance(50);
        when(virtualWalletMapper.getVirtualWallet(userId)).thenReturn(virtualWallet);
        when(virtualWalletMapper.updateVirtualWallet(userId, 150)).thenReturn(1);

        // Act
        int result = virtualWalletService.recharge(userId, amount);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testRechargeWalletNotFound() throws SQLException {
        // Arrange
        String userId = "testUser";
        Integer amount = 100;
        when(virtualWalletMapper.getVirtualWallet(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(BusinessException.class, () -> virtualWalletService.recharge(userId, amount));
    }

}
