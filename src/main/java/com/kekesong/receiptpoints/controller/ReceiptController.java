package com.kekesong.receiptpoints.controller;

import com.kekesong.receiptpoints.common.BaseResponse;
import com.kekesong.receiptpoints.pojo.Receipt;
import com.kekesong.receiptpoints.service.ReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<BaseResponse<Object>> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.saveReceipt(receipt);
        return ResponseEntity.ok(BaseResponse.success(id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<BaseResponse<Object>> getPoints(@PathVariable String id) {
        try {
            int points = receiptService.calculatePoints(id);
            Map<String, Integer> responseData = Map.of("points", points);
            return ResponseEntity.ok(BaseResponse.success(responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(400, e.getMessage()));
        }
    }


}
