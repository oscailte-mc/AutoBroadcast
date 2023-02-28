/*
 * Copyright (c) 2023, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */
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
