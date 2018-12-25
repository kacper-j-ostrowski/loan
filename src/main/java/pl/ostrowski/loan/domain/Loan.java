package pl.ostrowski.loan.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Positive(message = "Amount can not be below zero")
    private BigDecimal amount;

    @Column
    @PositiveOrZero(message = "Principal has to be above zero or zero")
    private BigDecimal principal;

    @Column
    @PositiveOrZero(message = "Due amount has to be above zero or zero")
    private BigDecimal dueAmount;

    @Column
    @FutureOrPresent
    private LocalDate startDate;

    @Column
    @Future
    private LocalDate dueDate;

    @Column
    @PositiveOrZero
    private int extensionCounter;
}