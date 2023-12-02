package com.elm.service;

import com.elm.model.vo.TransactionFlowVo;
import com.elm.model.vo.VirtualWalletVo;

import java.util.List;

public interface VirtualWalletService {
    /**
     * 新建钱包
     *
     * @param userId
     * @return
     */
    int saveWallet(String userId);

    /**
     * 查看虚拟钱包
     *
     * @param userId
     * @return
     */
    VirtualWalletVo getWallet(String userId);

    /**
     * 充钱
     *
     * @param userId
     * @param amount
     * @return
     */
    int recharge(String userId, Integer amount);

    /**
     * 消费
     *
     * @param userId
     * @param amount
     * @return
     */
    int expense(String userId, Integer amount);

    /**
     * 提现
     *
     * @param userId
     * @param amount
     * @param target
     * @return
     */
    Integer withdraw(String userId, Integer amount, String target);

    /**
     * 查询交易流水
     *
     * @param userId
     * @return
     */
    List<TransactionFlowVo> getLog(String userId);
}
