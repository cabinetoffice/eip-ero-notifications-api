package uk.gov.dluhc.notificationsapi.dto

abstract class BaseTemplatePersonalisationDto(
    val applicationReference: String,
    val firstName: String,
    val eroContactDetails: ContactDetailsDto
)

class IdDocumentPersonalisationDto(
    applicationReference: String,
    firstName: String,
    eroContactDetails: ContactDetailsDto,
    val idDocumentRequestFreeText: String
) : BaseTemplatePersonalisationDto(
    applicationReference = applicationReference,
    firstName = firstName,
    eroContactDetails = eroContactDetails
)

class IdDocumentRequiredPersonalisationDto(
    applicationReference: String,
    firstName: String,
    eroContactDetails: ContactDetailsDto,
    val idDocumentRequiredFreeText: String
) : BaseTemplatePersonalisationDto(
    applicationReference = applicationReference,
    firstName = firstName,
    eroContactDetails = eroContactDetails
)

class PhotoPersonalisationDto(
    applicationReference: String,
    firstName: String,
    eroContactDetails: ContactDetailsDto,
    val photoRequestFreeText: String,
    val uploadPhotoLink: String
) : BaseTemplatePersonalisationDto(
    applicationReference = applicationReference,
    firstName = firstName,
    eroContactDetails = eroContactDetails
)

class ApplicationApprovedPersonalisationDto(
    applicationReference: String,
    firstName: String,
    eroContactDetails: ContactDetailsDto,
) : BaseTemplatePersonalisationDto(
    applicationReference = applicationReference,
    firstName = firstName,
    eroContactDetails = eroContactDetails
)

class ApplicationReceivedPersonalisationDto(
    applicationReference: String,
    firstName: String,
    eroContactDetails: ContactDetailsDto,
) : BaseTemplatePersonalisationDto(
    applicationReference = applicationReference,
    firstName = firstName,
    eroContactDetails = eroContactDetails
)

class ApplicationRejectedPersonalisationDto(
    applicationReference: String,
    firstName: String,
    eroContactDetails: ContactDetailsDto,
    val rejectionReasonList: List<String>,
    val rejectionReasonMessage: String?
) : BaseTemplatePersonalisationDto(
    applicationReference = applicationReference,
    firstName = firstName,
    eroContactDetails = eroContactDetails
)
