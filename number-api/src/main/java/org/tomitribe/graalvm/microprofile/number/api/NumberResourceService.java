package org.tomitribe.graalvm.microprofile.number.api;

import org.eclipse.microprofile.config.ConfigProvider;

import static spark.Spark.get;
import static spark.Spark.port;

public class NumberResourceService {
    public static void main(String[] args) throws Exception {
        final Integer port = ConfigProvider.getConfig().getOptionalValue("NUMBER_API_PORT", Integer.class).orElse(3001);
        port(port);
        System.out.println("Server running on port " + port);
        get("/number-api/number/generate", (req, res) -> "MV-" + (int) Math.floor((Math.random() * 9999999)) + 1);
    }
}
