package net.multi.language.common.marker.message;

public @interface MessageDetails {
    Service publisher();

    Service[] subscribers();

    Channel channel();
}
