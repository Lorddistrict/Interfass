package io.realmit.interfass.api.controller;

import io.realmit.interfass.api.dto.GiveItemRequest;
import io.realmit.interfass.api.service.GiveItemService;

public final class GiveItemController {

    private final GiveItemService giveItemService;

    public GiveItemController(GiveItemService giveItemService) {
        this.giveItemService = giveItemService;
    }

    public String handle(GiveItemRequest request) {
        giveItemService.giveItem(request);

        return "Giving " + request.amount() + " " + request.item() + " to " + request.player();
    }
}
