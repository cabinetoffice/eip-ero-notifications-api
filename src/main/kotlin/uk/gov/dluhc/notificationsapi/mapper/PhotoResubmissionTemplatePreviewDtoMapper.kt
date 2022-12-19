package uk.gov.dluhc.notificationsapi.mapper

import org.mapstruct.Mapper
import uk.gov.dluhc.notificationsapi.dto.GeneratePhotoResubmissionTemplatePreviewDto
import uk.gov.dluhc.notificationsapi.models.GeneratePhotoResubmissionTemplatePreviewRequest

@Mapper
interface PhotoResubmissionTemplatePreviewDtoMapper {

    fun toPhotoResubmissionTemplatePreviewDto(
        request: GeneratePhotoResubmissionTemplatePreviewRequest
    ): GeneratePhotoResubmissionTemplatePreviewDto
}
