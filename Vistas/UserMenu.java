package Vistas;

import Controlador.*;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserMenu extends javax.swing.JFrame {

    Conexion conexion = new Conexion();
    Connection connection;
    Statement st;
    ResultSet rs;
    DefaultTableModel contenidoTabla, contenidoTabladirecciones, contenidoTablaEmpleados;
    ComboBoxModel enumDepartamentos, enumZonas, enumTipoCalles;

    public UserMenu() {
        enumDepartamentos = new DefaultComboBoxModel(EnumDepartamento.values());
        enumZonas = new DefaultComboBoxModel(EnumZona.values());
        enumTipoCalles = new DefaultComboBoxModel(EnumTipoCalle());
        initComponents();
        this.setLocationRelativeTo(this);
        listarEmpleados();
        listarDepartamentos();

    }

    public void listarDepartamentos() {
        String filtroDepartamento = txtSearchDepartamento.getText();
        if (filtroDepartamento.isEmpty()) {
            String query = "SELECT nombreSucursal,  nombreDepartamento, CONCAT('Zona', zona,'. ',tipoCalle, ' ',numero1,' # ',numero2, ' - ', numero3 )AS direccion FROM `sucursal` INNER JOIN `direccion`ON FK_idDireccion = idDireccion GROUP BY nombreSucursal,  nombreDepartamento ORDER BY nombreDepartamento;";
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(query);
                Object[] direccion = new Object[3];
                while (rs.next()) {
                    direccion[0] = rs.getString(" nombreSucursal ");
                    direccion[1] = rs.getString(" nombreDepartamento ");
                    direccion[2] = rs.getString(" direccion ");

                    contenidoTabladirecciones.addRow(direccion);
                    tblDepartamentos.setModel(contenidoTabladirecciones);

                }

            } catch (SQLException e) {
                System.out.println(e);
            }

        } else {
            String query = "SELECT nombreSucursal,  nombreDepartamento, CONCAT('Zona', zona,'. ',tipoCalle, ' ',numero1,' # ',numero2, ' - ', numero3 )AS direccion FROM `sucursal` INNER JOIN `direccion`ON FK_idDireccion = idDireccion AND nombreDepartamento = LIKE '%" + filtroDepartamento + "%' GROUP BY nombreSucursal,  nombreDepartamento ORDER BY nombreDepartamento;";

            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(query);
                Object[] direccion = new Object[3];
                contenidoTabladirecciones = (DefaultTableModel) tblDepartamentos.getModel();
                while (rs.next()) {
                    direccion[0] = rs.getString(" nombreSucursal ");
                    direccion[1] = rs.getString(" nombreDepartamento ");
                    direccion[2] = rs.getString(" direccion ");

                    contenidoTabladirecciones.addRow(direccion);
                    tblDepartamentos.setModel(contenidoTabladirecciones);

                }

            } catch (SQLException e) {
                System.out.println(e);

            }
        }
    }

    private void eliminarDatosTablaDepartamento() {
        for (int i = 0; i < tblDepartamentos.getRowCount(); i++) {
            contenidoTabladirecciones.removeRow(i);
            i -= 1;
        }
        cbDepartamento.setSelectedIndex(0);
        cbTipoCalle.setSelectedIndex(0);
        cbZona.setSelectedIndex(0);
        txtNumero1.setText("");
        txtNumero2.setText("");
        txtNumero3.setText("");

    }

    public void listarEmpleados() {
        String filtroBusqueda = txtSearch.getText();
        System.out.println("Buscar los nombres con Nombre o Apellido" + filtroBusqueda);

        if (filtroBusqueda.isEmpty()) {
            String query = "SELECT nombreEmp, apellidos, tipoDocumento, documento, correo, nombreSucursal FROM `empleado` INNER JOIN sucursal ON empleado.FK_idSucursal = sucursal.idSucursal";
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(query);
                Object[] empleados = new Object[6];
                contenidoTablaEmpleados = (DefaultTableModel) tblEmpleados.getModel();

                while (rs.next()) {

                    empleados[0] = rs.getString("nombreEmp");
                    empleados[1] = rs.getString("apellidos");
                    empleados[2] = rs.getString("tipoDocumento");
                    empleados[3] = rs.getString("numeroDocumento");
                    empleados[4] = rs.getString("correo");
                    empleados[5] = rs.getString("nombreSucursal");

                    contenidoTablaEmpleados.addRow(empleados);
                    tblEmpleados.setModel(contenidoTablaEmpleados);

                }

            } catch (SQLException e) {
                System.out.println("No se pudo cargar informaion de los empleados");
            }

        } else {
            String query = "SELECT nombreEmp, apellidos, tipoDocumento, documento, correo, nombreSucursal FROM `empleado` INNER JOIN sucursal WHERE empleado.FK_idSucursal = sucursal.idSucursal AND nombreEmp LIKE '%" + filtroBusqueda + "%'OR apellidos LIKE '%" + filtroBusqueda + "%';";
            System.out.println(query);
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(query);
                Object[] empleados = new Object[6];
                contenidoTablaEmpleados = (DefaultTableModel) tblEmpleados.getModel();

                while (rs.next()) {
                    empleados[0] = rs.getString("nombreEmp");
                    empleados[1] = rs.getString("apellidos");
                    empleados[2] = rs.getString("tipoDocumento");
                    empleados[3] = rs.getString("numeroDocumento");
                    empleados[4] = rs.getString("correo");
                    empleados[5] = rs.getString("nombreSucursal");

                    System.out.println("idEmp" + empleados[0] + ",nombre:" + empleados[1]
                            + "Tipo Documento" + empleados[2] + " " + empleados[3]
                            + "Correo" + empleados[4]);

                    contenidoTablaEmpleados.addRow(empleados);
                    tblEmpleados.setModel(contenidoTablaEmpleados);

                }

            } catch (SQLException e) {
                System.out.println("No se pudo cargar informaion de los empleados");
            }

        }

    }

    private void eliminarDatosTablaEmpleado() {
        for (int i = 0; i < tblEmpleados.getRowCount(); i++) {
            contenidoTablaEmpleados.removeRow(i);
            i -= 1;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtSearchDepartamento = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnListarEmpleados = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbDepartamento = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbZona = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtNumero1 = new javax.swing.JTextField();
        txtNumero2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        cbTipoCalle = new javax.swing.JComboBox<>();
        txtNumero3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDepartamentos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/logo.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/showUser.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Departamento");

        btnListarEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/avatar.png"))); // NOI18N
        btnListarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarEmpleadosActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel7.setText("Departamento");

        cbDepartamento.setModel(enumDepartamentos);

        jLabel8.setText("Zona");

        cbZona.setModel(enumZonas);

        jLabel9.setText("Numero");

        jLabel10.setText("Tipo Calle");

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/confirmIcon.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        cbTipoCalle.setModel(enumTipoCalles);

        jLabel13.setText("-");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sucursal", "Departamento", "Direccion"
            }
        ));
        tblDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDepartamentosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDepartamentos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(244, 244, 244))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSearchDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnListarEmpleados)))
                        .addGap(110, 116, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnListarEmpleados))
                .addGap(188, 188, 188))
        );

        jTabbedPane1.addTab("Sucursales", jPanel4);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido(s)", "Tipo Doc", "Documen", "Correo", "Sucursal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpleados);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/logo.png"))); // NOI18N

        jLabel2.setText("Empleados");

        jLabel3.setText("Mision TIC 2022");

        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/avatar.png"))); // NOI18N
        btnAddUser.setText("Nuevo");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombre Empleado");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/showUser.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(105, 105, 105)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(118, 118, 118)
                                        .addComponent(jLabel2))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddUser)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(84, 84, 84))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Empleados", jPanel1);

        jTabbedPane2.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.addTab("Linea de Ventas", jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        eliminarDatosTablaEmpleado();
        listarEmpleados();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        // Creamos instancia de Jdialog para crear usuarios
        AddUserForm addUser = new AddUserForm(this, rootPaneCheckingEnabled);
        addUser.setVisible(true);
        eliminarDatosTablaEmpleado();
        listarEmpleados();
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void tblEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosMouseClicked
        // Capturar numero de filas seleccionado
        int row = tblEmpleados.getSelectedRow();
        String nombre = (String) tblEmpleados.getValueAt(row, 0);
        String apellidos = (String) tblEmpleados.getValueAt(row, 1);
        String tipoDocumento = (String) tblEmpleados.getValueAt(row, 2);
        String documento = (String) tblEmpleados.getValueAt(row, 3);
        String correo = (String) tblEmpleados.getValueAt(row, 4);
        String sucursal = (String) tblEmpleados.getValueAt(row, 5);

        String queryIdEmpleado = "SELECT idEmp FROM empleado WHERE documento ='" + documento + "';";
        System.out.println(queryIdEmpleado);
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(queryIdEmpleado);
            while (rs.next()) {
                int idEmpleado = rs.getInt("idEmp");
                SowUserForm showUserForm = new SowUserForm(this, true);
                showUserForm.recibeDatos(idEmpleado, nombre, apellidos, documento, correo, sucursal);

                showUserForm.setVisible(true);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        eliminarDatosTablaEmpleado();
        listarEmpleados();


    }//GEN-LAST:event_tblEmpleadosMouseClicked

    private void tblDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDepartamentosMouseClicked
        // TODO add your handling code here:
        int row = tblDepartamentos.getSelectedRow();
        String sucursal = (String) tblDepartamentos.getValueAt(row, 0);
        String departamento = (String) tblDepartamentos.getValueAt(row, 1);
        String queryIdSucursal = "SELECT idSucursal FROM `sucursal` INNER JOIN `direccion`WHERE FK_idDireccion = idDireccion AND nombresucursal = '" + sucursal + "';";
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(queryIdSucursal);

            while (rs.next()) {
                int idSucursal = rs.getInt("idSucursal");
                GestionarSucursalForm gestionarSucursal = new GestionarSucursalForm(this, true);
                gestionarSucursal.recibirDatosSucursal(idSucursal);
                gestionarSucursal.setVisible(true);
                eliminarDatosTablaDepartamento();
                listarDepartamentos();

            }

        } catch (SQLException e) {
            System.out.println(e);
        }


    }//GEN-LAST:event_tblDepartamentosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        eliminarDatosTablaDepartamento();
        listarDepartamentos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnListarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarEmpleadosActionPerformed
        // TODO add your handling code here:

        int filaSeleccionada = tblDepartamentos.getSelectedRow();
        String sucursal = tblDepartamentos.getValueAt(filaSeleccionada, 0).toString();
        String query = "SELECT idSucursal FROM sucursal WHERE nombreSucursal '" + sucursal + "';";

        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                int idSucursal = rs.getInt("idSucursal");
                EmpleadoList empleadosList = new EmpleadoList(this, true);
                empleadosList.setVisible(true);
                empleadosList.recibirIdSucursal(idSucursal);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_btnListarEmpleadosActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        String departamento = cbDepartamento.getSelectedItem().toString();
        String zona = cbZona.getSelectedItem().toString();
        String tipoCalle = cbTipoCalle.getSelectedItem().toString();
        String numero1 = txtNumero1.getText();
        String numero2 = txtNumero2.getText();
        String numero3 = txtNumero3.getText();
        if (departamento != "Selecciona una opcion" && tipoCalle != "Selecciona una opcion" && zona != "Selecciona una opcion" && !numero1.isEmpty() && !numero2.isEmpty() && !numero3.isEmpty()) {
            SucursalForm sucursalForm = new SucursalForm(this, true);
            sucursalForm.setVisible(true);
            sucursalForm.recibirDatosDreccion(departamento, zona, tipoCalle, numero1, numero2, numero3);
            eliminarDatosTablaDepartamento();
            listarDepartamentos();
         
        }else{
            JOptionPane.showMessageDialog(this,"Faltan campos por diligenciar","Direccion",JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnListarEmpleados;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbDepartamento;
    private javax.swing.JComboBox<String> cbTipoCalle;
    private javax.swing.JComboBox<String> cbZona;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblDepartamentos;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtNumero1;
    private javax.swing.JTextField txtNumero2;
    private javax.swing.JTextField txtNumero3;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchDepartamento;
    // End of variables declaration//GEN-END:variables

    private Object[] EnumTipoCalle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
