package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Usuario;
import service.UsuarioService;

public class UsuarioView extends JFrame {
    private JTextField idField;
    private JTextField nombreField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JTextArea usuariosArea;
    private UsuarioService usuarioService;

    public UsuarioView() {
        usuarioService = new UsuarioService();

        setTitle("Gestión de Usuarios - Nuevo Diseño");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 240, 255));

        // Encabezado
        JLabel headerLabel = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(new Color(50, 50, 150));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Panel Principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(230, 240, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // Panel de Formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(20);
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        formPanel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        telefonoField = new JTextField(20);
        formPanel.add(telefonoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Panel de Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(230, 240, 255));

        JButton registrarButton = new JButton("Registrar");
        registrarButton.setPreferredSize(new Dimension(150, 40));
        registrarButton.setBackground(new Color(0, 153, 76));
        registrarButton.setForeground(Color.WHITE);
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        buttonPanel.add(registrarButton);

        JButton obtenerButton = new JButton("Mostrar Usuarios");
        obtenerButton.setPreferredSize(new Dimension(150, 40));
        obtenerButton.setBackground(new Color(0, 102, 204));
        obtenerButton.setForeground(Color.WHITE);
        obtenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerUsuarios();
            }
        });
        buttonPanel.add(obtenerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel de Usuarios Registrados
        usuariosArea = new JTextArea();
        usuariosArea.setEditable(false);
        usuariosArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        usuariosArea.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(usuariosArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Usuarios Registrados"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void registrarUsuario() {
        try {
            String id = idField.getText();
            String nombre = nombreField.getText();
            String telefono = telefonoField.getText();
            String email = emailField.getText();

            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);

            String mensaje = usuarioService.crearUsuario(usuario);
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obtenerUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getUsuarios();
            usuariosArea.setText("");
            for (Usuario usuario : usuarios) {
                usuariosArea.append(usuario.toString() + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        telefonoField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UsuarioView().setVisible(true);
            }
        });
    }
}
