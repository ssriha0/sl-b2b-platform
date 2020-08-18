package com.servicelive.orderfulfillment.notification.enumerations;

public enum NotificationType {
    EMAIL(1),
    SMS(2),
    PUSH(7),
    UNKNOWN(-1)
    ;

    private int notificationTypeId;

    NotificationType(int notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public int getNotificationTypeId() {
        return notificationTypeId;
    }

    public static NotificationType getType(int intVal) {
        if (intVal == EMAIL.notificationTypeId) {
            return EMAIL;
        } else if (intVal == SMS.notificationTypeId) {
            return SMS;
        }else if (intVal == PUSH.notificationTypeId) {
            return PUSH;
        }
        return UNKNOWN;
    }
}
