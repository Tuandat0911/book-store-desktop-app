package View;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Home extends javax.swing.JFrame {

    public Home() {
        initComponents();
        loadDataAuthor();
        loadDataGenre();
        loadDataPublisher();
        loadDataBook();
        getListAuthor();
        getListCategory();
        getListPublisher();
        
        txtAuthorId.setEnabled(false);
        txtGenreId.setEnabled(false);
        txtPublisherId.setEnabled(false);
        txtBookId.setEnabled(false);
        
        ButtonGroup button = new ButtonGroup();
        button.add(maleOption);
        button.add(femaleOption);
        
        
        // book
        txtBookId.setEnabled(false);
        txtBookTitle.setEnabled(false);
        txtYear.setEnabled(false);
        txtSelling.setEnabled(false);
        txtImport.setEnabled(false);
        txtISBN.setEnabled(false);
        txtPage.setEnabled(false);
        cboPublisher.setEnabled(false);
        cboCategory.setEnabled(false);
        cboAuthor.setEnabled(false);
        saveBook.setEnabled(false);
        updateBook.setEnabled(false);
        
        //author
        txtAuthorId.setEnabled(false);
        txtAuthorName.setEnabled(false);
        txtBirthdate.setEnabled(false);
        txtAuthorStory.setEnabled(false);
        maleOption.setEnabled(false);
        femaleOption.setEnabled(false);
        saveAuthor.setEnabled(false);
        updateAuthor.setEnabled(false);
        
        
        //publisher
        txtPublisher.setEnabled(false);
        txtPublisherId.setEnabled(false);
        savePublisher.setEnabled(false);
        updatePublisher.setEnabled(false);
        
        //genrd
        txtGenre.setEnabled(false);
        txtGenreId.setEnabled(false);
        saveGenre.setEnabled(false);
        updateGenre.setEnabled(false);   
    }

    public void loadDataAuthor() {
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
                String sql = "SELECT * FROM author ORDER BY id ASC";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Author Name");
                table.addColumn("Gender");
                table.addColumn("Story");
                table.addColumn("Birth Date");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String authorName = rs.getString("author_name");
                    String authorGender = rs.getString("gender");
                    String authorStory = rs.getString("story");
                    String authorBirth = rs.getString("birthdate");

                    table.addRow(new Object[]{id, authorName, authorGender, authorStory, authorBirth});
                    authorsTable.setModel(table);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void loadDataGenre() {
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM category ORDER BY id ASC";


            ResultSet rs = st.executeQuery(sql);


                DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Genre Name");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String categoryName = rs.getString("category_name");


                    table.addRow(new Object[]{id,categoryName});
                    genreTable.setModel(table);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void loadDataPublisher() {
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM publisher";

            ResultSet rs = st.executeQuery(sql);

                DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Publisher Name");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String publisherName = rs.getString("publisher_name");


                    table.addRow(new Object[]{id, publisherName});
                    publisherTable.setModel(table);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void loadDataBook() {
        try {
            int totalBook = 0;
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = """
                         SELECT books.book_id, books.book_title, books.publication_year, books.selling_price, books.import_price, books.ISBN, books.page_number, publisher.publisher_name, category.category_name, author.author_name
                         FROM books JOIN publisher
                         ON books.publisher_id = publisher.id
                         JOIN author
                         ON books.author_id = author.id
                         JOIN category 
                         ON books.category_id = category.id ORDER BY books.book_id ASC""";


            ResultSet rs = st.executeQuery(sql);

                DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Book Title");
                table.addColumn("Publication Year");
                table.addColumn("Selling Price");
                table.addColumn("Import Price");
                table.addColumn("ISBN");
                table.addColumn("Page Number");
                table.addColumn("Publisher");
                table.addColumn("Category");
                table.addColumn("Author");

                while (rs.next()) {
                    totalBook += 1;
                    int id = rs.getInt("book_id");
                    String bookName = rs.getString("book_title");
                    String publicationYear = rs.getString("publication_year");
                    Double sellingPrice = rs.getDouble("selling_price");
                    Double importPrice = rs.getDouble("import_price");
                    String isbn = rs.getString("ISBN");
                    int pageNumber = rs.getInt("page_number");
                    String publisher = rs.getString("publisher_name");
                    String category = rs.getString("category_name");
                    String author = rs.getString("author_name");

                    table.addRow(new Object[]{id, bookName, publicationYear, sellingPrice, importPrice, isbn, pageNumber, publisher, category, author});
                    bookTable.setModel(table);
                }
                txtTotalBook.setText(String.valueOf(totalBook));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getListAuthor() {
         try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT id, author_name FROM author ORDER BY id ASC";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String authorName = rs.getString("author_name");
                cboAuthor.addItem(authorName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private int getAuthorId(String authorName) {
        int id = 0;
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT id FROM author WHERE author_name = '"+authorName+"'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    
    private void getListPublisher() {
         try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT publisher_name FROM publisher ORDER BY id ASC";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String publisherName = rs.getString("publisher_name");
                cboPublisher.addItem(publisherName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private int getPublisherId(String publisherName) {
        int id = 0;
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT id FROM publisher WHERE publisher_name = '"+publisherName+"'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    
    
    
    private void getListCategory() {
         try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT id, category_name FROM category ORDER BY id ASC";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                cboCategory.addItem(categoryName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private int getCategoryId(String categoryName) {
        int id = 0;
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT id FROM category WHERE category_name = '"+categoryName+"'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        home_menu = new javax.swing.JLabel();
        books_menu = new javax.swing.JLabel();
        author_menu = new javax.swing.JLabel();
        publisher_menu = new javax.swing.JLabel();
        category_menu = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        support_menu = new javax.swing.JLabel();
        contact_menu = new javax.swing.JLabel();
        logout_menu = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        home_tab = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        book_tab = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        searchBookTitle = new javax.swing.JTextField();
        searchISBN = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        searchYear = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        searchPriceFrom = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        searchPirceTo = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtBookTitle = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSelling = new javax.swing.JTextField();
        txtImport = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPage = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboPublisher = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cboCategory = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cboAuthor = new javax.swing.JComboBox<>();
        insertBook = new javax.swing.JButton();
        deleteBook = new javax.swing.JButton();
        cancelBook = new javax.swing.JButton();
        fixBook = new javax.swing.JButton();
        updateBook = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        saveBook = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtTotalBook = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtBookId = new javax.swing.JTextField();
        searchBook = new javax.swing.JButton();
        author_tab = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtAuthorName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        saveAuthor = new javax.swing.JButton();
        updateAuthor = new javax.swing.JButton();
        fixAuthor = new javax.swing.JButton();
        cancelAuthor = new javax.swing.JButton();
        deleteAuthor = new javax.swing.JButton();
        insertAuthor = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel34 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        birthdateSearch = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        authorNameSearch = new javax.swing.JTextField();
        authorTable = new javax.swing.JScrollPane();
        authorsTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAuthorStory = new javax.swing.JTextArea();
        txtBirthdate = new javax.swing.JTextField();
        maleOption = new javax.swing.JRadioButton();
        femaleOption = new javax.swing.JRadioButton();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        txtAuthorId = new javax.swing.JTextField();
        authorSearchBtn = new javax.swing.JButton();
        genre_tab = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        insertPublisher = new javax.swing.JButton();
        deletePublisher = new javax.swing.JButton();
        cancelPublisher = new javax.swing.JButton();
        fixpublisher = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        updatePublisher = new javax.swing.JButton();
        txtPublisher = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        publisherTable = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        savePublisher = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        searchGenre = new javax.swing.JTextField();
        searchCategoryBtn = new javax.swing.JButton();
        insertGenre = new javax.swing.JButton();
        deleteGenre = new javax.swing.JButton();
        cancelGenre = new javax.swing.JButton();
        fixGenre = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        updateGenre = new javax.swing.JButton();
        txtGenre = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        genreTable = new javax.swing.JTable();
        saveGenre = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        publisherSearch = new javax.swing.JTextField();
        searchPublisher = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtGenreId = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtPublisherId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        home_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        home_menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        home_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home_icon.png"))); // NOI18N
        home_menu.setText("Home");
        home_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_menuMouseClicked(evt);
            }
        });

        books_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        books_menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        books_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/books_icon.png"))); // NOI18N
        books_menu.setText("Books");
        books_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                books_menuMouseClicked(evt);
            }
        });

        author_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        author_menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        author_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/author_icon.png"))); // NOI18N
        author_menu.setText("Authors");
        author_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                author_menuMouseClicked(evt);
            }
        });

        publisher_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        publisher_menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        publisher_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/publisher_icon.png"))); // NOI18N
        publisher_menu.setText("Publishers");
        publisher_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                publisher_menuMouseClicked(evt);
            }
        });

        category_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        category_menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        category_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/category_icon.png"))); // NOI18N
        category_menu.setText("Categories");
        category_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                category_menuMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 204, 102));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/book.png"))); // NOI18N

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(40, 40, 40))
            .addComponent(jSeparator2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(17, 17, 17)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        support_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        support_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/support_icon.png"))); // NOI18N
        support_menu.setText("Supports");

        contact_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        contact_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/contact_icon.png"))); // NOI18N
        contact_menu.setText("Contact");

        logout_menu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        logout_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logout_icon.png"))); // NOI18N
        logout_menu.setText("Log out");
        logout_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout_menuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(author_menu)
                    .addComponent(publisher_menu)
                    .addComponent(category_menu)
                    .addComponent(books_menu)
                    .addComponent(home_menu))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(support_menu)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(logout_menu)
                        .addComponent(contact_menu)))
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(home_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(books_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(author_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(publisher_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(category_menu)
                .addGap(69, 69, 69)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(support_menu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contact_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logout_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jPanel4.setLayout(new java.awt.CardLayout());

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/background.png"))); // NOI18N

        javax.swing.GroupLayout home_tabLayout = new javax.swing.GroupLayout(home_tab);
        home_tab.setLayout(home_tabLayout);
        home_tabLayout.setHorizontalGroup(
            home_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(home_tabLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        home_tabLayout.setVerticalGroup(
            home_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.add(home_tab, "card2");

        book_tab.setBackground(new java.awt.Color(255, 255, 204));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Books Managerment");

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Search");

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel22.setText("Book  Title");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel23.setText("ISBN");

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel27.setText("Publication Year");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel28.setText("Price range from to ");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel29.setText("To");

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setText("Book Title");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Publication Year");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Selling Price");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setText("Import Price");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setText("Page Number");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("ISBN");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("Publisher");

        cboPublisher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Publisher" }));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel13.setText("Categories");

        cboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel14.setText("Authors");

        cboAuthor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Author" }));

        insertBook.setBackground(new java.awt.Color(102, 255, 102));
        insertBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        insertBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        insertBook.setText("INSERT");
        insertBook.setHideActionText(true);
        insertBook.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        insertBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBookActionPerformed(evt);
            }
        });

        deleteBook.setBackground(new java.awt.Color(102, 255, 102));
        deleteBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        deleteBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        deleteBook.setText("DELETE");
        deleteBook.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        deleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookActionPerformed(evt);
            }
        });

        cancelBook.setBackground(new java.awt.Color(102, 255, 102));
        cancelBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancelBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        cancelBook.setText("CANCEL");
        cancelBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBookActionPerformed(evt);
            }
        });

        fixBook.setBackground(new java.awt.Color(102, 255, 102));
        fixBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        fixBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Edit.png"))); // NOI18N
        fixBook.setText("FIX");
        fixBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixBookActionPerformed(evt);
            }
        });

        updateBook.setBackground(new java.awt.Color(102, 255, 102));
        updateBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        updateBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tick.png"))); // NOI18N
        updateBook.setText("UPDATE");
        updateBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBookActionPerformed(evt);
            }
        });

        bookTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Book Title", "Publication Year", "Selling Price", "Import Price", "ISBN", "Page Number", "Publisher", "Category", "Author"
            }
        ));
        bookTable.setShowGrid(true);
        bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bookTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(bookTable);

        saveBook.setBackground(new java.awt.Color(102, 255, 102));
        saveBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        saveBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        saveBook.setText("SAVE");
        saveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBookActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel15.setText("Total Number Of Book");
        jLabel15.setToolTipText("");

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel41.setText("Book Id");

        searchBook.setBackground(new java.awt.Color(51, 255, 51));
        searchBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search.png"))); // NOI18N
        searchBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout book_tabLayout = new javax.swing.GroupLayout(book_tab);
        book_tab.setLayout(book_tabLayout);
        book_tabLayout.setHorizontalGroup(
            book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(book_tabLayout.createSequentialGroup()
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(book_tabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jSeparator5)
                            .addGroup(book_tabLayout.createSequentialGroup()
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator4)
                                    .addGroup(book_tabLayout.createSequentialGroup()
                                        .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(book_tabLayout.createSequentialGroup()
                                                .addComponent(updateBook)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(saveBook, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(book_tabLayout.createSequentialGroup()
                                                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jLabel14)
                                                    .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(cboCategory, javax.swing.GroupLayout.Alignment.LEADING, 0, 211, Short.MAX_VALUE)
                                                    .addComponent(cboPublisher, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txtISBN, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cboAuthor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txtBookId))))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(book_tabLayout.createSequentialGroup()
                        .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(book_tabLayout.createSequentialGroup()
                                .addGap(280, 280, 280)
                                .addComponent(jLabel11))
                            .addGroup(book_tabLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(book_tabLayout.createSequentialGroup()
                                        .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPage, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtImport, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSelling, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(book_tabLayout.createSequentialGroup()
                                        .addComponent(insertBook, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(deleteBook, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cancelBook, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(fixBook, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 369, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(book_tabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTotalBook, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(book_tabLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchBookTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(searchISBN))
                .addGap(31, 31, 31)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(book_tabLayout.createSequentialGroup()
                        .addComponent(searchPriceFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchPirceTo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchBook, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(book_tabLayout.createSequentialGroup()
                        .addComponent(searchYear, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        book_tabLayout.setVerticalGroup(
            book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(book_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(19, 19, 19)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cboPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSelling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cboAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(txtBookId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertBook)
                    .addComponent(deleteBook)
                    .addComponent(cancelBook)
                    .addComponent(fixBook)
                    .addComponent(updateBook)
                    .addComponent(saveBook))
                .addGap(25, 25, 25)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(searchBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBook)
                    .addComponent(jLabel28)
                    .addComponent(searchPriceFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(searchPirceTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(searchISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(55, 55, 55)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(book_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTotalBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.add(book_tab, "card3");

        author_tab.setBackground(new java.awt.Color(255, 255, 204));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Author Managerment");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel17.setText("Author Name");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel18.setText("Gender");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel30.setText("Story");

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel31.setText("Birth Of Date");

        saveAuthor.setBackground(new java.awt.Color(102, 255, 102));
        saveAuthor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        saveAuthor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        saveAuthor.setText("SAVE");
        saveAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAuthorActionPerformed(evt);
            }
        });

        updateAuthor.setBackground(new java.awt.Color(102, 255, 102));
        updateAuthor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        updateAuthor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tick.png"))); // NOI18N
        updateAuthor.setText("UPDATE");
        updateAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAuthorActionPerformed(evt);
            }
        });

        fixAuthor.setBackground(new java.awt.Color(102, 255, 102));
        fixAuthor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        fixAuthor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Edit.png"))); // NOI18N
        fixAuthor.setText("FIX");
        fixAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixAuthorActionPerformed(evt);
            }
        });

        cancelAuthor.setBackground(new java.awt.Color(102, 255, 102));
        cancelAuthor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancelAuthor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        cancelAuthor.setText("CANCEL");
        cancelAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAuthorActionPerformed(evt);
            }
        });

        deleteAuthor.setBackground(new java.awt.Color(102, 255, 102));
        deleteAuthor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        deleteAuthor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        deleteAuthor.setText("DELETE");
        deleteAuthor.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        deleteAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAuthorActionPerformed(evt);
            }
        });

        insertAuthor.setBackground(new java.awt.Color(102, 255, 102));
        insertAuthor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        insertAuthor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        insertAuthor.setText("INSERT");
        insertAuthor.setHideActionText(true);
        insertAuthor.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        insertAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertAuthorActionPerformed(evt);
            }
        });

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Search");

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel37.setText("Birth Of Date");

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel42.setText("Author Name");

        authorsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Author Name", "Gender", "Story", "Birth Date"
            }
        ));
        authorsTable.setShowGrid(true);
        authorsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                authorsTableMousePressed(evt);
            }
        });
        authorTable.setViewportView(authorsTable);

        txtAuthorStory.setColumns(20);
        txtAuthorStory.setRows(5);
        jScrollPane3.setViewportView(txtAuthorStory);

        maleOption.setBackground(new java.awt.Color(255, 255, 204));
        maleOption.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        maleOption.setText("Male");

        femaleOption.setBackground(new java.awt.Color(255, 255, 204));
        femaleOption.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        femaleOption.setText("Female");

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel32.setText("ID");

        authorSearchBtn.setBackground(new java.awt.Color(204, 255, 0));
        authorSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search.png"))); // NOI18N
        authorSearchBtn.setText("jButton7");
        authorSearchBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        authorSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorSearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout author_tabLayout = new javax.swing.GroupLayout(author_tab);
        author_tab.setLayout(author_tabLayout);
        author_tabLayout.setHorizontalGroup(
            author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, author_tabLayout.createSequentialGroup()
                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, author_tabLayout.createSequentialGroup()
                        .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(author_tabLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAuthorId, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAuthorName, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(author_tabLayout.createSequentialGroup()
                                            .addComponent(jLabel31)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtBirthdate))
                                        .addGroup(author_tabLayout.createSequentialGroup()
                                            .addComponent(jLabel30)
                                            .addGap(54, 54, 54)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(author_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(birthdateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(authorSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(author_tabLayout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(maleOption)
                                .addGap(18, 18, 18)
                                .addComponent(femaleOption))
                            .addGroup(author_tabLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(authorNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 165, Short.MAX_VALUE))
                    .addGroup(author_tabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(authorTable)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap())
            .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(author_tabLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(author_tabLayout.createSequentialGroup()
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel34)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator7))
                        .addGroup(author_tabLayout.createSequentialGroup()
                            .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(author_tabLayout.createSequentialGroup()
                                    .addGap(133, 133, 133)
                                    .addComponent(deleteAuthor)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cancelAuthor)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(fixAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(updateAuthor)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(saveAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(author_tabLayout.createSequentialGroup()
                                    .addGap(268, 268, 268)
                                    .addComponent(jLabel16))
                                .addGroup(author_tabLayout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19)
                                        .addComponent(insertAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        author_tabLayout.setVerticalGroup(
            author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(author_tabLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(txtAuthorId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(author_tabLayout.createSequentialGroup()
                        .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtAuthorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maleOption)
                            .addComponent(femaleOption)
                            .addComponent(jLabel18)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140)
                .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthdateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(authorNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(authorSearchBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(authorTable, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(author_tabLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel16)
                    .addGap(92, 92, 92)
                    .addComponent(jLabel19)
                    .addGap(125, 125, 125)
                    .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(insertAuthor)
                        .addComponent(deleteAuthor)
                        .addComponent(cancelAuthor)
                        .addComponent(fixAuthor)
                        .addComponent(updateAuthor)
                        .addComponent(saveAuthor))
                    .addGap(25, 25, 25)
                    .addGroup(author_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(322, Short.MAX_VALUE)))
        );

        jPanel4.add(author_tab, "card4");

        genre_tab.setBackground(new java.awt.Color(255, 255, 204));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Search");

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        insertPublisher.setBackground(new java.awt.Color(102, 255, 102));
        insertPublisher.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        insertPublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        insertPublisher.setText("INSERT");
        insertPublisher.setHideActionText(true);
        insertPublisher.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        insertPublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertPublisherActionPerformed(evt);
            }
        });

        deletePublisher.setBackground(new java.awt.Color(102, 255, 102));
        deletePublisher.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        deletePublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        deletePublisher.setText("DELETE");
        deletePublisher.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        deletePublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePublisherActionPerformed(evt);
            }
        });

        cancelPublisher.setBackground(new java.awt.Color(102, 255, 102));
        cancelPublisher.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancelPublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        cancelPublisher.setText("CANCEL");
        cancelPublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelPublisherActionPerformed(evt);
            }
        });

        fixpublisher.setBackground(new java.awt.Color(102, 255, 102));
        fixpublisher.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        fixpublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Edit.png"))); // NOI18N
        fixpublisher.setText("FIX");
        fixpublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixpublisherActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel35.setText("Publishers Name");

        updatePublisher.setBackground(new java.awt.Color(102, 255, 102));
        updatePublisher.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        updatePublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tick.png"))); // NOI18N
        updatePublisher.setText("UPDATE");
        updatePublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePublisherActionPerformed(evt);
            }
        });

        publisherTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        publisherTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Publisher Name"
            }
        ));
        publisherTable.setShowGrid(true);
        publisherTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                publisherTableMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(publisherTable);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        savePublisher.setBackground(new java.awt.Color(102, 255, 102));
        savePublisher.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        savePublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        savePublisher.setText("SAVE");
        savePublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePublisherActionPerformed(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 51, 51));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Genres & Publishers");

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel38.setText("Genre Name");

        searchCategoryBtn.setBackground(new java.awt.Color(204, 255, 0));
        searchCategoryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search.png"))); // NOI18N
        searchCategoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCategoryBtnActionPerformed(evt);
            }
        });

        insertGenre.setBackground(new java.awt.Color(102, 255, 102));
        insertGenre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        insertGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        insertGenre.setText("INSERT");
        insertGenre.setHideActionText(true);
        insertGenre.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        insertGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertGenreActionPerformed(evt);
            }
        });

        deleteGenre.setBackground(new java.awt.Color(102, 255, 102));
        deleteGenre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        deleteGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        deleteGenre.setText("DELETE");
        deleteGenre.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        deleteGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGenreActionPerformed(evt);
            }
        });

        cancelGenre.setBackground(new java.awt.Color(102, 255, 102));
        cancelGenre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancelGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        cancelGenre.setText("CANCEL");
        cancelGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelGenreActionPerformed(evt);
            }
        });

        fixGenre.setBackground(new java.awt.Color(102, 255, 102));
        fixGenre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        fixGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Edit.png"))); // NOI18N
        fixGenre.setText("FIX");
        fixGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixGenreActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel39.setText("Genre Name");

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );

        updateGenre.setBackground(new java.awt.Color(102, 255, 102));
        updateGenre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        updateGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tick.png"))); // NOI18N
        updateGenre.setText("UPDATE");
        updateGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateGenreActionPerformed(evt);
            }
        });

        genreTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        genreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Genre Name"
            }
        ));
        genreTable.setShowGrid(true);
        genreTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                genreTableMousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(genreTable);

        saveGenre.setBackground(new java.awt.Color(102, 255, 102));
        saveGenre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        saveGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        saveGenre.setText("SAVE");
        saveGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGenreActionPerformed(evt);
            }
        });

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Search");

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel40.setText("Publisher Name");

        searchPublisher.setBackground(new java.awt.Color(204, 255, 0));
        searchPublisher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search.png"))); // NOI18N
        searchPublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPublisherActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setText("id");

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel33.setText("id");

        javax.swing.GroupLayout genre_tabLayout = new javax.swing.GroupLayout(genre_tab);
        genre_tab.setLayout(genre_tabLayout);
        genre_tabLayout.setHorizontalGroup(
            genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(genre_tabLayout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel53)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(genre_tabLayout.createSequentialGroup()
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator11)
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGap(442, 442, 442)
                                .addComponent(jSeparator10, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(genre_tabLayout.createSequentialGroup()
                                        .addGap(381, 381, 381)
                                        .addComponent(jLabel21)))
                                .addGap(429, 429, 429))))
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(searchCategoryBtn))
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(genre_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addGap(22, 22, 22)
                                        .addComponent(saveGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtGenreId, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(genre_tabLayout.createSequentialGroup()
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addGap(393, 393, 393)
                        .addComponent(jLabel20))
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genre_tabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(publisherSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(searchPublisher)
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genre_tabLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(txtPublisherId)
                        .addGap(94, 94, 94))
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(updatePublisher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cancelPublisher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(insertPublisher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(deletePublisher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fixpublisher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(savePublisher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(53, 53, 53))))
            .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(genre_tabLayout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(insertGenre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cancelGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fixGenre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteGenre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addComponent(updateGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(62, 62, 62)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(63, 63, 63)))
        );
        genre_tabLayout.setVerticalGroup(
            genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(genre_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53)
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtGenreId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(160, 160, 160)
                        .addComponent(jLabel36)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genre_tabLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveGenre)
                        .addGap(29, 29, 29)))
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(searchGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(searchCategoryBtn)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txtPublisherId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(genre_tabLayout.createSequentialGroup()
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(txtPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(insertPublisher)
                                    .addComponent(deletePublisher))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cancelPublisher)
                                    .addComponent(fixpublisher))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(updatePublisher)
                                    .addComponent(savePublisher)))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(genre_tabLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(publisherSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(searchPublisher))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(genre_tabLayout.createSequentialGroup()
                    .addGap(72, 72, 72)
                    .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(genre_tabLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(genre_tabLayout.createSequentialGroup()
                                    .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel39)
                                        .addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(insertGenre)
                                        .addComponent(deleteGenre))
                                    .addGap(18, 18, 18)
                                    .addGroup(genre_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cancelGenre)
                                        .addComponent(fixGenre))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(updateGenre))
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(422, Short.MAX_VALUE)))
        );

        jPanel4.add(genre_tab, "card5");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void books_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_books_menuMouseClicked
        book_tab.setVisible(true);
        home_tab.setVisible(false);
        author_tab.setVisible(false);
        genre_tab.setVisible(false);
    }//GEN-LAST:event_books_menuMouseClicked

    private void home_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_menuMouseClicked
        home_tab.setVisible(true);
    }//GEN-LAST:event_home_menuMouseClicked

    private void author_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_author_menuMouseClicked
       author_tab.setVisible(true);
       home_tab.setVisible(false);
       book_tab.setVisible(false);
       genre_tab.setVisible(false);
    }//GEN-LAST:event_author_menuMouseClicked

    private void publisher_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_publisher_menuMouseClicked
        author_tab.setVisible(false);
        home_tab.setVisible(false);
        book_tab.setVisible(false);
        genre_tab.setVisible(true);
    }//GEN-LAST:event_publisher_menuMouseClicked

    private void category_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_category_menuMouseClicked
        author_tab.setVisible(false);
        home_tab.setVisible(false);
        book_tab.setVisible(false);
        genre_tab.setVisible(true);
    }//GEN-LAST:event_category_menuMouseClicked

    private void saveAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAuthorActionPerformed
        String authorName = txtAuthorName.getText().toString().trim();
        String birthdate = txtBirthdate.getText().toString().trim();
        String story = txtAuthorStory.getText().toString().trim();
        String gender = "";
        if(maleOption.isSelected()) {
            gender = "Male";
        }
        if(femaleOption.isSelected()) {
            gender = "Female";
        }
        
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "INSERT INTO author (author_name, gender, story, birthdate) VALUES ('" +authorName + "', '" + gender + "', '" + story + "', '" + birthdate + "')";
            int rowAffected = st.executeUpdate(sql);
            if(rowAffected > 0) {
                JOptionPane.showMessageDialog(rootPane, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtAuthorId.setEnabled(false);
                txtAuthorName.setEnabled(false);
                txtBirthdate.setEnabled(false);
                txtAuthorStory.setEnabled(false);
                maleOption.setEnabled(false);
                femaleOption.setEnabled(false);
                saveAuthor.setEnabled(false);
                saveAuthor.setEnabled(false);
            }else {
                JOptionPane.showMessageDialog(rootPane, "Insert Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadDataAuthor();
        txtAuthorName.setText("");
        txtAuthorStory.setText("");
        txtBirthdate.setText("");
    }//GEN-LAST:event_saveAuthorActionPerformed

    private void deleteAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAuthorActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        String id = txtAuthorId.getText();
        if(confirm == 0) {
             try {
                String url = "jdbc:mysql://localhost:3306/bookstore";
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "DELETE FROM author WHERE id =  '" + id + "'";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(rootPane, "Delete Success", "Success" , JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        loadDataAuthor();
        txtAuthorId.setText("");
        txtAuthorName.setText("");
        txtAuthorStory.setText("");
        txtBirthdate.setText("");
    }//GEN-LAST:event_deleteAuthorActionPerformed

    private void authorsTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_authorsTableMousePressed
        int rowIndex = authorsTable.getSelectedRow();
        String id = authorsTable.getModel().getValueAt(rowIndex, 0).toString();
        String authorName = authorsTable.getModel().getValueAt(rowIndex, 1).toString();
        String authorGender = authorsTable.getModel().getValueAt(rowIndex, 2).toString();
        String authorStory = authorsTable.getModel().getValueAt(rowIndex, 3).toString();
        String authorBirth = authorsTable.getModel().getValueAt(rowIndex, 4).toString();

        txtAuthorId.setText(id);
        txtAuthorName.setText(authorName);
        txtAuthorStory.setText(authorStory);
        txtBirthdate.setText(authorBirth);

        if (authorGender.equals("Male")) {
            maleOption.setSelected(true);
        } else if (authorGender.equals("Female")) {
            femaleOption.setSelected(true);
        }
    }//GEN-LAST:event_authorsTableMousePressed

    private void updateAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateAuthorActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to update ?", "Update", JOptionPane.YES_NO_OPTION);
        if(confirm == 0) {
            String id = txtAuthorId.getText().toString().trim();
            String authorName = txtAuthorName.getText().toString().trim();
            String birthdate = txtBirthdate.getText().toString().trim();
            String story = txtAuthorStory.getText().toString().trim();
            String gender = "";
            if(maleOption.isSelected()) {
                gender = "Male";
            }
            if(femaleOption.isSelected()) {
                gender = "Female";
            }

            try {
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "UPDATE author SET author_name = '"+authorName+"', gender = '"+gender+"', story = '"+story+"', birthdate = '"+birthdate+"' WHERE id = " + id;
                int rowAffected = st.executeUpdate(sql);
                if(rowAffected > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateAuthor.setEnabled(false);
                    txtAuthorName.setEnabled(false);
                    txtBirthdate.setEnabled(false);
                    txtAuthorStory.setEnabled(false);
                    maleOption.setEnabled(false);
                    femaleOption.setEnabled(false);
                  
                    fixAuthor.setEnabled(false);
                }else {
                    JOptionPane.showMessageDialog(rootPane, "Insert Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        
        loadDataAuthor();
        txtAuthorId.setText("");
        txtAuthorName.setText("");
        txtAuthorStory.setText("");
        txtBirthdate.setText("");
    }//GEN-LAST:event_updateAuthorActionPerformed

    private void authorSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorSearchBtnActionPerformed
        String authorsName = authorNameSearch.getText().toString().trim();
        String birthdate = birthdateSearch.getText().toString().trim();
        
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM author WHERE author_name LIKE '%" + authorsName + "%'";

            if(birthdate.equals("")) {
                sql = "SELECT * FROM author WHERE author_name LIKE '%"+authorsName+"%'";
            }
            else {
                sql = "SELECT * FROM author WHERE author_name LIKE '%"+authorsName+"%' AND birthdate = '"+birthdate+"'";
            }
            
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Author Name");
                table.addColumn("Gender");
                table.addColumn("Story");
                table.addColumn("Birth Date");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String authorName = rs.getString("author_name");
                    String authorGender = rs.getString("gender");
                    String authorStory = rs.getString("story");
                    String authorBirth = rs.getString("birthdate");
                    System.out.println(authorName);

                    table.addRow(new Object[]{id, authorName, authorGender, authorStory, authorBirth});
                    authorsTable.setModel(table);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        txtAuthorName.setText("");
        txtAuthorStory.setText("");
        txtBirthdate.setText("");
        authorNameSearch.setText("");
        birthdateSearch.setText("");
    }//GEN-LAST:event_authorSearchBtnActionPerformed

    private void cancelAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAuthorActionPerformed
        loadDataAuthor();
        txtAuthorId.setText("");
        txtAuthorName.setText("");
        txtAuthorStory.setText("");
        txtBirthdate.setText("");
    }//GEN-LAST:event_cancelAuthorActionPerformed

    private void saveGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGenreActionPerformed
        String categoryName = txtGenre.getText().toString().trim();
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "INSERT INTO category (category_name) VALUES ('" + categoryName + "')";
            int rowAffected = st.executeUpdate(sql);
            if(rowAffected > 0) {
                JOptionPane.showMessageDialog(rootPane, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                saveGenre.setEnabled(false);
                txtGenre.setEnabled(false);  
            }else {
                JOptionPane.showMessageDialog(rootPane, "Insert Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadDataGenre();
        txtGenre.setText("");
       
    }//GEN-LAST:event_saveGenreActionPerformed

    private void deleteGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGenreActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        String id = txtGenreId.getText();
        if(confirm == 0) {
             try {
                String url = "jdbc:mysql://localhost:3306/bookstore";
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "DELETE FROM author WHERE id =  '" + id + "'";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(rootPane, "Delete Success", "Success" , JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        loadDataGenre();
        txtGenreId.setText("");
        txtGenre.setText("");
    }//GEN-LAST:event_deleteGenreActionPerformed

    private void genreTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genreTableMousePressed
        int rowIndex = genreTable.getSelectedRow();
        String id = genreTable.getModel().getValueAt(rowIndex, 0).toString();
        String categoryName = genreTable.getModel().getValueAt(rowIndex, 1).toString();

        txtGenreId.setText(id);
        txtGenre.setText(categoryName);
    }//GEN-LAST:event_genreTableMousePressed

    private void fixGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixGenreActionPerformed
        updateGenre.setEnabled(true);
        txtGenre.setEnabled(true);
         
    }//GEN-LAST:event_fixGenreActionPerformed

    private void updateGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateGenreActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to update ?", "Update", JOptionPane.YES_NO_OPTION);
        if(confirm == 0) {
            String id = txtGenreId.getText().toString().trim();
            String categoryName = txtGenre.getText().toString().trim();

            try {
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "UPDATE category SET category_name = '"+categoryName+"' WHERE id = " + id;
                int rowAffected = st.executeUpdate(sql);
                if(rowAffected > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateGenre.setEnabled(false);
                    txtGenre.setEnabled(false);
                  
                }else {
                    JOptionPane.showMessageDialog(rootPane, "Update Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        
        loadDataGenre();
        txtGenreId.setText("");
        txtGenre.setText("");
    }//GEN-LAST:event_updateGenreActionPerformed

    private void searchCategoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCategoryBtnActionPerformed
        String categoryName = searchGenre.getText().toString().trim();
        
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM category WHERE category_name LIKE '%" + categoryName + "%'";
            
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Genre Name");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String categoriesName = rs.getString("category_name");
                    table.addRow(new Object[]{id, categoriesName});
                    genreTable.setModel(table);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        searchGenre.setText("");
    }//GEN-LAST:event_searchCategoryBtnActionPerformed

    private void savePublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePublisherActionPerformed
         String publisherName = txtPublisher.getText().toString().trim();
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "INSERT INTO publisher (publisher_name) VALUES ('" + publisherName + "')";
            int rowAffected = st.executeUpdate(sql);
            if(rowAffected > 0) {
                JOptionPane.showMessageDialog(rootPane, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                savePublisher.setEnabled(false);
                txtPublisher.setEnabled(false);
                
            }else {
                JOptionPane.showMessageDialog(rootPane, "Insert Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadDataPublisher();
        txtPublisher.setText("");
       
       
    }//GEN-LAST:event_savePublisherActionPerformed

    private void deletePublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePublisherActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        String id = txtPublisherId.getText();
        if(confirm == 0) {
             try {
                String url = "jdbc:mysql://localhost:3306/bookstore";
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "DELETE FROM publisher WHERE id =  '" + id + "'";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(rootPane, "Delete Success", "Success" , JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        loadDataGenre();
        txtPublisherId.setText("");
        txtPublisher.setText("");
    }//GEN-LAST:event_deletePublisherActionPerformed

    private void publisherTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_publisherTableMousePressed
        int rowIndex = publisherTable.getSelectedRow();
        String id = publisherTable.getModel().getValueAt(rowIndex, 0).toString();
        String publisherName = publisherTable.getModel().getValueAt(rowIndex, 1).toString();

        txtPublisherId.setText(id);
        txtPublisher.setText(publisherName);
    }//GEN-LAST:event_publisherTableMousePressed

    private void searchPublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPublisherActionPerformed
        String publisherName = publisherSearch.getText().toString().trim();
        
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM publisher WHERE publisher_name LIKE '%" + publisherName + "%'";
            
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel table = new DefaultTableModel();
                table.addColumn("ID");
                table.addColumn("Publisher Name");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String publishersName = rs.getString("publisher_name");
                    table.addRow(new Object[]{id, publishersName});
                    publisherTable.setModel(table);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        searchPublisher.setText("");
    }//GEN-LAST:event_searchPublisherActionPerformed

    private void cancelPublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelPublisherActionPerformed
        loadDataPublisher();
        txtPublisher.setText("");
        txtPublisherId.setText("");
    }//GEN-LAST:event_cancelPublisherActionPerformed

    private void cancelGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelGenreActionPerformed
        loadDataGenre();
        txtGenre.setText("");
        txtGenreId.setText("");
    }//GEN-LAST:event_cancelGenreActionPerformed

    private void updatePublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePublisherActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to update ?", "Update", JOptionPane.YES_NO_OPTION);
        if(confirm == 0) {
            String id = txtPublisherId.getText().toString().trim();
            String publisherName = txtPublisher.getText().toString().trim();

            try {
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "UPDATE publisher SET publisher_name = '"+publisherName+"' WHERE id = " + id;
                int rowAffected = st.executeUpdate(sql);
                if(rowAffected > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updatePublisher.setEnabled(false);
                    txtPublisher.setEnabled(false);
                  
                }else {
                    JOptionPane.showMessageDialog(rootPane, "Update Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        
        loadDataPublisher();
        txtPublisher.setText("");
        txtPublisherId.setText("");
    }//GEN-LAST:event_updatePublisherActionPerformed

    private void logout_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_menuMouseClicked
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?", "Logout", JOptionPane.YES_NO_OPTION);
        if(confirm == 0) {
           this.dispose();
           Login d = new Login();
           d.setLocationRelativeTo(null);
           d.setVisible(true);
        } else {
            return;
        }
    }//GEN-LAST:event_logout_menuMouseClicked

    private void saveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBookActionPerformed
        String bookName = txtBookTitle.getText().trim();
        String publicationYear = txtYear.getText().trim(); 
        Double sellingPrice = Double.valueOf(txtSelling.getText());
        Double importPrice = Double.valueOf(txtImport.getText());
        String isbn = txtISBN.getText().trim();
        int pageNumber = Integer.parseInt(txtPage.getText());

        int authorId = getAuthorId(cboAuthor.getSelectedItem().toString());
        int categoryId = getCategoryId(cboCategory.getSelectedItem().toString());
        int publisherId = getPublisherId(cboPublisher.getSelectedItem().toString());
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            int check = 0;
            String sql = "SELECT * FROM books WHERE ISBN = '"+isbn+"'";
            ResultSet rs = st.executeQuery(sql);
            String insert = "INSERT INTO books "
                    + "(book_title, publication_year, selling_price, import_price, ISBN, page_number, publisher_id, category_id, author_id) "
                    + "VALUES "
                    + "('" +bookName+ "', '"+publicationYear+"', '"+sellingPrice+"', '"+importPrice+"', '"+isbn+"', '"+pageNumber+"', "+publisherId+", "+categoryId+", "+authorId+")";
            
            if(rs.next()) check = 1;
            if(check == 0) {
                st.executeUpdate(insert);
                JOptionPane.showMessageDialog(rootPane, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                saveBook.setEnabled(false);
                txtBookId.setEnabled(false);
                txtBookTitle.setEnabled(false);
                txtYear.setEnabled(false);
                txtSelling.setEnabled(false);
                txtImport.setEnabled(false);
                txtISBN.setEnabled(false);
                txtPage.setEnabled(false);
                cboPublisher.setEnabled(false);
                cboCategory.setEnabled(false);
                cboAuthor.setEnabled(false);
                saveBook.setEnabled(false);
                updateBook.setEnabled(false);
            }else {
                JOptionPane.showMessageDialog(rootPane, "Insert Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadDataBook();
        txtBookId.setText("");
        txtBookTitle.setText("");
        txtYear.setText("");
        txtSelling.setText("");
        txtImport.setText("");
        txtISBN.setText("");
        txtPage.setText("");
        cboPublisher.setSelectedIndex(0);
        cboCategory.setSelectedIndex(0);
        cboAuthor.setSelectedIndex(0);
    }//GEN-LAST:event_saveBookActionPerformed

    private void deleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        String id = txtBookId.getText().trim();
        if(confirm == 0) {
             try {
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql = "DELETE FROM books WHERE book_id = " + id + "";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(rootPane, "Delete Success", "Success" , JOptionPane.INFORMATION_MESSAGE);
                
            } catch (HeadlessException | SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        loadDataBook();
        txtBookId.setText("");
        txtBookTitle.setText("");
        txtYear.setText("");
        txtSelling.setText("");
        txtImport.setText("");
        txtISBN.setText("");
        txtPage.setText("");
        cboPublisher.setSelectedIndex(0);
        cboCategory.setSelectedIndex(0);
        cboAuthor.setSelectedItem(0);
        

    }//GEN-LAST:event_deleteBookActionPerformed

    private void bookTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookTableMousePressed
        int rowIndex = bookTable.getSelectedRow();
        String id = bookTable.getModel().getValueAt(rowIndex, 0).toString();
        String bookTitle = bookTable.getModel().getValueAt(rowIndex, 1).toString();
        String publicationYear = bookTable.getModel().getValueAt(rowIndex, 2).toString();
        String sellingPrice = bookTable.getModel().getValueAt(rowIndex, 3).toString();
        String importPrice = bookTable.getModel().getValueAt(rowIndex, 4).toString();
        String isbn = bookTable.getModel().getValueAt(rowIndex, 5).toString();
        String pageNumber = bookTable.getModel().getValueAt(rowIndex, 6).toString();
        String publisher = bookTable.getModel().getValueAt(rowIndex, 7).toString();
        String category = bookTable.getModel().getValueAt(rowIndex, 8).toString();
        String author = bookTable.getModel().getValueAt(rowIndex, 9).toString();

        txtBookId.setText(id);
        txtBookTitle.setText(bookTitle);
        txtYear.setText(publicationYear);
        txtSelling.setText(sellingPrice);
        txtImport.setText(importPrice);
        txtISBN.setText(isbn);
        txtPage.setText(pageNumber);
        cboPublisher.setSelectedItem(publisher);
        cboCategory.setSelectedItem(category);
        cboAuthor.setSelectedItem(author);
    }//GEN-LAST:event_bookTableMousePressed

    private void cancelBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBookActionPerformed
        loadDataBook();
        txtBookId.setText("");
        txtBookTitle.setText("");
        txtYear.setText("");
        txtSelling.setText("");
        txtImport.setText("");
        txtISBN.setText("");
        txtPage.setText("");
        cboPublisher.setSelectedIndex(0);
        cboCategory.setSelectedIndex(0);
        cboAuthor.setSelectedIndex(0);
    }//GEN-LAST:event_cancelBookActionPerformed

    private void updateBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBookActionPerformed
        int bookId = Integer.valueOf(txtBookId.getText());
        String bookName = txtBookTitle.getText().trim();
        String publicationYear = txtYear.getText().trim(); 
        Double sellingPrice = Double.valueOf(txtSelling.getText());
        Double importPrice = Double.valueOf(txtImport.getText());
        String isbn = txtISBN.getText().trim();
        int pageNumber = Integer.parseInt(txtPage.getText());

        int authorId = getAuthorId(cboAuthor.getSelectedItem().toString());
        int categoryId = getCategoryId(cboCategory.getSelectedItem().toString());
        int publisherId = getPublisherId(cboPublisher.getSelectedItem().toString());
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to update ?", "Update", JOptionPane.YES_NO_OPTION);
        if(confirm == 0) {      

            try {
                Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
                Statement st = con.createStatement();
                String sql =    "UPDATE books SET " +
                                "book_title = '" + bookName + "', " +
                                "publication_year = '" + publicationYear + "', " +
                                "selling_price = " + sellingPrice + ", " +
                                "import_price = " + importPrice + ", " +
                                "ISBN = '" + isbn + "', " +
                                "page_number = " + pageNumber + ", " +
                                "publisher_id = " + publisherId + ", " +
                                "category_id = " + categoryId + ", " +
                                "author_id = " + authorId +
                                " WHERE book_id = " + bookId;

                int rowAffected = st.executeUpdate(sql);
                if(rowAffected > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateBook.setEnabled(false);
                    
                    txtBookTitle.setEnabled(true);
                    txtYear.setEnabled(false);
                    txtSelling.setEnabled(false);
                    txtImport.setEnabled(false);
                    txtISBN.setEnabled(false);
                    txtPage.setEnabled(false);
                    cboPublisher.setEnabled(false);
                    cboCategory.setEnabled(false);
                    cboAuthor.setEnabled(false);
                }else {
                    JOptionPane.showMessageDialog(rootPane, "Update Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }
        loadDataBook();
        txtBookId.setText("");
        txtBookTitle.setText("");
        txtYear.setText("");
        txtSelling.setText("");
        txtImport.setText("");
        txtISBN.setText("");
        txtPage.setText("");
        cboPublisher.setSelectedIndex(0);
        cboCategory.setSelectedIndex(0);
        cboAuthor.setSelectedIndex(0);
    }//GEN-LAST:event_updateBookActionPerformed

    private void searchBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookActionPerformed
      
    String bookName = searchBookTitle.getText().trim();
    String publicationYear = searchYear.getText().trim();
    
    Double priceFrom = null;
    Double priceTo = null;

    String priceFromText = searchPriceFrom.getText().trim();
    String priceToText = searchPirceTo.getText().trim();

    if (!priceFromText.isEmpty()) {
        try {
            priceFrom = Double.valueOf(priceFromText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    if (!priceToText.isEmpty()) {
        try {
            priceTo = Double.valueOf(priceToText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    String isbn = txtISBN.getText().trim();
    String sql = "";

    if (!bookName.isEmpty()) {
        sql = "SELECT book_id FROM books WHERE book_title LIKE '%" + bookName + "%'";
    } else if (!publicationYear.isEmpty()) {
        sql = "SELECT book_id FROM books WHERE publication_year = '" + publicationYear + "'";
    } else if (!isbn.isEmpty()) {
        sql = "SELECT book_id FROM books WHERE ISBN = '" + isbn + "'";
    } else if (priceFrom != null && priceTo != null) {
        sql = "SELECT book_id FROM books WHERE selling_price BETWEEN " + priceFrom + " AND " + priceTo;
    } else if (!bookName.isEmpty() && !isbn.isEmpty()) {
        sql = "SELECT book_id FROM books WHERE book_title LIKE '%" + bookName + "%' AND ISBN = '" + isbn + "'";
    } else if (!bookName.isEmpty() && !isbn.isEmpty() && !publicationYear.isEmpty()) {
        sql = "SELECT book_id FROM books WHERE book_title LIKE '%" + bookName + "%' AND ISBN = '" + isbn + "' AND publication_year = '" + publicationYear + "'";
    } else {
        sql = "SELECT book_id FROM books WHERE book_title LIKE '%" + bookName + "%' AND ISBN = '" + isbn + "' AND publication_year = '" + publicationYear + "' AND selling_price BETWEEN " + priceFrom + " AND " + priceTo;
    }

    try {
        Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
        Statement st = con.createStatement();
        int totalBook = 0;
        String sql1 = """
                SELECT books.book_id, books.book_title, books.publication_year, books.selling_price, books.import_price, books.ISBN, books.page_number, publisher.publisher_name, category.category_name, author.author_name
                FROM books 
                JOIN publisher ON books.publisher_id = publisher.id
                JOIN author ON books.author_id = author.id
                JOIN category ON books.category_id = category.id
                WHERE books.book_id IN (""" + sql + ")";

        ResultSet rs = st.executeQuery(sql1);

        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("ID");
        table.addColumn("Book Title");
        table.addColumn("Publication Year");
        table.addColumn("Selling Price");
        table.addColumn("Import Price");
        table.addColumn("ISBN");
        table.addColumn("Page Number");
        table.addColumn("Publisher");
        table.addColumn("Category");
        table.addColumn("Author");

        while (rs.next()) {
            totalBook += 1;
            int id = rs.getInt("book_id");
            String name = rs.getString("book_title");
            String year = rs.getString("publication_year");
            Double sellingPrice = rs.getDouble("selling_price");
            Double importPrice = rs.getDouble("import_price");
            String isbnn = rs.getString("ISBN");
            int pageNumber = rs.getInt("page_number");
            String publisher = rs.getString("publisher_name");
            String category = rs.getString("category_name");
            String author = rs.getString("author_name");

            table.addRow(new Object[]{id, name, year, sellingPrice, importPrice, isbnn, pageNumber, publisher, category, author});
            bookTable.setModel(table);
        }
        
        if(totalBook == 0) {
            JOptionPane.showMessageDialog(rootPane, "No suitable books found!", "Error", JOptionPane.ERROR_MESSAGE);
            searchBookTitle.setText("");
            searchISBN.setText("");
            searchPirceTo.setText("");
            searchPriceFrom.setText("");
            searchYear.setText("");
            return;
        }
        txtTotalBook.setText(String.valueOf(totalBook));
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    searchBookTitle.setText("");
    searchISBN.setText("");
    searchPirceTo.setText("");
    searchPriceFrom.setText("");
    searchYear.setText("");
    }//GEN-LAST:event_searchBookActionPerformed

    private void insertBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBookActionPerformed
        
        txtBookTitle.setEnabled(true);
        txtYear.setEnabled(true);
        txtSelling.setEnabled(true);
        txtImport.setEnabled(true);
        txtISBN.setEnabled(true);
        txtPage.setEnabled(true);
        cboPublisher.setEnabled(true);
        cboCategory.setEnabled(true);
        cboAuthor.setEnabled(true);
        
        saveBook.setEnabled(true);
    }//GEN-LAST:event_insertBookActionPerformed

    private void fixBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixBookActionPerformed
        txtBookTitle.setEnabled(true);
        txtYear.setEnabled(true);
        txtSelling.setEnabled(true);
        txtImport.setEnabled(true);
        txtISBN.setEnabled(true);
        txtPage.setEnabled(true);
        cboPublisher.setEnabled(true);
        cboCategory.setEnabled(true);
        cboAuthor.setEnabled(true);
        updateBook.setEnabled(true);
    }//GEN-LAST:event_fixBookActionPerformed

    private void insertAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertAuthorActionPerformed
        saveAuthor.setEnabled(true);
        txtAuthorName.setEnabled(true);
        txtBirthdate.setEnabled(true);
        txtAuthorStory.setEnabled(true);
        maleOption.setEnabled(true);
        femaleOption.setEnabled(true);
        saveAuthor.setEnabled(true);
    }//GEN-LAST:event_insertAuthorActionPerformed

    private void fixAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixAuthorActionPerformed
        updateAuthor.setEnabled(true);
        txtAuthorName.setEnabled(true);
        txtBirthdate.setEnabled(true);
        txtAuthorStory.setEnabled(true);
        maleOption.setEnabled(true);
        femaleOption.setEnabled(true);
        
        fixAuthor.setEnabled(true);
    }//GEN-LAST:event_fixAuthorActionPerformed

    private void insertGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertGenreActionPerformed
        saveGenre.setEnabled(true);
        txtGenre.setEnabled(true); 
    }//GEN-LAST:event_insertGenreActionPerformed

    private void insertPublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertPublisherActionPerformed
        savePublisher.setEnabled(true);
        txtPublisher.setEnabled(true);
      
    }//GEN-LAST:event_insertPublisherActionPerformed

    private void fixpublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixpublisherActionPerformed
        updatePublisher.setEnabled(true);
        txtPublisher.setEnabled(true);
    
    }//GEN-LAST:event_fixpublisherActionPerformed
        
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Home home = new Home();
                home.setLocationRelativeTo(null);
                home.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField authorNameSearch;
    private javax.swing.JButton authorSearchBtn;
    private javax.swing.JScrollPane authorTable;
    private javax.swing.JLabel author_menu;
    private javax.swing.JPanel author_tab;
    private javax.swing.JTable authorsTable;
    private javax.swing.JTextField birthdateSearch;
    private javax.swing.JTable bookTable;
    private javax.swing.JPanel book_tab;
    private javax.swing.JLabel books_menu;
    private javax.swing.JButton cancelAuthor;
    private javax.swing.JButton cancelBook;
    private javax.swing.JButton cancelGenre;
    private javax.swing.JButton cancelPublisher;
    private javax.swing.JLabel category_menu;
    private javax.swing.JComboBox<String> cboAuthor;
    private javax.swing.JComboBox<String> cboCategory;
    private javax.swing.JComboBox<String> cboPublisher;
    private javax.swing.JLabel contact_menu;
    private javax.swing.JButton deleteAuthor;
    private javax.swing.JButton deleteBook;
    private javax.swing.JButton deleteGenre;
    private javax.swing.JButton deletePublisher;
    private javax.swing.JRadioButton femaleOption;
    private javax.swing.JButton fixAuthor;
    private javax.swing.JButton fixBook;
    private javax.swing.JButton fixGenre;
    private javax.swing.JButton fixpublisher;
    private javax.swing.JTable genreTable;
    private javax.swing.JPanel genre_tab;
    private javax.swing.JLabel home_menu;
    private javax.swing.JPanel home_tab;
    private javax.swing.JButton insertAuthor;
    private javax.swing.JButton insertBook;
    private javax.swing.JButton insertGenre;
    private javax.swing.JButton insertPublisher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel logout_menu;
    private javax.swing.JRadioButton maleOption;
    private javax.swing.JTextField publisherSearch;
    private javax.swing.JTable publisherTable;
    private javax.swing.JLabel publisher_menu;
    private javax.swing.JButton saveAuthor;
    private javax.swing.JButton saveBook;
    private javax.swing.JButton saveGenre;
    private javax.swing.JButton savePublisher;
    private javax.swing.JButton searchBook;
    private javax.swing.JTextField searchBookTitle;
    private javax.swing.JButton searchCategoryBtn;
    private javax.swing.JTextField searchGenre;
    private javax.swing.JTextField searchISBN;
    private javax.swing.JTextField searchPirceTo;
    private javax.swing.JTextField searchPriceFrom;
    private javax.swing.JButton searchPublisher;
    private javax.swing.JTextField searchYear;
    private javax.swing.JLabel support_menu;
    private javax.swing.JTextField txtAuthorId;
    private javax.swing.JTextField txtAuthorName;
    private javax.swing.JTextArea txtAuthorStory;
    private javax.swing.JTextField txtBirthdate;
    private javax.swing.JTextField txtBookId;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JTextField txtGenre;
    private javax.swing.JTextField txtGenreId;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtImport;
    private javax.swing.JTextField txtPage;
    private javax.swing.JTextField txtPublisher;
    private javax.swing.JTextField txtPublisherId;
    private javax.swing.JTextField txtSelling;
    private javax.swing.JTextField txtTotalBook;
    private javax.swing.JTextField txtYear;
    private javax.swing.JButton updateAuthor;
    private javax.swing.JButton updateBook;
    private javax.swing.JButton updateGenre;
    private javax.swing.JButton updatePublisher;
    // End of variables declaration//GEN-END:variables
}
