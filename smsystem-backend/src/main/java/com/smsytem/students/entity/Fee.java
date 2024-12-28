package com.smsytem.students.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing fee structure and payments
 * Manages detailed fee information for students
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    private FeeType feeType;

    @Column(name = "academic_year", nullable = false)
    private String academicYear; // e.g., "2023-2024"

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "paid_amount")
    private Double paidAmount = 0.0;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "late_fee")
    private Double lateFee = 0.0;

    @Column(name = "discount")
    private Double discount = 0.0;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collected_by")
    private User collectedBy; // Staff who collected the payment

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    /**
     * Calculate remaining balance
     */
    public Double getBalance() {
        return amount - paidAmount + lateFee - discount;
    }

    /**
     * Enum for fee types
     */
    public enum FeeType {
        TUITION_FEE,
        ADMISSION_FEE,
        EXAMINATION_FEE,
        LIBRARY_FEE,
        LABORATORY_FEE,
        SPORTS_FEE,
        TRANSPORT_FEE,
        HOSTEL_FEE,
        CANTEEN_FEE,
        DEVELOPMENT_FEE,
        MISCELLANEOUS_FEE
    }

    /**
     * Enum for payment status
     */
    public enum PaymentStatus {
        PENDING,
        PARTIALLY_PAID,
        FULLY_PAID,
        OVERDUE,
        WAIVED
    }

    /**
     * Enum for payment methods
     */
    public enum PaymentMethod {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD,
        BANK_TRANSFER,
        ONLINE_PAYMENT,
        CHECK,
        DIGITAL_WALLET
    }
}
