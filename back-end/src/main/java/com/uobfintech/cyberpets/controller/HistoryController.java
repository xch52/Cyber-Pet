package com.uobfintech.cyberpets.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uobfintech.cyberpets.entity.Auction;
import com.uobfintech.cyberpets.entity.LotteryHistory;
import com.uobfintech.cyberpets.result.Result;
import com.uobfintech.cyberpets.service.HistoryService;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private HistoryService historyService;

    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }

    @GetMapping("lottery")
    public Result getLotteryHistoy(){
        List<LotteryHistory> lotteryHistories = historyService.findAllLotteryHistory();
        return Result.success(lotteryHistories);
    }

    @GetMapping("auction")
    public Result getAuctionHistoy(){
        List<Auction> auctions = historyService.findAllAuction();
        return Result.success(auctions);
    }
}
