package com.shareholder.ui;

public class Stock {

    private Integer id;
    private String stockName;
    private Integer amount;
    private Double averagePrice;
    private String description;

    public Stock() {
    }

    public Stock(Integer id, String stockName, Integer amount, Double averagePrice, String description) {
        this.id = id;
        this.stockName = stockName;
        this.amount = amount;
        this.averagePrice = averagePrice;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
