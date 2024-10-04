import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PanelEncuesta {
    private JPanel Formulario;
    private JTextField textField1;
    private JTextField textField2;
    private JCheckBox hombreCheckBox;
    private JCheckBox mujerCheckBox;
    private JCheckBox otroCheckBox;
    private JComboBox<String> comboBox1;
    private JButton guardarButton;
    private JButton cancelarButton;
    private ButtonGroup generoGroup;

    public PanelEncuesta() {
        generoGroup = new ButtonGroup();
        generoGroup.add(hombreCheckBox);
        generoGroup.add(mujerCheckBox);
        generoGroup.add(otroCheckBox);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String apellido = textField2.getText();
                String genero = "";
                if (hombreCheckBox.isSelected()) genero = "Hombre";
                if (mujerCheckBox.isSelected()) genero = "Mujer";
                if (otroCheckBox.isSelected()) genero = "Otro";
                String opcion = (String) comboBox1.getSelectedItem();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO genero (nombre, apellido, genero, opcion) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, nombre);
                        statement.setString(2, apellido);
                        statement.setString(3, genero);
                        statement.setString(4, opcion);
                        statement.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(Formulario,
                            "Datos guardados correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Formulario,
                            "Error al guardar los datos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                JOptionPane.showMessageDialog(Formulario,
                        "Nombre: " + nombre + "\n" +
                                "Apellido: " + apellido + "\n" +
                                "Género: " + genero + "\n" +
                                "Opción seleccionada: " + opcion,
                        "Resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Operación cancelada");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PanelEncuesta");
        frame.setContentPane(new PanelEncuesta().Formulario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
