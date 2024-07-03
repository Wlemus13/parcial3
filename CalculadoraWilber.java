import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraWilber  {
    private JFrame frame;
    private JTextField display;
    private JButton[] numeros;
    private JButton[] operadores;
    private JButton igual;
    private JButton limpiar;

    private double numero1 = 0;
    private double numero2 = 0;
    private double resultado = 0;
    private int operador = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CalculadoraWilber  window = new CalculadoraWilber ();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CalculadoraWilber () {
        crearInterfaz();
        agregarAcciones();
    }

    private void crearInterfaz() {
        frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 400);

        display = new JTextField();
        display.setFont(new Font("Serif", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));
        frame.add(panel, BorderLayout.CENTER);

        numeros = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numeros[i] = new JButton(String.valueOf(i));
            panel.add(numeros[i]);
        }

        operadores = new JButton[4];
        operadores[0] = new JButton("+");
        operadores[1] = new JButton("-");
        operadores[2] = new JButton("*");
        operadores[3] = new JButton("/");

        igual = new JButton("=");
        limpiar = new JButton("C");

        for (int i = 0; i < 4; i++) {
            panel.add(operadores[i]);
        }

        panel.add(igual);
        panel.add(limpiar);
    }

    private void agregarAcciones() {
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();

                if (source instanceof JButton) {
                    JButton boton = (JButton) source;

                    if (boton == limpiar) {
                        numero1 = 0;
                        numero2 = 0;
                        resultado = 0;
                        operador = 0;
                        display.setText("");
                    } else if (boton == igual) {
                        numero2 = Double.parseDouble(display.getText());
                        switch (operador) {
                            case 1:
                                resultado = numero1 + numero2;
                                break;
                            case 2:
                                resultado = numero1 - numero2;
                                break;
                            case 3:
                                resultado = numero1 * numero2;
                                break;
                            case 4:
                                if (numero2 != 0) {
                                    resultado = numero1 / numero2;
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Error: no se puede dividir entre cero");
                                    return;
                                }
                                break;
                        }

                        display.setText(String.valueOf(resultado));
                        numero1 = 0;
                        numero2 = 0;
                        operador = 0;
                    } else if (boton.getText().matches("\\d")) {
                        String texto = display.getText();
                        if (texto.equals("0")) {
                            display.setText(boton.getText());
                        } else {
                            display.setText(texto + boton.getText());
                        }
                    } else {
                        String texto = display.getText();
                        if (!texto.equals("")) {
                            numero1 = Double.parseDouble(texto);
                            display.setText("");
                        }

                        if (boton.getText().equals("+")) {
                            operador = 1;
                        } else if (boton.getText().equals("-")) {
                            operador = 2;
                        } else if (boton.getText().equals("*")) {
                            operador = 3;
                        } else if (boton.getText().equals("/")) {
                            operador = 4;
                        }
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            numeros[i].addActionListener(listener);
        }

        for (int i = 0; i < 4; i++) {
            operadores[i].addActionListener(listener);
        }

        igual.addActionListener(listener);
        limpiar.addActionListener(listener);
    }
}