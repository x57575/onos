/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.store.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A distributed, eventually consistent map.
 *
 * This map does not offer read after writes consistency. Operations are
 * serialized via the timestamps issued by the clock service. If two updates
 * are in conflict, the update with the more recent timestamp will endure.
 *
 * The interface is mostly similar to {@link java.util.Map} with some minor
 * semantic changes and the addition of a listener framework (because the map
 * can be mutated by clients on other instances, not only through the local Java
 * API).
 *
 * Clients are expected to register an
 * {@link org.onosproject.store.impl.EventuallyConsistentMapListener} if they
 * are interested in receiving notifications of update to the map.
 */
public interface EventuallyConsistentMap<K, V> {

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return number of key-value mappings
     */
    public int size();

    /**
     * Returns true if this map is empty.
     *
     * @return true if this map is empty, otherwise false
     */
    public boolean isEmpty();

    /**
     * Returns true if the map contains a mapping for the specified key.
     *
     * @param key the key to check if this map contains
     * @return true if this map has a mapping for the key, otherwise false
     */
    public boolean containsKey(K key);

    /**
     * Returns true if the map contains a mapping from any key to the specified
     * value.
     *
     * @param value the value to check if this map has a mapping for
     * @return true if this map has a mapping to this value, otherwise false
     */
    public boolean containsValue(V value);

    /**
     * Returns the value mapped to the specified key.
     *
     * @param key the key to look up in this map
     * @return the value mapped to the key, or null if no mapping is found
     */
    public V get(K key);

    /**
     * Associates the specified value to the specified key in this map.
     * <p>
     * Note: this differs from the specification of {@link java.util.Map}
     * because it does not return the previous value associated with the key.
     * Clients are expected to register an
     * {@link org.onosproject.store.impl.EventuallyConsistentMapListener} if
     * they are interested in receiving notification of updates to the map.
     * </p>
     *
     * @param key the key to add a mapping for in this map
     * @param value the value to associate with the key in this map
     */
    public void put(K key, V value);

    /**
     * Removes the mapping associated with the specified key from the map.
     * <p>
     * Note: this differs from the specification of {@link java.util.Map}
     * because it does not return the previous value associated with the key.
     * Clients are expected to register an
     * {@link org.onosproject.store.impl.EventuallyConsistentMapListener} if
     * they are interested in receiving notification of updates to the map.
     * </p>
     *
     * @param key the key to remove the mapping for
     */
    public void remove(K key);

    /**
     * Adds mappings for all key-value pairs in the specified map to this map.
     * <p>
     * This will be more efficient in communication than calling individual put
     * operations.
     * </p>
     *
     * @param m a map of values to add to this map
     */
    public void putAll(Map<? extends K, ? extends V> m);

    /**
     * Removes all mappings from this map.
     */
    public void clear();

    /**
     * Returns a set of the keys in this map. Changes to the set are not
     * reflected back to the map.
     *
     * @return set of keys in the map
     */
    public Set<K> keySet();

    /**
     * Returns a collections of values in this map. Changes to the collection
     * are not reflected back to the map.
     *
     * @return collection of values in the map
     */
    public Collection<V> values();

    /**
     * Returns a set of mappings contained in this map. Changes to the set are
     * not reflected back to the map.
     *
     * @return set of key-value mappings in this map
     */
    public Set<Map.Entry<K, V>> entrySet();

    /**
     * Adds the specified listener to the map which will be notified whenever
     * the mappings in the map are changed.
     *
     * @param listener listener to register for events
     */
    public void addListener(EventuallyConsistentMapListener listener);

    /**
     * Removes the specified listener from the map such that it will no longer
     * receive change notifications.
     *
     * @param listener listener to deregister for events
     */
    public void removeListener(EventuallyConsistentMapListener listener);

    /**
     * Shuts down the map and breaks communication between different instances.
     * This allows the map objects to be cleaned up and garbage collected.
     * Calls to any methods on the map subsequent to calling destroy() will
     * throw a {@link java.lang.RuntimeException}.
     */
    public void destroy();
}
