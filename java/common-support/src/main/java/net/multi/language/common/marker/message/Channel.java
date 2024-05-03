package net.multi.language.common.marker.message;

// generated from the APIBuilder JSON files, used throughout the entire system
public enum Channel {

    // Order channels
    CHARGE_ORDER(Channels.Commands.CHARGE_ORDER),
    CREATE_ORDER(Channels.Commands.CREATE_ORDER),
    ORDER_CREATED(Channels.Events.ORDER_CREATED),

    ORDER_CHARGED(Channels.Events.ORDER_CHARGED),
    ORDER_NOT_CHARGED(Channels.Events.ORDER_NOT_CHARGED),
    ;

    private final String channelName;

    Channel(final String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
