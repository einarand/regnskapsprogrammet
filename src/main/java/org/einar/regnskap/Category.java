package org.einar.regnskap;

import java.util.ArrayList;
import java.util.Collection;

public final class Category {

    public static final Category INNSKUDD = new Category("Innskudd");
    public static Category UNKNOWN = new Category("Unknown");

	private final String name;
	private final Collection<String> strings;
	
	public String getName() {
		return name;
	}
	
	public Collection<String> getStrings() {
		return strings;
	}

    public Category(String name) {
        this(name, new ArrayList<String>());
    }

	public Category(String name, Collection<String> strings) {
		this.name = name;		
		this.strings = strings;
	}
	
	public boolean containsText(String string) {
		if (string == null || strings == null) { return false; }
		for (String str : strings) {
			if (string.toLowerCase().contains(str)) {
				return true;
			}
		}
		return false;		
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
	public String toString() {
		return name; 
	}
		
}
