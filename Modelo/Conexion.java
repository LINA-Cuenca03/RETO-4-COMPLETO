
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    Connection connection;

    public Conexion() {
        //intentar conexion con la base de datos 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/reto 1","root","");
            if(connection != null){
                System.out.println("Conexion Exitosa");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Conexion Fallida");
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    
}
