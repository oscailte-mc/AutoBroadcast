package io.paradaux.autobroadcast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Various Random Number helper methods. Originally from FriendlyBot
 * @author Rían Errity
 * @since 2.0.0
 * */
public class RandomUtil {

    private final Random random;

    public RandomUtil() {
        this.random = new Random();
    }

    public RandomUtil(Random random) {
        this.random = random;
    }

    /**
     * Picks a random element from the provided collection, assuming we can't get by index, use an Iterator, otherwise use the index.
     * */
    @Nullable
    public <E> E fromCollection(Collection<? extends E> collection) {
        if (collection.size() == 0) {
            return null;
        }

        int index = random.nextInt(collection.size());

        if (collection instanceof List) {
            return ((List<? extends E>) collection).get(index);
        }

        Iterator<? extends E> iterator = collection.iterator();

        for (int i = 0; i < index; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Pick a random element from a JsonArray
     * */
    @Nullable
    public JsonElement fromJsonArray(JsonArray array) {
        if (array.size() == 0) {
            return null;
        }

        int index = random.nextInt(array.size());
        Iterator<JsonElement> iterator = array.iterator();

        for (int i = 0; i < index; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Picks a random number between the provided bounds
     * */
    @NonNull
    public int pickRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return random.nextInt((max - min) + 1) + min;
    }
}
