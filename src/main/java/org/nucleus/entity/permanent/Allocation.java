package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ALLOCATION_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "ALLOCATION_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class Allocation {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long allocationId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "loanID")
    private LoanAccount loanAccount;

    private Date depositDate;
    private Double interestComponentReceived;
    private Double principalComponentReceived;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "allocationId")
    List<Penalty> penaltyList;

}