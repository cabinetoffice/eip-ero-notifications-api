package uk.gov.dluhc.notificationsapi.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import uk.gov.dluhc.notificationsapi.config.NotifyTemplateConfiguration
import uk.gov.dluhc.notificationsapi.model.NotificationType

internal class NotificationTemplateMapperTest {

    private val mapper = NotificationTemplateMapper(
        NotifyTemplateConfiguration("RECEIVED-ID", "APPROVED-ID", "REJECTED-ID")
    )

    @ParameterizedTest
    @CsvSource(
        value = [
            "APPLICATION_RECEIVED, RECEIVED-ID",
            "APPLICATION_REJECTED, REJECTED-ID",
            "APPLICATION_APPROVED, APPROVED-ID"
        ]
    )
    fun `should map Notification Type to Notify Template ID`(approvalReason: NotificationType, expected: String) {
        // Given

        // When
        val notifyTemplateId = mapper.fromNotificationType(approvalReason)

        // Then
        assertThat(notifyTemplateId).isEqualTo(expected)
    }
}
