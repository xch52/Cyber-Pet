package com.uobfintech.cyberpets.service;

import java.util.List;

import com.uobfintech.cyberpets.entity.Auction;
import com.uobfintech.cyberpets.entity.History;
import com.uobfintech.cyberpets.entity.LotteryHistory;

public interface HistoryService {
    List<LotteryHistory> findAllLotteryHistory();

    List<Auction> findAllAuction();

    List<History> findAllMarketHistory();
}
