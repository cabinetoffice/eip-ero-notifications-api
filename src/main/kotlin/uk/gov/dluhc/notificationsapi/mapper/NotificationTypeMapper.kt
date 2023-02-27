package uk.gov.dluhc.notificationsapi.mapper

import org.mapstruct.Mapper
import org.mapstruct.ValueMapping
import uk.gov.dluhc.notificationsapi.dto.NotificationType
import uk.gov.dluhc.notificationsapi.messaging.models.MessageType
import uk.gov.dluhc.notificationsapi.models.TemplateType
import uk.gov.dluhc.notificationsapi.database.entity.NotificationType as NotificationTypeEntity

@Mapper
interface NotificationTypeMapper {

    @ValueMapping(target = "APPLICATION_RECEIVED", source = "APPLICATION_MINUS_RECEIVED")
    @ValueMapping(target = "APPLICATION_APPROVED", source = "APPLICATION_MINUS_APPROVED")
    @ValueMapping(target = "APPLICATION_REJECTED", source = "APPLICATION_MINUS_REJECTED")
    @ValueMapping(target = "PHOTO_RESUBMISSION", source = "PHOTO_MINUS_RESUBMISSION")
    @ValueMapping(target = "ID_DOCUMENT_RESUBMISSION", source = "ID_MINUS_DOCUMENT_MINUS_RESUBMISSION")
    @ValueMapping(target = "POSTAL_APPLICATION_RECEIVED", source = "POSTAL_MINUS_APPLICATION_MINUS_RECEIVED")
    fun mapMessageTypeToNotificationType(messageType: MessageType): NotificationType

    fun toNotificationTypeEntity(notificationType: NotificationType): NotificationTypeEntity

    fun toNotificationTypeDto(notificationTypeEntity: NotificationTypeEntity): NotificationType

    @ValueMapping(source = "APPLICATION_RECEIVED", target = "APPLICATION_MINUS_RECEIVED")
    @ValueMapping(source = "APPLICATION_APPROVED", target = "APPLICATION_MINUS_APPROVED")
    @ValueMapping(source = "APPLICATION_REJECTED", target = "APPLICATION_MINUS_REJECTED")
    @ValueMapping(source = "PHOTO_RESUBMISSION", target = "PHOTO_MINUS_RESUBMISSION")
    @ValueMapping(source = "ID_DOCUMENT_RESUBMISSION", target = "ID_MINUS_DOCUMENT_MINUS_RESUBMISSION")
    @ValueMapping(source = "POSTAL_APPLICATION_RECEIVED", target = "POSTAL_MINUS_APPLICATION_MINUS_RECEIVED")
    fun fromNotificationTypeDtoToTemplateTypeApi(notificationType: NotificationType): TemplateType
}
