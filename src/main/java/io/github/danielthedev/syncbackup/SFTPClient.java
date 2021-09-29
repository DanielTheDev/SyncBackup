package io.github.danielthedev.syncbackup;

import com.jcraft.jsch.*;

import java.util.Map;

public class SFTPClient {

    private static final String PROTOCOL = "sftp";

    private final java.util.Properties config = new java.util.Properties();
    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final String path;
    private JSch provider = new JSch();
    private Session session;
    private ChannelSftp channel;

    public SFTPClient(Properties properties) {
        this.hostname = properties.getValue("server.hostname");
        this.port = properties.getValue("server.port");
        this.username = properties.getValue("server.username");
        this.password = properties.getValue("server.password");
        this.path = properties.getValue("home-path");
        this.config.put("StrictHostKeyChecking", "no");
    }

    public void connect() {
        try {
            this.session = this.provider.getSession(this.username, this.hostname);
            session.setPassword(this.password);
            session.setConfig(this.config);
            session.connect();
            this.channel = (ChannelSftp) session.openChannel(PROTOCOL);
            this.channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if(this.channel != null && this.channel.isConnected()) {
            this.channel.exit();
            this.channel = null;
        }
        if(this.session != null && this.session.isConnected()) {
            this.session.disconnect();
            this.channel = null;
        }
    }

    public boolean isConnected() {
        return this.channel != null && this.session != null && this.channel.isConnected() && this.session.isConnected();
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
