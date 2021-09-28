package com.shpp.p2p.cs.istuzhuk.namesurfer;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class NameSurferDataBase implements NameSurferConstants {

    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    ArrayList<String> dataBase = new ArrayList<>();

    public NameSurferDataBase(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String entry;
        while ((entry = br.readLine()) != null) {
            dataBase.add(entry);
        }
        br.close();
    }

    /* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        for (String line : dataBase) {
            if (line.toLowerCase().startsWith(name.toLowerCase()))
                return new NameSurferEntry(line);
        }
        return null;
    }
}

