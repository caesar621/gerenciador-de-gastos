package entity;

import jakarta.persistence.*;
import java.util.Date;
import enums.Status;

public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public int installmentNumber;
    public float installmentValue;
    public Date dueDate;

    @Enumerated(EnumType.STRING)
    public Status status;

}
