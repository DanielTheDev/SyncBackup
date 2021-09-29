package io.github.danielthedev.syncbackup;

public class SyncBackup {

    private final Properties properties = Properties.getInstance();
    private final SFTPClient client = new SFTPClient(properties);

    public SyncBackup() {}

    public static void main(String[] args) {
        SyncBackup syncBackup = new SyncBackup();
        syncBackup.start();
    }

    public void start() {
        this.client.connect();
        System.out.println("good");
        this.client.disconnect();
    }
}
