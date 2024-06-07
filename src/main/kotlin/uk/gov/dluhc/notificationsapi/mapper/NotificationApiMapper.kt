package uk.gov.dluhc.notificationsapi.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import uk.gov.dluhc.notificationsapi.dto.NotificationDto
import uk.gov.dluhc.notificationsapi.models.SentCommunicationResponse
import java.time.OffsetDateTime
import java.time.ZoneOffset
import uk.gov.dluhc.notificationsapi.database.entity.Notification as NotificationEntity

@Mapper(
    uses = [NotificationTypeMapper::class, NotificationChannelMapper::class, SourceTypeMapper::class],
    imports = [OffsetDateTime::class, ZoneOffset::class]
)
interface NotificationApiMapper {

    fun toNotificationDto(notificationSummary: NotificationEntity): NotificationDto

    @Mapping(target = "subject", source = "notifyDetailsDto.subject")
    @Mapping(target = "body", source = "notifyDetailsDto.body")
    fun toSentCommunicationsApi(notificationDto: NotificationDto): SentCommunicationResponse
}
