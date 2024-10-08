package cce_104Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class library_system extends JFrame {

	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet result = null;
    // Table to display book data
    private JTable bookTable;
    private DefaultTableModel bookTableModel;

    public library_system() {
        // Initialize the JFrame
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a toolbar with buttons
        JToolBar toolBar = new JToolBar();
        JButton addBookButton = new JButton("Add Book");
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton searchBookButton = new JButton("Search Book");
        JButton createAccountButton = new JButton("Create Account");

        toolBar.add(addBookButton);
        toolBar.add(issueBookButton);
        toolBar.add(returnBookButton);
        toolBar.add(searchBookButton);
        toolBar.add(createAccountButton);

        // Add action listeners to the buttons
        addBookButton.addActionListener(e -> showAddBookDialog());
        issueBookButton.addActionListener(e -> showIssueBookDialog());
        returnBookButton.addActionListener(e -> showReturnBookDialog());
        searchBookButton.addActionListener(e -> showSearchBookDialog());
        createAccountButton.addActionListener(e-> showCreateAccountDialog());
        

        // Create the table to display books
        bookTableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Year", "Genre", "Status"}, 0);
        bookTable = new JTable(bookTableModel);
       
        loadBooksData();

        // Add the components to the panel
        panel.add(toolBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // Add the main panel to the frame
        add(panel);
    }

    // Method to load books from the database and display in JTable
    private void loadBooksData() {
        String query = "SELECT * FROM Books";
        try (Connection conn = database_connector.getConnection(); // Get connection from database_connector
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            bookTableModel.setRowCount(0); // Clear existing data

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("year_published"),
                    rs.getString("genre"),
                    rs.getString("status")
                };
                bookTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //show dialog to create a student account
    private void showCreateAccountDialog() {
    	JDialog dialog = new JDialog(this, " Create student Account", true);
    	dialog.setSize(400, 300);
    	dialog.setLayout(new GridLayout(6,2));
    	
    	// form fields for students account
    	JLabel studentIdLabel = new JLabel("Student ID:");
    	JLabel firstnameLabel = new JLabel("FIrst Name:");
    	JLabel lastnameLabel = new JLabel("Last Name:");
    	JLabel phonenumberLabel = new JLabel("Phone Number");
    	JLabel emailLabel = new JLabel("Email");
    	
    	JTextField studentField = new JTextField();
    	JTextField firstnameField = new JTextField();
    	JTextField lastnameField = new JTextField();
    	JTextField PhonenumField = new JTextField();
    	JTextField emailField = new JTextField();
    	
    	//button for submission or cancellation 
    	JButton createButton = new JButton("Create");
    	JButton cancelButton = new JButton("Cancel");
    	
    	//dialogs
    	dialog.add(studentIdLabel);
    	dialog.add(studentField);
    	dialog.add(firstnameLabel);
    	dialog.add(lastnameLabel);
    	dialog.add(phonenumberLabel);
    	dialog.add(emailLabel);
    	dialog.add(createButton);
    	dialog.add(cancelButton);
    	
    	dialog.add(studentField);
    	dialog.add(firstnameField);
    	dialog.add(lastnameField);
    	dialog.add(PhonenumField);
    	dialog.add(emailField);

    	//action for the create button
    	createButton.addActionListener(e->{
    		String studentId = studentField.getText();
    		String firstname = firstnameField.getText();
    		String lastname = lastnameField.getText();
    		String phonenum = phonenumberLabel.getText();
    		String email = emailLabel.getText();
    		
    		if (studentId.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || phonenum.isEmpty() || email.isEmpty()) {
    			JOptionPane.showMessageDialog(dialog,  "All field are required!", "Error", JOptionPane.ERROR_MESSAGE);
    		}else {
    			createStudentAccount(studentId, firstname, lastname, phonenum, email);
    			dialog.dispose();
    		}
    	});
    	cancelButton.addActionListener(e-> dialog.dispose());
    	dialog.setVisible(true);
    }
    
    //methode to add student account to the database
    private void createStudentAccount(String studentId, String firstname, String lastname, String phonenum, String email) {
    	String sql = "INSERT INTO students (student_id, first_name, last_name, phone_number, email) VALUES(?, ?, ?, ?, ?)";
    	try (Connection conn = database_connector.getConnection();
    			PreparedStatement pstmt =  conn.prepareStatement(sql)){
    		pstmt.setString(1, studentId);
    		pstmt.setString(2, firstname);
    		pstmt.setString(3, lastname);
    		pstmt.setString(4,phonenum);
    		pstmt.setString(5, email);
    		pstmt.executeUpdate();
    		JOptionPane.showMessageDialog(this, "Student account created successfully!");
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(this, "Failed to create student account.","Error", JOptionPane.ERROR_MESSAGE);
    		e.printStackTrace();
    	}
    }
    
    
    // Show dialog to add a book
    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add Book", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        // Form fields
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField();

        // Buttons
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(titleLabel);
        dialog.add(titleField);
        dialog.add(authorLabel);
        dialog.add(authorField);
        dialog.add(yearLabel);
        dialog.add(yearField);
        dialog.add(genreLabel);
        dialog.add(genreField);
        dialog.add(addButton);
        dialog.add(cancelButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());
            String genre = genreField.getText();
            addBookToDatabase(title, author, year, genre);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // Add book to database
    private void addBookToDatabase(String title, String author, int year, String genre) {
        String sql = "INSERT INTO Books (title, author, year_published, genre, status) VALUES (?, ?, ?, ?, 'Available')";
        try (Connection conn = database_connector.getConnection(); // Get connection from database_connector
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.setString(4, genre);
            pstmt.executeUpdate();
            loadBooksData(); // Refresh table data
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show dialog to issue a book
    private void showIssueBookDialog() {
        JDialog dialog = new JDialog(this, "Issue Book", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2));

        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField();
        JLabel memberIdLabel = new JLabel("Student ID:");
        JTextField memberIdField = new JTextField();

        JButton issueButton = new JButton("Issue");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(bookIdLabel);
        dialog.add(bookIdField);
        dialog.add(memberIdLabel);
        dialog.add(memberIdField);
        dialog.add(issueButton);
        dialog.add(cancelButton);

        issueButton.addActionListener(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            int memberId = Integer.parseInt(memberIdField.getText());
            issueBookToMember(bookId, memberId);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // Issue book to a student
    private void issueBookToMember(int bookId, int memberId) {
        String sql = "INSERT INTO BorrowingRecords (book_id, member_id, borrow_date) VALUES (?, ?, CURDATE())";
        String updateBookStatus = "UPDATE Books SET status = 'Borrowed' WHERE book_id = ?";
        try (Connection conn = database_connector.getConnection(); // Get connection from database_connector
             PreparedStatement pstmtIssue = conn.prepareStatement(sql);
             PreparedStatement pstmtUpdateStatus = conn.prepareStatement(updateBookStatus)) {

            pstmtIssue.setInt(1, bookId);
            pstmtIssue.setInt(2, memberId);
            pstmtIssue.executeUpdate();

            pstmtUpdateStatus.setInt(1, bookId);
            pstmtUpdateStatus.executeUpdate();
            loadBooksData(); // Refresh table data
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show dialog to return a book
    private void showReturnBookDialog() {
        JDialog dialog = new JDialog(this, "Return Book", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(2, 2));

        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField();

        JButton returnButton = new JButton("Return");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(bookIdLabel);
        dialog.add(bookIdField);
        dialog.add(returnButton);
        dialog.add(cancelButton);

        returnButton.addActionListener(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            returnBookToLibrary(bookId);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // Return book to the library
    private void returnBookToLibrary(int bookId) {
        String updateRecord = "UPDATE BorrowingRecords SET return_date = CURDATE() WHERE book_id = ? AND return_date IS NULL";
        String updateBookStatus = "UPDATE Books SET status = 'Available' WHERE book_id = ?";
        try (Connection conn = database_connector.getConnection(); // Get connection from database_connector
             PreparedStatement pstmtUpdateRecord = conn.prepareStatement(updateRecord);
             PreparedStatement pstmtUpdateBook = conn.prepareStatement(updateBookStatus)) {

            pstmtUpdateRecord.setInt(1, bookId);
            pstmtUpdateRecord.executeUpdate();

            pstmtUpdateBook.setInt(1, bookId);
            pstmtUpdateBook.executeUpdate();
            loadBooksData(); // Refresh table data
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show dialog to search for books
    private void showSearchBookDialog() {
        JDialog dialog = new JDialog(this, "Search Book", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(2, 2));

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField();

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(searchLabel);
        dialog.add(searchField);
        dialog.add(searchButton);
        dialog.add(cancelButton);

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();
            searchBooks(keyword);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // Search books in the database
    private void searchBooks(String keyword) {
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
        try (Connection conn = database_connector.getConnection(); // Get connection from database_connector
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            bookTableModel.setRowCount(0); // Clear the table

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("year_published"),
                    rs.getString("genre"),
                    rs.getString("status")
                };
                bookTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            library_system frame = new library_system();
            frame.setVisible(true);
        });
    }
}
