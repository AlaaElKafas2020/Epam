package org.example;
import java.io.*;
import java.sql.*;

public class FileDAO {
    private final String url;
    private final String username;
    private final String password;

    public FileDAO(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void saveFile(String filename, long filesize, byte[] data, Date expirationDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             CallableStatement stmt = conn.prepareCall("{CALL save_file(?,?,?,?)}")) {
            stmt.setString(1, filename);
            stmt.setLong(2, filesize);
            InputStream inputStream = new ByteArrayInputStream(data);
            stmt.setBinaryStream(3, inputStream, data.length);
            stmt.setDate(4, expirationDate);
            stmt.executeUpdate();
        }
    }

    public File getFile(int id) throws SQLException, IOException {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             CallableStatement stmt = conn.prepareCall("{CALL get_file(?,?,?,?,?)}")) {
            stmt.setInt(1, id);
            stmt.registerOutParameter(2, Types.VARCHAR);
            stmt.registerOutParameter(3, Types.BIGINT);
            stmt.registerOutParameter(4, Types.BLOB);
            stmt.registerOutParameter(5, Types.DATE);
            stmt.execute();

            String fileName = stmt.getString(2);
            long fileSize = stmt.getLong(3);
            InputStream inputStream = stmt.getBlob(4).getBinaryStream();
            Date expirationDate = stmt.getDate(5);

            // Create a temporary file to hold the retrieved data
            File tempFile = File.createTempFile("file", ".tmp");
            tempFile.deleteOnExit();

            // Copy the data from the input stream to the temporary file
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Set the file name, size, and expiration date as attributes of the temporary file
            tempFile.setLastModified(expirationDate.getTime());
            tempFile.setReadable(true);
            tempFile.setWritable(true);
            tempFile.setExecutable(false);

            // Return the temporary file
            return tempFile;
        }
    }
}
