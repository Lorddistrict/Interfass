package io.realmit.interfass.api.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.realmit.interfass.api.controller.SayHelloController;

import java.io.IOException;

import static io.realmit.interfass.api.http.utils.HttpResponseUtils.sendText;
import static io.realmit.interfass.api.http.utils.HttpRequestUtils.validateRequestMethod;

public final class SayHelloHandler implements HttpHandler {

    private final SayHelloController controller;

    public SayHelloHandler(SayHelloController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!validateRequestMethod(exchange, "GET")) {
            return;
        }

        controller.handle();
        sendText(exchange, 200, null);
    }
}
