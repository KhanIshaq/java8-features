/**
 * 
 */
package features;

/**
 * @author nsa
 *
 */

//Exercise 3-27. A simple Book class
//Exercise 4-13. A simple POJO representing a book
public class Book {

	private Integer id; private String title;
	private double price;
	
	public Book(Integer id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Book(Integer id, String title, double price) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book {id=" + id + ", title=" + title + ", price=" + price + "}";
	}
	
	
	
}
