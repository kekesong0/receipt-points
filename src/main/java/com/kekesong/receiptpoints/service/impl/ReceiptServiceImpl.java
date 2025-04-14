package com.kekesong.receiptpoints.service.impl;
import com.kekesong.receiptpoints.pojo.Item;
import com.kekesong.receiptpoints.pojo.Receipt;
import com.kekesong.receiptpoints.service.ReceiptService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private Map<String, Receipt> receiptMap = new HashMap<>();

    @Override
    public String saveReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptMap.put(id, receipt);
        return id;
    }

    @Override
    public int calculatePoints(String id) {
        Receipt receipt = receiptMap.get(id);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt with id " + id + " not found.");
        }

        int points = 0;

        //Rule 1 ：One point for every alphanumeric character in the retailer name.
        String retailer = receipt.getRetailer();
        for (char c : retailer.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points++;
            }
        }

        try {
            double total = Double.parseDouble(receipt.getTotal());
            //Rule 2 ：50 points if the total is a round dollar amount with no cents.
            if (total % 1 == 0) {
                points += 50;
            }

            //Rule 3 ：25 points if the total is a multiple of 0.25.
            if (total % 0.25 == 0) {
                points += 25;
            }

        } catch (NumberFormatException e) {
            // Invalid total format
            throw new NumberFormatException("Invalid total format: " + receipt.getTotal());
        }

        //Rule4 ：5 points for every two items on the receipt.
        List<Item> items = receipt.getItems();
        points += (items.size() / 2) * 5;

        //Rule 5 : If the trimmed description length is a multiple of 3, earn ceil(price × 0.2) points.
        for (Item item : items) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                try {
                    double price = Double.parseDouble(item.getPrice());
                    points += (int) Math.ceil(price * 0.2);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Failed to parse price: '" + item.getPrice() + "' in item: " + desc);
                }
            }
        }

        //Rule 6 ：6 points if the day in the purchase date is odd.
        try {
            String[] dateParts = receipt.getPurchaseDate().split("-");
            int day = Integer.parseInt(dateParts[2]);
            if (day % 2 != 0) {
                points += 6;
            }
        } catch (Exception e) {
            throw new NumberFormatException("Invalid purchaseDate format: " + receipt.getPurchaseDate());
        }

        //Rule 7 : 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        try {
            String[] timeParts = receipt.getPurchaseTime().split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            if (hour == 14 || (hour == 15 && minute < 60)) {
                points += 10;
            }
        } catch (Exception e) {
            throw new NumberFormatException("Invalid time format: " + receipt.getPurchaseTime());
        }

        return points;
    }
}

