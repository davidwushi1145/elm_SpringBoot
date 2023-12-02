package com.elm.service.impl;

import com.elm.common.ErrorCode;
import com.elm.mapper.TransactionFlowMapper;
import com.elm.mapper.VirtualWalletMapper;
import com.elm.model.bo.TransactionFlow;
import com.elm.model.bo.VirtualWallet;
import com.elm.model.vo.TransactionFlowVo;
import com.elm.model.vo.VirtualWalletVo;
import com.elm.service.VirtualWalletService;
import com.elm.exception.BusinessException;
import com.elm.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirtualWalletServiceImpl implements VirtualWalletService {
    @Autowired
    private VirtualWalletMapper virtualWalletMapper;

    @Autowired
    private TransactionFlowMapper transactionFlowMapper;

    @Override
    public int saveWallet(String userId) {
        try {
            String currentTime = DateUtil.getTodayString();
            return virtualWalletMapper.saveVirtualWallet(userId, currentTime);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VirtualWalletVo getWallet(String userId) {
        try {
            VirtualWallet virtualWallet = virtualWalletMapper.getVirtualWallet(userId);
            return getVirtualWalletVo(virtualWallet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int recharge(String userId, Integer amount) {
        //先获取余额
        VirtualWalletVo virtualWalletVo = this.getWallet(userId);
        if (virtualWalletVo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "当前用户" + userId + " 的虚拟钱包不存在");
        }
        //再更新余额
        Integer balance = virtualWalletVo.getBalance() + amount;
        try {
            return virtualWalletMapper.updateVirtualWallet(userId, balance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int expense(String userId, Integer amount) {
        //先获取余额
        VirtualWalletVo virtualWalletVo = this.getWallet(userId);
        if (virtualWalletVo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "当前用户" + userId + " 的虚拟钱包不存在");
        }
        //再更新余额
        Integer balance = virtualWalletVo.getBalance() - amount;
        try {
            return virtualWalletMapper.updateVirtualWallet(userId, balance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer withdraw(String userId, Integer amount, String target) {
        switch (target) {
            case "WeChat":
                System.out.println("提现到微信账户");
                break;
            case "ALiPay":
                System.out.println("提现到支付宝账户");
                break;
            case "bankCard":
                System.out.println("提现到银行卡");
                break;
        }
        //先获取余额
        VirtualWalletVo virtualWalletVo = this.getWallet(userId);
        if (virtualWalletVo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "当前用户" + userId + " 的虚拟钱包不存在");
        }
        //再更新余额
        Integer balance = virtualWalletVo.getBalance() - amount;
        try {
            return virtualWalletMapper.updateVirtualWallet(userId, balance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TransactionFlowVo> getLog(String userId) {
        VirtualWalletVo virtualWalletVo = this.getWallet(userId);
        if (virtualWalletVo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "当前用户" + userId + " 的虚拟钱包不存在");
        }
        try {
            List<TransactionFlow> transactionFlowList = transactionFlowMapper.getTransactionFlow(userId, virtualWalletVo.getId());
            return getTransactionFlowVo(transactionFlowList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public VirtualWalletVo getVirtualWalletVo(VirtualWallet virtualWallet) {
        if (virtualWallet == null) {
            return null;
        }
        VirtualWalletVo virtualWalletVo = new VirtualWalletVo();
        BeanUtils.copyProperties(virtualWallet, virtualWalletVo);
        return virtualWalletVo;
    }

    public TransactionFlowVo getTransactionFlowVo(TransactionFlow transactionFlow) {
        if (transactionFlow == null) {
            return null;
        }
        TransactionFlowVo transactionFlowVo = new TransactionFlowVo();
        BeanUtils.copyProperties(transactionFlow, transactionFlowVo);
        return transactionFlowVo;
    }

    public List<TransactionFlowVo> getTransactionFlowVo(List<TransactionFlow> transactionFlowList) {
        if (CollectionUtils.isEmpty(transactionFlowList)) {
            return new ArrayList<>();
        }
        return transactionFlowList.stream().map(this::getTransactionFlowVo).collect(Collectors.toList());
    }
}
