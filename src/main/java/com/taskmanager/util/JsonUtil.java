package com.taskmanager.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.taskmanager.domain.exception.DataPersistenceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for JSON serialization and deserialization.
 * Demonstrates utility class pattern and proper resource handling.
 */
public final class JsonUtil {
    private static final Gson GSON = createGson();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private JsonUtil() {
        // Prevent instantiation
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * Create configured Gson instance with custom type adapters.
     */
    private static Gson createGson() {
        return new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, 
                (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> 
                    new JsonPrimitive(src.format(DATE_FORMATTER)))
            .registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                    LocalDateTime.parse(json.getAsString(), DATE_FORMATTER))
            .create();
    }

    /**
     * Convert object to JSON string.
     * 
     * @param object the object to serialize
     * @return JSON string
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * Convert JSON string to object.
     * 
     * @param json the JSON string
     * @param clazz the target class
     * @return deserialized object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * Write object to JSON file.
     * 
     * @param object the object to write
     * @param path the file path
     */
    public static void writeToFile(Object object, Path path) {
        try {
            // Ensure parent directory exists
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            String json = toJson(object);
            Files.writeString(path, json);
        } catch (IOException e) {
            throw new DataPersistenceException("Failed to write to file: " + path, e);
        }
    }

    /**
     * Read object from JSON file.
     * 
     * @param path the file path
     * @param clazz the target class
     * @return deserialized object, or null if file doesn't exist
     */
    public static <T> T readFromFile(Path path, Class<T> clazz) {
        try {
            if (!Files.exists(path)) {
                return null;
            }
            
            String json = Files.readString(path);
            return fromJson(json, clazz);
        } catch (IOException e) {
            throw new DataPersistenceException("Failed to read from file: " + path, e);
        }
    }
}

