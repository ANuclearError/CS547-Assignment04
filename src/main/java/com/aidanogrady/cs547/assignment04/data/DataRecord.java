package com.aidanogrady.cs547.assignment04.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A data record is an individual item that appears in a .arff file. Each set
 * in the data contains various attributes as defined in the file, as well as an
 * 'effort' that represents the goal to work towards.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class DataRecord {
    /**
     * The attributes that make up this record.
     */
    private Map<String, Double> attributes;

    /**
     * The actual cost to be calculated by the genetic program.
     */
    private double effort;

    /**
     * Constructs a new data record.
     *
     * @param effort - the actual cost extracted from file
     */
    public DataRecord(double effort) {
        this.attributes = new HashMap<>();
        this.effort = effort;
    }

    /**
     * Puts the given key and value into the attributes map.
     *
     * @param key - the attribute to be added
     * @param value - the value of that record
     */
    public void put(String key, double value) {
        attributes.put(key, value);
    }

    /**
     * Returns the value the given key maps to.
     *
     * @param key - the attribute being desired
     * @return value key maps to, null if not found.
     */
    public double get(String key) {
        return attributes.get(key);
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Double> attributes) {
        this.attributes = attributes;
    }

    public double getEffort() {
        return effort;
    }

    public void setEffort(double effort) {
        this.effort = effort;
    }
}
