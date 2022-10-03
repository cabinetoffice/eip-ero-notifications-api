package uk.gov.dluhc.notificationsapi.testsupport.testdata

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import uk.gov.dluhc.notificationsapi.testsupport.RsaKeyPair
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.UUID

fun getBearerToken(
    eroId: String = aValidRandomEroId(),
    email: String = "an-ero-user@$eroId.gov.uk",
    groups: List<String> = listOf("ero-$eroId", "ero-vc-admin-$eroId")
): String =
    "Bearer ${buildAccessToken(eroId, email, groups)}"

fun buildAccessToken(
    eroId: String = aValidRandomEroId(),
    email: String = "an-ero-user@$eroId.gov.uk",
    groups: List<String> = listOf("ero-$eroId", "ero-vc-admin-$eroId")
): String =
    Jwts.builder()
        .setSubject(UUID.randomUUID().toString())
        .setClaims(
            mapOf(
                "cognito:groups" to groups,
                "email" to email
            )
        )
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
        .signWith(RsaKeyPair.privateKey, SignatureAlgorithm.RS256)
        .compact()
