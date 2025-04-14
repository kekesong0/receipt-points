package com.kekesong.receiptpoints.service;
import com.kekesong.receiptpoints.pojo.Receipt;

public interface ReceiptService {
    String saveReceipt(Receipt receipt);
    int calculatePoints(String id);
}
