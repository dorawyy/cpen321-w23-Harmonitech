package com.cpen321.tunematch;

// ChatGPT Usage: No
public class SessionUser {
    private String userId;
    private String userName;
    private boolean isHost; // Indicates if this user is the host of the session
    private boolean isListening; // Indicates if this user is currently listening to the session music

    // Constructor
    // ChatGPT Usage: No
    public SessionUser(String userId, String userName, boolean isHost, boolean isListening) {
        this.userId = userId;
        this.userName = userName;
        this.isHost = isHost;
        this.isListening = isListening;
    }

    // Getters and Setters
    // ChatGPT Usage: No
    public String getUserId() {
        return userId;
    }

    // ChatGPT Usage: No
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // ChatGPT Usage: No
    public String getUserName() {
        return userName;
    }

    // ChatGPT Usage: No
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // ChatGPT Usage: No
    public boolean isHost() {
        return isHost;
    }

    // ChatGPT Usage: No
    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    // ChatGPT Usage: No
    public boolean isListening() {
        return isListening;
    }

    // ChatGPT Usage: No
    public void setListening(boolean isListening) {
        this.isListening = isListening;
    }

    // Additional Methods
    // For example, you may have methods to manage user's actions within a session.
    // ChatGPT Usage: No
    public void toggleListening() {
        isListening = !isListening;
    }
}
