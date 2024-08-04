
public class Customer {
	private long id;
	private String first_name;
	private String last_name;
	private String email;
	private String phone_number;
	private String login_password;
	
	public Customer(long id, String first_name, String last_name, String email, String phone_number, String login_password) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone_number = phone_number;
		
		this.login_password = login_password;
	}
	
	public String show_detail() {
		return "ID: "+id+"\n"+"First Name"+ (String) first_name +"\n" + 
				(String) last_name + "\n" +phone_number + "\n" + (String) email;
	}
	
    // Getters and Setters (optional, for access)
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

	
	public String getLogin_password() {
		return login_password;
	}

	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}
	
}
