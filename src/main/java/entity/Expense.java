package entity;

import enums.PaymentType;
import jakarta.persistence.*;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String name;
    public float totalValue;

    @Enumerated(EnumType.STRING)
    public PaymentType paymentType;

    @OneToMany
    public Category category;

    public int installment;
}


