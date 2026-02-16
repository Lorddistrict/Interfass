package io.realmit.interfass.api.controller;

import io.realmit.interfass.api.service.PrintMessageService;

public final class SayHelloController {

    private final PrintMessageService printMessageService;

    public SayHelloController(PrintMessageService printMessageService) {
        this.printMessageService = printMessageService;
    }

    public void handle() {
        printMessageService.printMessage();
    }
}
