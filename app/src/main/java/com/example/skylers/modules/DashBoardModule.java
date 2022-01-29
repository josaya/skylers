package com.example.skylers.modules;

public class DashBoardModule {

    String product, productPerformance, percentageIncrease;

    public DashBoardModule(String product, String performance, String percentage){
        this.product            =   product;
        this.productPerformance =   performance;
        this.percentageIncrease =   percentage;
    }

    public String getProduct() {
        return product;
    }

    public String getProductPerformance() {
        return productPerformance;
    }

    public String getPercentageIncrease() {
        return percentageIncrease;
    }
}
