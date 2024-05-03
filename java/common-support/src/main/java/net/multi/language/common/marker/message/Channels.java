package net.multi.language.common.marker.message;

public final class Channels {

    // commands
    public static class Commands {
        public static final String CHARGE_ORDER = "charge_order";
        public static final String CREATE_ORDER = "create_order";
    }

    // events
    public static class Events {
        public static final String ORDER_CREATED = "order_created";
        public static final String ORDER_CHARGED = "order_charged";
        public static final String ORDER_NOT_CHARGED = "order_not_charged";
    }

    // queries
    public static class Queries {
        public static final String FIND_ORDER = "find_order";
    }

    // deny instantiation
    private Channels() {}
}
