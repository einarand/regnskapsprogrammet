package org.einar.regnskap.dbupdater;

import org.einar.regnskap.Category;
import org.einar.regnskap.parser.CategoryParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by
 * User: einahage
 * Date: 2/15/14
 * Time: 3:00 PM
 */
public class CategoryReader {

    public static Collection<Category> read(String fileName) throws NumberFormatException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = null;
            Collection<Category> categories = new ArrayList<Category>();
            while ((line = br.readLine()) != null) {
                categories.add(CategoryParser.parse(line));
            }
            return categories;
        } catch (IOException e) {
            throw new RuntimeException("Could not load categories, " +fileName, e);
        }
    }

}
