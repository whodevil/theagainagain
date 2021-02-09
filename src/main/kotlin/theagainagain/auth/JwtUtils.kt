package theagainagain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier


class JwtUtils {

    val algorithm: Algorithm = Algorithm.HMAC256("secret")
    val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer("auth0")
            .build()

    fun getIdentity(authHeader: String) {
        var parts = authHeader.split(" ")
        assert(parts.size == 2)
        val jwt: DecodedJWT = verifier.verify(authHeader)
    }
}
