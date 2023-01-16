package uk.gov.dluhc.notificationsapi.testsupport

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.badRequest
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.exactly
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.moreThan
import com.github.tomakehurst.wiremock.client.WireMock.notFound
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.serverError
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.http.RequestMethod.POST
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.newRequestPattern
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uk.gov.dluhc.eromanagementapi.models.ElectoralRegistrationOfficeResponse
import uk.gov.dluhc.notificationsapi.testsupport.model.NotifyGenerateTemplatePreviewSuccessResponse
import uk.gov.dluhc.notificationsapi.testsupport.model.NotifySendEmailSuccessResponse
import uk.gov.dluhc.notificationsapi.testsupport.model.NotifySendLetterSuccessResponse

/**
 * Service class to provide support to tests with setting up and managing wiremock stubs
 */
@Service
class WiremockService(private val wireMockServer: WireMockServer) {

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    private var baseUrl: String? = null

    private companion object {
        private const val NOTIFY_SEND_EMAIL_URL = "/v2/notifications/email"
        private const val NOTIFY_SEND_LETTER_URL = "/v2/notifications/letter"
        private const val GENERATE_TEMPLATE_PREVIEW_URL = "/v2/template/{templateId}/preview"
    }

    fun resetAllStubsAndMappings() {
        wireMockServer.resetAll()
    }

    fun wiremockBaseUrl(): String {
        if (baseUrl == null) {
            baseUrl = "http://localhost:${wireMockServer.port()}"
        }
        return baseUrl!!
    }

    fun stubNotifySendEmailResponse(response: NotifySendEmailSuccessResponse) {
        wireMockServer.stubFor(
            post(urlPathMatching(NOTIFY_SEND_EMAIL_URL)).willReturn(
                ResponseDefinitionBuilder.responseDefinition().withStatus(201)
                    .withBody(objectMapper.writeValueAsString(response))
            )
        )
    }

    fun stubNotifySendEmailBadRequestResponse() {
        wireMockServer.stubFor(
            post(urlPathMatching(NOTIFY_SEND_EMAIL_URL)).willReturn(
                badRequest().withBody(
                    """
                    [{"error": "BadRequestError",
                    "message": "Can't send to this recipient using a team-only API key"
                    }]"""
                )
            )
        )
    }

    fun stubNotifySendEmailInternalErrorResponse() {
        wireMockServer.stubFor(
            post(urlPathMatching(NOTIFY_SEND_EMAIL_URL)).willReturn(
                serverError().withBody(
                    """
                    [{"error": "Exception",
                    "message": "Internal server error"
                    }]"""
                )
            )
        )
    }

    fun stubNotifySendLetterResponse(response: NotifySendLetterSuccessResponse) {
        wireMockServer.stubFor(
            post(urlPathMatching(NOTIFY_SEND_LETTER_URL)).willReturn(
                ResponseDefinitionBuilder.responseDefinition().withStatus(201)
                    .withBody(objectMapper.writeValueAsString(response))
            )
        )
    }

    fun stubNotifySendLetterBadRequestResponse() {
        wireMockServer.stubFor(
            post(urlPathMatching(NOTIFY_SEND_LETTER_URL)).willReturn(
                badRequest().withBody(
                    """
                    [{"error": "BadRequestError",
                    "message": "Can't send to this recipient using a team-only API key"
                    }]"""
                )
            )
        )
    }

    fun stubNotifySendLetterInternalErrorResponse() {
        wireMockServer.stubFor(
            post(urlPathMatching(NOTIFY_SEND_LETTER_URL)).willReturn(
                serverError().withBody(
                    """
                    [{"error": "Exception",
                    "message": "Internal server error"
                    }]"""
                )
            )
        )
    }

    fun stubNotifyGenerateTemplatePreviewSuccessResponse(response: NotifyGenerateTemplatePreviewSuccessResponse) {
        val url = GENERATE_TEMPLATE_PREVIEW_URL.replace("{templateId}", response.id)
        wireMockServer.stubFor(
            post(urlPathEqualTo(url)).willReturn(
                ok().withBody(objectMapper.writeValueAsString(response))
            )
        )
    }

    fun stubNotifyGenerateTemplatePreviewNotFoundResponse(templateId: String) {
        val url = GENERATE_TEMPLATE_PREVIEW_URL.replace("{templateId}", templateId)
        val response = """
            {
              "errors": [
                {
                  "error": "NoResultFound",
                  "message": "No result found"
                }
              ],
              "status_code": 404
            }
        """.trimIndent()
        wireMockServer.stubFor(
            post(urlPathEqualTo(url)).willReturn(notFound().withBody(response))
        )
    }

    fun stubNotifyGenerateTemplatePreviewBadRequestResponse(templateId: String) {
        val url = GENERATE_TEMPLATE_PREVIEW_URL.replace("{templateId}", templateId)
        val response = """
            {
              "errors": [
                {
                  "error": "BadRequestError",
                  "message": "Missing personalisation: applicationReference, firstName"
                }
              ],
              "status_code": 400
            }
        """.trimIndent()
        wireMockServer.stubFor(
            post(urlPathEqualTo(url)).willReturn(badRequest().withBody(response))
        )
    }

    fun verifyNotifyGenerateTemplatePreview(templateId: String, personalisation: Map<String, Any>?) {
        val body = JSONObject()
        if (!personalisation.isNullOrEmpty()) {
            body.put("personalisation", JSONObject(personalisation))
        }

        val url = GENERATE_TEMPLATE_PREVIEW_URL.replace("{templateId}", templateId)
        wireMockServer.verify(
            1,
            postRequestedFor(urlPathEqualTo(url))
                .withRequestBody(equalToJson(body.toString()))
        )
    }

    fun verifyNotifySendEmailCalled() {
        wireMockServer.verify(newRequestPattern(POST, urlPathEqualTo(NOTIFY_SEND_EMAIL_URL)))
    }

    fun verifyNotifySendEmailCalledExactly(expectedCount: Int) {
        wireMockServer.verify(exactly(expectedCount), newRequestPattern(POST, urlPathEqualTo(NOTIFY_SEND_EMAIL_URL)))
    }

    fun verifyNotifySendEmailCalledMoreThan(expectedCount: Int) {
        wireMockServer.verify(moreThan(expectedCount), newRequestPattern(POST, urlPathEqualTo(NOTIFY_SEND_EMAIL_URL)))
    }

    fun verifyNotifySendLetterCalled() {
        wireMockServer.verify(newRequestPattern(POST, urlPathEqualTo(NOTIFY_SEND_LETTER_URL)))
    }

    fun stubCognitoJwtIssuerResponse() {
        wireMockServer.stubFor(
            get(urlPathMatching("/cognito/.well-known/jwks.json")).willReturn(
                ok().withBody(
                    """
                            {
                               "keys":[
                                    ${RsaKeyPair.jwk.toJSONString()}
                               ]
                            }
                    """.trimIndent()
                )
            )
        )
    }

    fun stubEroManagementGetEroByEroId(ero: ElectoralRegistrationOfficeResponse, eroId: String) {
        val responseBody = objectMapper.writeValueAsString(ero)
        wireMockServer.stubFor(
            get(WireMock.urlEqualTo("/ero-management-api/eros/$eroId"))
                .willReturn(
                    ResponseDefinitionBuilder.responseDefinition()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                )
        )
    }
}
