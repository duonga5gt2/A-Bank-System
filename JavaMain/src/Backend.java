import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backend {
	// Register Group
	public boolean isIdUsed(long id) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load and register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "duongngoquy");
            
            // SQL query to check if the ID exists in the "usedid" table
            String query = "SELECT COUNT(*) FROM usedid WHERE used_id = ?";
            
            // Create a PreparedStatement  
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, id);
            
            // Execute the query
            rs = pstmt.executeQuery();
            
            // Check if the ID is used
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } finally {
            // Close the resources in the finally block to ensure they are closed even if an exception occurs
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
	
	
        
	
	
	public double getCurrentBalance(long id) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double currentBalance = 0.0;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			
			String statememt = "SELECT * FROM accounts WHERE idCustomer = ?";
			
			pstmt = con.prepareStatement(statememt);
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
	            currentBalance = rs.getDouble("Balance");
	        }
        }
        finally { 
        	 try {
                 if (rs != null) rs.close();
                 if (pstmt != null) pstmt.close();
                 if (con != null) con.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
        	 
        }
        return currentBalance;
        
        
	}
	
	
	
	public void InfoIntoDB(long id, String first_name, String last_name, String email, String phone_number, String login_password) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		
		try {
			// Debug: Print input values
            System.out.println("Debug: Input values");
            System.out.println("ID: " + id);
            System.out.println("First Name: " + first_name);
            System.out.println("Last Name: " + last_name);
            System.out.println("Email: " + email);
            System.out.println("Phone Number: " + phone_number);
            
            System.out.println("Login Password: " + login_password);

            Customer customer = new Customer(id, first_name, last_name, email, phone_number, login_password);

            // Debug: Print customer values
            System.out.println("Debug: Customer values");
            System.out.println("Customer ID: " + customer.getId());
            System.out.println("Customer First Name: " + customer.getFirstName());
            System.out.println("Customer Last Name: " + customer.getLastName());
            System.out.println("Customer Email: " + customer.getEmail());
            System.out.println("Customer Phone Number: " + customer.getPhoneNumber());
            System.out.println("Customer Login Password: " + customer.getLogin_password());

			//SQL Code Below
			
			Class.forName("com.mysql.cj.jdbc.Driver");
				
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			System.out.println("Connected");
			
			
			
			String insertSQL = "INSERT INTO customers (idCustomer, FirstName, LastName, email, PhoneNumber, LoginPassword) VALUES (?, ?, ?, ?, ?, ?)";
			
			
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setLong(1, customer.getId());
			pstmt.setString(2, customer.getFirstName());
			pstmt.setString(3, customer.getLastName());
			pstmt.setString(4, customer.getEmail());
			pstmt.setString(5, customer.getPhoneNumber());
			pstmt.setString(6, customer.getLogin_password());
			
			pstmt.executeUpdate();
			System.out.println("Inserted!");
			
			String insertID = "INSERT INTO usedid VALUES (?)";
			
			
			pstmt = con.prepareStatement(insertID);
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();
			
			System.out.println("ID Inserted");
			
			String insertAccount = "INSERT INTO accounts VALUES (0, ?)";
			
			pstmt = con.prepareStatement(insertAccount);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			
			System.out.println("Accounts Updated");
			
		}
		
		
		finally {
			try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		
	}
	boolean getMainWindow(long id) throws Exception {
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    FrontEnd front_end;

	    try {
	        // Load and register MySQL JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        // Establish the connection
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "duongngoquy");
	        
	        // SQL query to retrieve multiple columns
	        String query = "SELECT FirstName, LastName, email, PhoneNumber FROM customers WHERE idCustomer = ?";
	        
	        // Create a PreparedStatement
	        pstmt = con.prepareStatement(query);
	        pstmt.setLong(1, id);
	        
	        // Execute the query
	        rs = pstmt.executeQuery();
	        
	        // Variables to store the retrieved data
	        String firstName = null;
	        String lastName = null;
	        String email = null;
	        String phoneNumber = null;
	        
	        // Retrieve data from the ResultSet
	        if (rs.next()) {
	            firstName = rs.getString("FirstName");
	            lastName = rs.getString("LastName");
	            email = rs.getString("email");
	            phoneNumber = rs.getString("PhoneNumber");
	            double currentBalance = getCurrentBalance(id);
	            front_end = new FrontEnd();
	            front_end.openMainWindow(id, firstName,lastName,email,phoneNumber, currentBalance);
	            
//	            MainWindow mainWindow = new MainWindow(id, firstName, lastName, email, phoneNumber, currentBalance);
//	            mainWindow.setVisible(true);
//	            dispose();
	            return true;
	        } 
	        else {
	        	System.out.println("Cannot Find Details");
	        	
	        }
	    } finally {
	        // Close the resources in the finally block to ensure they are closed even if an exception occurs
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
	
	//Login Group
	public boolean LoginDetail(String phone_number, String password) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			String statement = "SELECT * FROM customers WHERE PhoneNumber = ? AND LoginPassword = ?";
			
			pstmt = con.prepareStatement(statement);
			pstmt.setString(1, phone_number);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				System.out.println("Login Successful. User ID: " + rs.getLong("idCustomer"));
				getMainWindow(rs.getLong("idCustomer"));
				return true;
			}
			else {
				System.out.println("Login Failed. Invalid phone number or password.");
				
			}
        }
        finally {
        	try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
	}
		//Email
		//Password
			//Authentication by email, phone number (by choice)
	
	
	//Details management
	public void firstNameChange(String new_name, long id) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //SQL CODE 
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			String sql = "UPDATE customers SET FirstName = ? WHERE idCustomer = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_name);
			pstmt.setLong(2, id);
			
			int affected = pstmt.executeUpdate();
			System.out.println("Updated: "+ affected);
        }
        finally {
        	try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void lastNameChange(String new_last, long id) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //SQL CODE 
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			String sql = "UPDATE customers SET FirstName = ? WHERE idCustomer = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_last);
			pstmt.setLong(2, id);
			
			int affected = pstmt.executeUpdate();
			System.out.println("Updated: "+ affected);
        }
        finally {
        	try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
	}
	public void emailChange(String new_email, long id) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //SQL CODE 
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			String sql = "UPDATE customers SET FirstName = ? WHERE idCustomer = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_email);
			pstmt.setLong(2, id);
			
			int affected = pstmt.executeUpdate();
			System.out.println("Updated: "+ affected);
        }
        finally {
        	try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	public void phoneChange(String new_phone, long id) throws Exception {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //SQL CODE 
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","duongngoquy");
			String sql = "UPDATE customers SET FirstName = ? WHERE idCustomer = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_phone);
			pstmt.setLong(2, id);
			
			int affected = pstmt.executeUpdate();
			System.out.println("Updated: "+ affected);
        }
        finally {
        	try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
	}
	public boolean isValidName(String first_name, String last_name) {
        // Regex pattern for valid name (alphabetic characters, optional spaces or hyphens)
        String regex = "^[a-zA-Z]+([\\s-][a-zA-Z]+)*$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create matcher object
        Matcher matcher = pattern.matcher(first_name);
        Matcher matcher1 = pattern.matcher(last_name);

        // Perform matching
        boolean cac = matcher.matches() && matcher1.matches();
        if (cac == true) {
        	System.out.println("Correct Name");
        	return true;
        }
        else {
        	System.out.println("Incorrect Name");
        	return false;
        }
	}
	
	public boolean isValidEmail(String email) {
		String regex = "^(.+)@(.+)$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create matcher object
        Matcher matcher = pattern.matcher(email);

        // Perform matching
        boolean cac = matcher.matches();
        if (cac == true) {
        	System.out.println("Correct Email");
        	return true;
        }
        else {
        	System.out.println("Email lao");
        	return false;
        }
	}
	
	public boolean isValidNumber(String phone_number) {
		if (phone_number.length() <= 12) {
			System.out.println("Valid Number");
			return true;
		}
		System.out.println("Invalid Number");
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Main function
	public boolean checkValidIDBeforeTransactions(long sender, long receiver) throws Exception {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean senderValid = false;
        boolean receiverValid = false;
        
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "duongngoquy");
            
            // Query to check if sender exists
            String senderQuery = "SELECT COUNT(*) FROM accounts WHERE idCustomer = ?";
            pstmt = con.prepareStatement(senderQuery);
            pstmt.setLong(1, sender);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                senderValid = rs.getInt(1) > 0;
            }
            
            rs.close();
            pstmt.close();
            
            // Query to check if receiver exists
            String receiverQuery = "SELECT COUNT(*) FROM accounts WHERE idCustomer = ?";
            pstmt = con.prepareStatement(receiverQuery);
            pstmt.setLong(1, receiver);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                receiverValid = rs.getInt(1) > 0;
            }
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return senderValid && receiverValid;
    }
	
	
	public boolean Transactions(boolean valid, long sender, long receiver, double amount) throws Exception {
		Connection con = null;
	    PreparedStatement pstmt = null;

	    if (valid) {
	        try {
	            // Load the MySQL JDBC driver
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Establish a connection to the database
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "duongngoquy");
	            con.setAutoCommit(false); // Start transaction

	            // Subtract amount from sender's account
	            String senderUpdate = "UPDATE accounts SET Balance = Balance - ? WHERE idCustomer = ?";
	            pstmt = con.prepareStatement(senderUpdate);
	            pstmt.setDouble(1, amount);
	            pstmt.setLong(2, sender);
	            int rowsAffectedSender = pstmt.executeUpdate();

	            // Check if the update for the sender's account was successful
	            if (rowsAffectedSender == 0) {
	                con.rollback();
	                System.out.println("Nothing updated-1");
	                return false;
	            }

	            // Add amount to receiver's account
	            String receiverUpdate = "UPDATE accounts SET Balance = Balance + ? WHERE idCustomer = ?";
	            pstmt = con.prepareStatement(receiverUpdate);
	            pstmt.setDouble(1, amount);
	            pstmt.setLong(2, receiver);
	            int rowsAffectedReceiver = pstmt.executeUpdate();

	            // Check if the update for the receiver's account was successful
	            if (rowsAffectedReceiver == 0) {
	                con.rollback();
	                System.out.println("Nothing updated-2");
	                return false;
	            }

	            // Commit the transaction
	            con.commit();
	            return true;

	        } catch (Exception e) {
	            if (con != null) {
	                con.rollback(); // Rollback the transaction in case of error
	            }
	            e.printStackTrace();
	            throw e;
	        } finally {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            if (con != null) {
	                con.setAutoCommit(true); // Reset auto-commit to true
	                con.close();
	            }
	        }
	    }

	    return false;
	}

	public boolean sufficientBalance(long sender_id, double amount) throws Exception {
		boolean sender_balance_check = this.getCurrentBalance(sender_id) >= amount;
		return sender_balance_check;
		
		
		
	}

	
	
	
}


