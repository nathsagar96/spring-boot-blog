package dev.sagar.wordsmith.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JWT token operations.
 *
 * <p>This class provides functionality to generate, validate, and extract information from JWT tokens.</p>
 */
@Service
public class JwtService {

    @Value("${jwt.secret:secret}")
    private String jwtSecret;

    @Value("${jwt.expiry:900000}")
    private long jwtExpiry;

    /**
     * Generates a JWT token using the provided claims and user details.
     *
     * @param claims      the claims to include in the token.
     * @param userDetails the details of the user for whom the token is being generated.
     * @return the generated JWT token as a String.
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiry);
    }

    /**
     * Builds a JWT token with the specified claims, user details, and expiration time.
     *
     * @param extraClaims   additional claims to include in the token.
     * @param userDetails   the user details for the token.
     * @param jwtExpiration the expiration time for the token in milliseconds.
     * @return the generated JWT token as a String.
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Long jwtExpiration) {
        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Retrieves the signing key used to create the JWT.
     *
     * @return the signing key as a Key object.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates the provided JWT token against the user details.
     *
     * @param jwtToken    the JWT token to validate.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String userName = extractUsername(jwtToken);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks if the given JWT token has expired.
     *
     * @param jwtToken the JWT token to check.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param jwtToken the JWT token from which to extract the expiration date.
     * @return the expiration date as a Date object.
     */
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param jwtToken the JWT token from which to extract the username.
     * @return the username as a String.
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using a provided resolver function.
     *
     * @param jwtToken      the JWT token from which to extract the claim.
     * @param claimResolver the function to extract the claim.
     * @param <T>           the type of the claim.
     * @return the extracted claim of type T.
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param jwtToken the JWT token from which to extract all claims.
     * @return the extracted claims as a Claims object.
     * @throws SignatureException if the JWT signature validation fails.
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}
