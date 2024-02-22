package uk.gov.dluhc.notificationsapi.database.entity

enum class NotificationType {
    APPLICATION_RECEIVED,
    APPLICATION_APPROVED,
    APPLICATION_REJECTED,
    PHOTO_RESUBMISSION,
    ID_DOCUMENT_RESUBMISSION,
    ID_DOCUMENT_REQUIRED,
    REJECTED_DOCUMENT,
    REJECTED_SIGNATURE,
    REQUESTED_SIGNATURE,
    NINO_NOT_MATCHED,
    REJECTED_PARENT_GUARDIAN,
    REJECTED_QUALIFYING_ADDRESS
}
