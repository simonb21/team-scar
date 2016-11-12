package config;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Config {
    public static final String SERVER_NAME;
    public static final int PORT = 2100;

    static {
        try {
            SERVER_NAME = InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            throw new Error("Failed to retrieve Server details.", e);
        }
    }
}
