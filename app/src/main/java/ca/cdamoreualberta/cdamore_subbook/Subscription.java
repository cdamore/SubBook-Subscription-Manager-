/*
 * Subscription
 *
 * February, 3, 2018
 *
 * Copyright 2018...
 */

package ca.cdamoreualberta.cdamore_subbook;

/**
 * Represents a users subscription. Has name, date, charge, and optional comment
 *
 * @author Colin D'Amore
 */

public class Subscription {
    private String name;
    private String date;
    private String charge;
    private String comment;

    /**
     * Constructors for subscription that set the name, date, charge, and an optional
     * parameter for a comment
     *
     * @param name
     * @param date
     * @param charge
     */

    Subscription(String name, String date, String charge) {
        this.name = name;
        this.date = date;
        this.charge = charge;
    }

    Subscription(String name, String date, String charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }

    /**
     * Getters for name, date, charge, and comment
     *
     * @return returns String of item called
     */

    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getCharge() {
        return  '$' + charge;
    }
    public String getComment() { return comment; }

    /**
     * Setters for name, date, charge, and comment
     *
     * param -- the item you wish to set
     */
    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setCharge(String charge) {this.charge = charge; }
    public void setComment(String comment) { this.comment = comment; }

    /**
     * Overrides default toString() to return name instead of object default
     *
     * @return returns name
     */
    @Override
    public String toString() {
        return name;
    }
}
