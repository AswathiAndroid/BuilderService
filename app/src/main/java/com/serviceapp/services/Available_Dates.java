package com.serviceapp.services;

import java.io.Serializable;

/**
 * Simple container object for Date data
 *
 */
public class Available_Dates implements Serializable{
    private String dates;

    public Available_Dates(String e) {
        dates = e;
    }

    public String getDates() { return dates; }
    
    @Override
    public String toString() { return dates; }

}
