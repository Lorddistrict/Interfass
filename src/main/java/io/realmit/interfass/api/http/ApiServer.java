package io.realmit.interfass.api.http;

import com.sun.net.httpserver.HttpServer;
import io.realmit.interfass.api.controller.SayHelloController;
import io.realmit.interfass.api.http.handlers.GiveItemHandler;
import io.realmit.interfass.api.controller.GiveItemController;
import io.realmit.interfass.api.http.handlers.SayHelloHandler;
import io.realmit.interfass.api.service.GiveItemService;
import io.realmit.interfass.api.service.PrintMessageService;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ApiServer {

    private final Plugin plugin;
    private final int port;
    private HttpServer server;
    private ExecutorService executor;

    public ApiServer(Plugin plugin, int port) {
        this.plugin = plugin;
        this.port = port;
    }

    public void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        this.server = HttpServer.create(address, 0);

        GiveItemService giveItemService = new GiveItemService(plugin);
        GiveItemController giveItemController = new GiveItemController(giveItemService);
        GiveItemHandler giveItemHandler = new GiveItemHandler(giveItemController);

        PrintMessageService printMessageService = new PrintMessageService(plugin);
        SayHelloController sayHelloController = new SayHelloController(printMessageService);
        SayHelloHandler sayHelloHandler = new SayHelloHandler(sayHelloController);

        // HTTP routing
        this.server.createContext("/api/give", giveItemHandler);
        this.server.createContext("/api/hello", sayHelloHandler);

        this.executor = Executors.newCachedThreadPool();
        this.server.setExecutor(executor);
        this.server.start();

        plugin.getLogger().info("API server started on port " + port);
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
        plugin.getLogger().info("API server stopped");
    }
}
