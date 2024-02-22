package uk.gov.dluhc.notificationsapi.testsupport.testdata.models

import uk.gov.dluhc.notificationsapi.models.ContactDetails
import uk.gov.dluhc.notificationsapi.models.GenerateRejectedOverseasDocumentTemplatePreviewRequest
import uk.gov.dluhc.notificationsapi.models.Language
import uk.gov.dluhc.notificationsapi.models.NotificationChannel
import uk.gov.dluhc.notificationsapi.models.OverseasDocumentType
import uk.gov.dluhc.notificationsapi.models.RejectedDocument
import uk.gov.dluhc.notificationsapi.models.RejectedOverseasDocumentPersonalisation
import uk.gov.dluhc.notificationsapi.testsupport.testdata.DataFaker
import uk.gov.dluhc.notificationsapi.testsupport.testdata.aValidApplicationReference
import uk.gov.dluhc.notificationsapi.testsupport.testdata.api.buildContactDetailsRequest

fun buildRejectedOverseasDocumentTemplatePreviewRequest(
    personalisation: RejectedOverseasDocumentPersonalisation = buildRejectedOverseasDocumentPersonalisation(),
    language: Language = Language.EN,
    overseasDocumentType: OverseasDocumentType = OverseasDocumentType.PARENT_MINUS_GUARDIAN,
    channel: NotificationChannel = NotificationChannel.EMAIL
): GenerateRejectedOverseasDocumentTemplatePreviewRequest =
    GenerateRejectedOverseasDocumentTemplatePreviewRequest(
        personalisation = personalisation,
        language = language,
        overseasDocumentType = overseasDocumentType,
        channel = channel
    )

fun buildRejectedOverseasDocumentPersonalisation(
    applicationReference: String = aValidApplicationReference(),
    firstName: String = DataFaker.faker.name().firstName(),
    eroContactDetails: ContactDetails = buildContactDetailsRequest(localAuthorityName = "Barcelona"),
    documents: List<RejectedDocument> = listOf(buildRejectedDocument()),
    rejectedDocumentFreeText: String? = null
): RejectedOverseasDocumentPersonalisation =
    RejectedOverseasDocumentPersonalisation(
        applicationReference = applicationReference,
        firstName = firstName,
        eroContactDetails = eroContactDetails,
        documents = documents,
        rejectedDocumentFreeText = rejectedDocumentFreeText
    )
