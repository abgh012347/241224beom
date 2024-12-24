package com.example.demo_db.controller;

import com.example.demo_db.data.dto.BuyDTO;
import com.example.demo_db.service.BuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BuyController {
    private final BuyService buyService;

    @GetMapping(value="/buyinfo/{userid}")
    public ResponseEntity<List<BuyDTO>> getBuyInfo(@PathVariable("userid") String userId) {
        List<BuyDTO> buyDTOList=this.buyService.searchBuyInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(buyDTOList);
    }

    @PostMapping(value="/buyinfo")
    public ResponseEntity<BuyDTO> addBuyInfo(@RequestBody BuyDTO buyDTO) {
        BuyDTO saveBuyDTO=this.buyService.saveBuyInfo(buyDTO);
        if(saveBuyDTO!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saveBuyDTO);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
