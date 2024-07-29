package org.nucleus.dto;

import org.nucleus.entity.meta.MetaData;
import org.springframework.stereotype.Component;


@Component
public class EligibilityPolicyParameterDTO {
    private Long id;
    private String parameter;
    private String value;
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PolicyEligibilityParameterMstDTO{" +
                "id=" + id +
                ", parameter='" + parameter + '\'' +
                ", value='" + value + '\'' +
                ", metaData=" + metaData +
                '}';
    }
}
