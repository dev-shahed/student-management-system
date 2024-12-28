package com.smsytem.students.entity;

import java.time.LocalDateTime;

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
 * Entity representing notifications/announcements in the system
 * Supports different types of notifications for various user roles
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_audience", nullable = false)
    private TargetAudience targetAudience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender; // Who sent the notification

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassOrSection targetClass; // If notification is for specific class

    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime; // For scheduled notifications

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Enum for notification types
     */
    public enum NotificationType {
        ANNOUNCEMENT,
        ASSIGNMENT,
        EXAM_SCHEDULE,
        RESULT_PUBLISHED,
        ATTENDANCE_ALERT,
        FEE_REMINDER,
        EVENT,
        HOLIDAY,
        EMERGENCY,
        GENERAL
    }

    /**
     * Enum for notification priority
     */
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    /**
     * Enum for target audience
     */
    public enum TargetAudience {
        ALL_USERS,
        STUDENTS,
        TEACHERS,
        PARENTS,
        ADMIN_STAFF,
        SPECIFIC_CLASS,
        SPECIFIC_USER
    }
}
