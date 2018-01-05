package com.zheleznyakov.lingvo.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ZhConfigFactory {
    private static final String CONFIG_FILE = "application";
    private static Config config;

    private ZhConfigFactory() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static Config get() {
        if (config == null)
            initialiseConfig();
        return config;
    }

    private static void initialiseConfig() {
        config = ConfigFactory.empty()
                .withFallback(ConfigFactory.parseResourcesAnySyntax(CONFIG_FILE));
    }
}
