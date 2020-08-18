package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buyer_sku_category")
public class BuyerOrderSkuCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId;

    @Column(name="buyer_id", nullable=false)
    private Long buyerId;

    @Column(name="category_name", nullable=false)
    private String categoryName;

    @Column(name="category_descr", nullable=false)
    private String categoryDescription;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="buyerSkuCategory")
    @JoinColumn(name="category_id")
    private List<BuyerOrderSku> buyerSkuList = new ArrayList<BuyerOrderSku>();

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<BuyerOrderSku> getBuyerSkuList() {
        return buyerSkuList;
    }

    public void setBuyerSkuList(List<BuyerOrderSku> buyerSkuList) {
        this.buyerSkuList = buyerSkuList;
    }
}
