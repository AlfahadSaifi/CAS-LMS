package org.nucleus.dto;/*
Developer: Vibhav Sehrawat
*/

import org.hibernate.validator.constraints.NotEmpty;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.FundBasedFlag;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductTypeDTO {
    private Long productTypeId;
    @NotEmpty(message = "Product type code cannot be left empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$" , message = "Product type code cannot have special characters and space")
    private String productTypeCode;
    @NotEmpty(message = "Product type description cannot be empty")
    private String description;
    private FundBasedFlag fundBasedFlag;
    private Character securedFlag;   // Y or N
    private List<String> productCodes;
    private TempMetaData metaData;

    public ProductTypeDTO() {
        productCodes = new ArrayList<>();
        metaData = new TempMetaData();
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FundBasedFlag getFundBasedFlag() {
        return fundBasedFlag;
    }

    public void setFundBasedFlag(FundBasedFlag fundBasedFlag) {
        this.fundBasedFlag = fundBasedFlag;
    }

    public Character getSecuredFlag() {
        return securedFlag;
    }

    public void setSecuredFlag(Character securedFlag) {
        this.securedFlag = securedFlag;
    }

    public TempMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TempMetaData metaData) {
        this.metaData = metaData;
    }

    public List<String> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<String> productCodes) {
        this.productCodes = productCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTypeDTO that = (ProductTypeDTO) o;
        return Objects.equals(productTypeId, that.productTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productTypeId);
    }

    @Override
    public String toString() {
        return "ProductTypeDTO{" +
                "productTypeId=" + productTypeId +
                ", productTypeCode='" + productTypeCode + '\'' +
                ", description='" + description + '\'' +
                ", fundBasedFlag=" + fundBasedFlag +
                ", securedFlag=" + securedFlag +
                ", metaData=" + metaData +
                '}';
    }
}
