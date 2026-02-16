package io.realmit.interfass.api.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.realmit.interfass.api.controller.GiveItemController;
import io.realmit.interfass.api.dto.GiveItemRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.realmit.interfass.api.http.utils.HttpResponseUtils.sendText;
import static io.realmit.interfass.api.http.utils.HttpRequestUtils.validateRequestMethod;

public final class GiveItemHandler implements HttpHandler {

    private final GiveItemController controller;

    public GiveItemHandler(GiveItemController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!validateRequestMethod(exchange, "POST")) {
            return;
        }

        String body = readBody(exchange);

        String playerName = extractJsonValue(body, "player");
        String itemName = extractJsonValue(body, "item");
        String amountStr = extractJsonValue(body, "amount");

        if (playerName == null || itemName == null || amountStr == null) {
            sendText(exchange, 400, "Missing player/item/amount");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            sendText(exchange, 400, "amount must be an integer");
            return;
        }

        GiveItemRequest request = new GiveItemRequest(playerName, itemName, amount);
        String message = controller.handle(request);

        sendText(exchange, 200, message);
    }

    private String readBody(HttpExchange exchange) throws IOException {
        try (InputStream in = exchange.getRequestBody()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in.transferTo(out);
            return out.toString(StandardCharsets.UTF_8);
        }
    }

    // Very naive parser, just for the tiny example.
    private String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\"";
        int i = json.indexOf(pattern);
        if (i == -1) return null;
        int colon = json.indexOf(':', i + pattern.length());
        if (colon == -1) return null;

        int pos = colon + 1;
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            pos++;
        }

        if (pos < json.length() && json.charAt(pos) == '"') {
            int secondQuote = json.indexOf('"', pos + 1);
            if (secondQuote == -1) return null;
            return json.substring(pos + 1, secondQuote);
        } else {
            int end = pos;
            while (end < json.length()
                    && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) {
                end++;
            }
            if (end == pos) return null;
            return json.substring(pos, end);
        }
    }
}
