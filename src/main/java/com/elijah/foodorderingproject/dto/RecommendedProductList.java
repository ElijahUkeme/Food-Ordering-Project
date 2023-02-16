package com.elijah.foodorderingproject.dto;

import com.elijah.foodorderingproject.model.product.Product;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RecommendedProductList {

    private int totalSize;
    private int typeId;
    private int offSet;
    private List<Product> productList;

    public RecommendedProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = productList.size();
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = 2;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = 5;
    }
}
