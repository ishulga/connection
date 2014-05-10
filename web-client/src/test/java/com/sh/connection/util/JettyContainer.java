package com.sh.connection.util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyContainer {
    Logger log = LoggerFactory.getLogger(JettyContainer.class);

    private static final int PORT = 1234;
    private static final String CONTEXT_ROOT = "/connection";
    public static final String URL = "http://localhost:" + PORT + CONTEXT_ROOT;
    private static Server server;

    public JettyContainer() throws Exception {
        log.debug("Starting jetty.");
        try {
            if (server != null && server.isRunning()) {
                log.debug("Server already running. Returning.");
                return;
            }
            server = new Server(PORT);
            WebAppContext h = new WebAppContext("src/main/webapp", CONTEXT_ROOT);
            h.setClassLoader(this.getClass().getClassLoader());
            h.setAttribute(
                    "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                    ".*jar");
            server.setHandler(h);
            server.start();
            log.info("Jetty started. Url: {}", URL);
        } catch (Exception e) {
            log.error("Jetty start failed.", e);
            throw new RuntimeException("Failed to start jetty container.", e);
        }
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public static void main(String[] args) throws Exception {
        JettyContainer c = new JettyContainer();
        c.join();
    }

}
