package com.project.app;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    Map<String, List<Transaction>> allTransactions;

    public TransactionController() {
        allTransactions = new HashMap<>();
    }

    @RequestMapping(path = "/points", method = RequestMethod.POST)
    public Transaction transaction (@Valid @RequestBody Transaction points) {

        List<Transaction> transactions;
        //Check to see if payer already exists.
        if (allTransactions.containsKey(points.getPayer())) {
            //If so get current transactions.
            transactions = allTransactions.get(points.getPayer());
        } else {
            transactions = new ArrayList<>();
        }
        transactions.add(points);
        allTransactions.put(points.getPayer(), transactions);

        return points;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Map<String, Integer> points () {
        Map<String, Integer> balance = new HashMap<>();
        //Looping through the Map of all the transactions we have and summing together to get total for each payer.
        for (Map.Entry<String, List<Transaction>> pair : allTransactions.entrySet()) {
            balance.put(pair.getKey(), pair.getValue().stream().mapToInt(Transaction::getPoints).sum());
        }

        return balance;
    }

    @RequestMapping(path = "/spend", method = RequestMethod.POST)
    public Map<String, Integer> spend (@Valid @RequestBody Transaction points) {
        //Getting just the points from the transaction cause that's all we care about.
        Integer pointsValue = points.getPoints();
        Map<String, Integer> spent = new HashMap<>();
        //Creating a list of all transactions we have ordered by date.
        List<Transaction> orderedTransactions = allTransactions.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparingLong(point -> point.getTimestamp().getTime()))
                .collect(Collectors.toList());

        //Looping through the ordered transactions either till we have run out of transactions
        //or we have used up all the points to spend.
        for (int i = 0; i < orderedTransactions.size() && pointsValue > 0; i++) {
            Transaction point = orderedTransactions.get(i);
            if (point.getPoints() < pointsValue) {
                if (spent.containsKey(point.getPayer())) {
                    spent.put(point.getPayer(), spent.get(point.getPayer()) + -point.getPoints());
                } else {
                    spent.put(point.getPayer(), -point.getPoints());
                }
                pointsValue -= point.getPoints();
                point.setPoints(0);
            } else {
                if (spent.containsKey(point.getPayer())) {
                    spent.put(point.getPayer(), spent.get(point.getPayer()) + -pointsValue);
                } else {
                    spent.put(point.getPayer(), -pointsValue);
                }
                point.setPoints(point.getPoints() - pointsValue);
                pointsValue = 0;
            }
        }

        return spent;
    }

}
