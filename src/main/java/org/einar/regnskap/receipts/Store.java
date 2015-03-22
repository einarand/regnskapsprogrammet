package org.einar.regnskap.receipts;

/**
 * Created by
 * User: einahage
 * Date: 2/16/14
 * Time: 8:26 PM
 */
public class Store {

    private final String name;
    private final String department;

    public Store(String name) {
        this(name, null);
    }

    public Store(String name, String department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                "department='" + department + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
