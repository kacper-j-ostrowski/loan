package pl.ostrowski.loan.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SystemParameter {

    @Id
    @Column
    private String paramName;

    @Column
    private String paramValue;

}
