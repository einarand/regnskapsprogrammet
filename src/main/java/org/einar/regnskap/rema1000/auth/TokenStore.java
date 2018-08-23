package org.einar.regnskap.rema1000.auth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Optional;


public class TokenStore {

    private static final String ACCESS_TOKEN_FILE_NAME = "access-token.ser";
    private static final String MEDIA_STORE_TOKEN = "media-store-token.ser";
    private static TokenStore instance;

    private Optional<RemaAccessToken> accessToken;
    private Optional<MediaStoreToken> mediaStoreToken;

    private TokenStore() {
        accessToken = readTokenFromFile(ACCESS_TOKEN_FILE_NAME);
        mediaStoreToken = readTokenFromFile(MEDIA_STORE_TOKEN);
    }

    public static TokenStore getInstance() {
        if (instance == null) {
            instance = new TokenStore();
        }
        return instance;
    }

    public boolean accessTokenIsValid() {
        return accessToken.filter(a -> !a.isExpired()).isPresent();
    }

    public Optional<RemaAccessToken> getAccessToken() {
        return accessToken;
    }

    public void saveAccessToken(RemaAccessToken accessToken) {
        this.accessToken = Optional.of(accessToken);
        accessToken.setExpires(accessToken.getExpiresIn()); //TODO fix this ugly shit
        writeTokenToFile(accessToken, ACCESS_TOKEN_FILE_NAME);
    }

    public Optional<MediaStoreToken> getMediaStoreToken() {
        return mediaStoreToken;
    }

    public void saveMediaStoreToken(MediaStoreToken mediaStoreToken) {
        this.mediaStoreToken = Optional.of(mediaStoreToken);
        writeTokenToFile(mediaStoreToken, MEDIA_STORE_TOKEN);
    }

    private static <T> Optional<T> readTokenFromFile(String filename) {
        try (InputStream file = new FileInputStream(filename)) {
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                return Optional.ofNullable((T) input.readObject());
            } finally {
                input.close();
                buffer.close();
                file.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    private static <T> void writeTokenToFile(T token, String fileName) {
        try {
            OutputStream file = new FileOutputStream(fileName);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(token);
            output.close();
            buffer.close();
            file.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
