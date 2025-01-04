package com.wonkglorg.utilitylib.serializer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Contract;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for Base64 encoding and decoding of objects
 * <p>Author: Wonkglorg</p>
 */
public class Base64Converter {
    private static final Logger log = Logger.getLogger(Base64Converter.class.getName());

    /**
     * Converts an object to a Base64 encoded string.
     *
     * @param obj The object to encode.
     * @return Base64 encoded string or null if encoding fails.
     */
    @Contract(pure = true, value = "null -> null")
    public static String toBase64(final Object obj) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(obj);
            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to convert object to Base64: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Converts a Base64 encoded string back to an object.
     *
     * @param str Base64 encoded string.
     * @return Decoded object or null if decoding fails.
     */
    @Contract(pure = true, value = "null -> null")
    public static Object fromBase64(final String str) {
        byte[] data = Base64.getDecoder().decode(str);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {

            return ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Failed to decode Base64 to object: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Serializes an array of {@link ItemStack} to a Base64 encoded string.
     *
     * @param items Array of {@link ItemStack}.
     * @return Base64 encoded string or null if serialization fails.
     */
    @Contract(pure = true)
    public static String itemStackArrayToBase64(final ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeInt(items.length);
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to serialize ItemStack array to Base64: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Serializes a single {@link ItemStack} to a Base64 encoded string.
     *
     * @param item {@link ItemStack}.
     * @return Base64 encoded string or null if serialization fails.
     */
    @Contract(pure = true, value = "null -> null")
    public static String itemToBase64(final ItemStack item) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeObject(item);
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to serialize ItemStack to Base64: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Deserializes a Base64 encoded string into an array of {@link ItemStack}.
     *
     * @param data Base64 encoded string.
     * @return Array of {@link ItemStack}, or null if deserialization fails.
     */
    @Contract(pure = true, value = "null -> null")
    public static ItemStack[] itemStackArrayFromBase64(final String data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            int length = dataInput.readInt();
            ItemStack[] items = new ItemStack[length];
            for (int i = 0; i < length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }
            return items;

        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Failed to deserialize Base64 to ItemStack array: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Deserializes a Base64 encoded string into a single {@link ItemStack}.
     *
     * @param data Base64 encoded string.
     * @return {@link ItemStack} or null if deserialization fails.
     */
    @Contract(pure = true, value = "null -> null")
    public static ItemStack itemFromBase64(final String data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            return (ItemStack) dataInput.readObject();

        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Failed to deserialize Base64 to ItemStack: " + e.getMessage(), e);
            return null;
        }
    }
}
