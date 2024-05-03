package net.multi.language.common.message;

import net.multi.language.common.marker.message.Channels;

public enum OutputBindings {
    ORDER_CREATED("orderCreatedProducer-out-0", Channels.Events.ORDER_CREATED),
    ORDER_CHARGED("orderChargedProducer-out-0", Channels.Events.ORDER_CHARGED),
    CHARGE_ORDER("chargeOrderProducer-out-0", Channels.Commands.CHARGE_ORDER);

    private final String bindingName;
    private final String channelName;

    OutputBindings(String bindingName, String channelName) {
        this.bindingName = bindingName;
        this.channelName = channelName;
    }

    public String getBindingName() {
        return bindingName;
    }

    public String getChannelName() {
        return channelName;
    }
}
