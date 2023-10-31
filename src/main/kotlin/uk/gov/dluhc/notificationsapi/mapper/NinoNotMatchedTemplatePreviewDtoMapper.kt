package uk.gov.dluhc.notificationsapi.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import uk.gov.dluhc.notificationsapi.dto.LanguageDto
import uk.gov.dluhc.notificationsapi.dto.NinoNotMatchedPersonalisationDto
import uk.gov.dluhc.notificationsapi.dto.NinoNotMatchedTemplatePreviewDto
import uk.gov.dluhc.notificationsapi.dto.NotificationType
import uk.gov.dluhc.notificationsapi.models.GenerateNinoNotMatchedTemplatePreviewRequest
import uk.gov.dluhc.notificationsapi.models.NinoNotMatchedPersonalisation
import uk.gov.dluhc.notificationsapi.models.SourceType

@Mapper(uses = [LanguageMapper::class, NotificationChannelMapper::class, SourceTypeMapper::class])
abstract class NinoNotMatchedTemplatePreviewDtoMapper {
    @Mapping(
        target = "notificationType",
        expression = "java( ninoNotMatchedNotificationType(request) )"
    )
    @Mapping(
        target = "personalisation",
        expression = "java( mapPersonalisation( language, request.getPersonalisation(), request.getSourceType() ) )"
    )
    abstract fun toDto(request: GenerateNinoNotMatchedTemplatePreviewRequest): NinoNotMatchedTemplatePreviewDto

    protected fun ninoNotMatchedNotificationType(request: GenerateNinoNotMatchedTemplatePreviewRequest): NotificationType =
        if (request.isSpecialCategoryElector)
            NotificationType.NINO_NOT_MATCHED_SPECIAL_CATEGORY_ELECTOR
        else
            NotificationType.NINO_NOT_MATCHED

    @Mapping(
        target = "sourceType",
        expression = "java( sourceTypeMapper.toSourceTypeString( sourceType, languageDto ) )",
    )
    @Mapping(
        source = "personalisation.additionalNotes",
        target = "additionalNotes"
    )
    abstract fun mapPersonalisation(
        languageDto: LanguageDto,
        personalisation: NinoNotMatchedPersonalisation,
        sourceType: SourceType
    ): NinoNotMatchedPersonalisationDto
}
