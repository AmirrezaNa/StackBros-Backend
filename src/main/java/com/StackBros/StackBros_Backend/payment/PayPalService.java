package com.StackBros.StackBros_Backend.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class PayPalService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.base-url}")
    private String baseUrl;

    private PayPalAccessToken cachedToken;

    // --- 1. Authentication (server-to-server, not visible to the customer) ---

    private synchronized String getAccessToken() throws Exception {
        if (cachedToken != null && !cachedToken.isExpired()) {
            return cachedToken.token();
        }

        String credentials = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/v1/oauth2/token"))
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode json = objectMapper.readTree(response.body());

        if (!json.has("access_token")) {
            throw new IllegalStateException("PayPal auth failed: " + response.body());
        }

        String token = json.get("access_token").asText();
        int expiresInSeconds = json.get("expires_in").asInt();
        cachedToken = new PayPalAccessToken(token, Instant.now().plusSeconds(expiresInSeconds));

        return token;
    }

    // --- 2. Create a PayPal order for a given amount ---

    public record PayPalOrderCreation(String paypalOrderId, String approveUrl) {}

    public PayPalOrderCreation createOrder(Long ourOrderId, BigDecimal amount, String currency) throws Exception {
        String token = getAccessToken();

        String body = """
            {
              "intent": "CAPTURE",
              "purchase_units": [{
                "reference_id": "%s",
                "amount": { "currency_code": "%s", "value": "%s" }
              }]
            }
            """.formatted(ourOrderId, currency, amount.setScale(2));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/v2/checkout/orders"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode json = objectMapper.readTree(response.body());

        if (!json.has("id")) {
            throw new IllegalStateException("PayPal order creation failed: " + response.body());
        }

        String paypalOrderId = json.get("id").asText();

        String approveUrl = null;
        for (JsonNode link : json.get("links")) {
            if ("approve".equals(link.get("rel").asText())) {
                approveUrl = link.get("href").asText();
                break;
            }
        }

        return new PayPalOrderCreation(paypalOrderId, approveUrl);
    }

    // --- 3. Capture (actually collect) the payment after customer approval ---

    public boolean captureOrder(String paypalOrderId) throws Exception {
        String token = getAccessToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/v2/checkout/orders/" + paypalOrderId + "/capture"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode json = objectMapper.readTree(response.body());

        String status = json.has("status") ? json.get("status").asText() : "UNKNOWN";
        return "COMPLETED".equals(status);
    }
}