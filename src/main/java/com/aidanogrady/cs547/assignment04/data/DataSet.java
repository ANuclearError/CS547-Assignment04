package com.aidanogrady.cs547.assignment04.data;

import java.util.ArrayList;
import java.util.List;

/**
 *  The dataset is the data extacted from the file. It contains all the
 *  information in the file.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class DataSet {
    /**
     * The name of the dataset.
     */
    private String name;

    /**
     * All attributes that were extracted from the file.
     */
    private List<String> attributes;

    /**
     * All records extracted from the file.
     */
    private List<DataRecord> records;

    /**
     * Constructs a new DataSet with empty attributes and records list.
     *
     * @param name - the name of the dataset
     */
    public DataSet(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    /**
     * Constructs a new DataSet with the given name and attributes, and an empty
     * record list.
     *
     * @param name - the name of the dataset
     * @param attributes - the attributes of the dataset
     */
    public DataSet(String name, List<String> attributes) {
        this.name = name;
        this.attributes = attributes;
        this.records = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<DataRecord> getRecords() {
        return records;
    }

    public void setRecords(List<DataRecord> records) {
        this.records = records;
    }

    /**
     * Adds a new attribute to the attribute list and returns the success of the
     * addition.
     *
     * @param attribute the attribute to be added
     * @return true if addition successful, false otherwise
     */
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    /**
     * Returns the attribute at given index.
     *
     * @param index the index being searched for
     * @return null if  index is invalid, otherwise attribute at given index.
     */
    public String getAttribute(int index) {
        if (index < 0 || index >= attributes.size())
            return null;
        return attributes.get(index);
    }

    /**
     * Adds a new record to the records list and returns the success of the
     * addition.
     *
     * @param record the record to be added
     * @return true if addition successful, false otherwise
     */
    public boolean addRecord(DataRecord record) {
        return records.add(record);
    }

    /**
     * Returns the record at the given index.
     *
     * @param index the index being asked for
     * @return null if index is invalid, otherwise record at given index.
     */
    public DataRecord getRecord(int index) {
        if (index < 0 || index >= records.size())
            return null;
        return records.get(index);
    }
}
