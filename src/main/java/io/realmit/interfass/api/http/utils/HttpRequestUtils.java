
package io.realmit.interfass.api.http.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static io.realmit.interfass.api.http.utils.HttpResponseUtils.sendText;

public final class HttpRequestUtils {

    private HttpRequestUtils() {
    }

    public static boolean validateRequestMethod(HttpExchange exchange, String method) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase(method)) {
            sendText(exchange, 405, "Method Not Allowed (use " + method + ")");

            return false;
        }

        return true;
    }
}
