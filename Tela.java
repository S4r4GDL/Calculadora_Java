import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.swing.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;


public class Tela extends JFrame{
    //static Calculadora ca;
    protected JPanel calculadoraPanel, visorPanel, tecladoPanel, botoesPanel;
    protected JTextField escreveTf;
    final int L = 2;
    final Color azulCalculadora = new Color(174, 238, 232);
    final Color amareloCalculadora = new Color(253, 255, 194);
    final Color cianoEscuroCalculadora = new Color(45, 91, 75);
    final Color cinzaCalculadora = new Color(227, 227, 227);
    final Color verdeCalculadora = new Color(102, 248, 160);
    static Calculadora ca;
    static String calculo;
    static String historico;


    public Tela(){

        try {
            ca = Calculadora.getCalculadora();
            ca.setTela(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        montarFrame(banner(), montarVisor(), montarTeclado());
        mostraTela(this);
        calculo = "";
        historico = "";
    }

    private void montarFrame(Component banner, Component component, JPanel jPanel) {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("North", banner);
        getContentPane().add("Center", component);
        getContentPane().add("South", jPanel);
    }
    public void mostraTela(JFrame f)
    {
        f.setSize(411,411);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private JPanel montarTeclado() {
        tecladoPanel = new JPanel();
        tecladoPanel.setLayout(new GridLayout(5,4));
        JButton teclas[] = new JButton[20];
        String teclado[] = {"/", "x", "+", "-", "7", "8", "9", "%", "4", "5", "6", "√", "1", "2", "3", ".", "0", "Histórico", "Limpar", "Enter"};
        int i = 0;
        ActionListener acaoBotoes = new AcaoBotoes();
        for (JButton tecla : teclas) {
            tecla = new JButton();
            tecla.setText(teclado[i]);

            if (teclado[i].matches("[\\d]")) {
                tecla.setBackground(cinzaCalculadora);

            } else if(i == 19) {
                tecla.setBackground(verdeCalculadora);
            } else if (i > 16 && i < 19) {
                tecla.setBackground(amareloCalculadora);
            } else{
                tecla.setBackground(azulCalculadora);
            }
            tecla.setPreferredSize(new Dimension(100, 50));
            tecla.setFont(new Font("Arial", Font.BOLD, 15 ));
            tecla.addActionListener(acaoBotoes);
            tecladoPanel.add(tecla);
            i++;


        }

        return tecladoPanel;
    }

    private Component montarVisor() {


        escreveTf = new JTextField();
        escreveTf.setEditable(false);
        escreveTf.setFont(new Font("Arial", Font.PLAIN, 23));

        JScrollPane scrollPane = new JScrollPane(escreveTf);

        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);

        visorPanel = new JPanel();
        visorPanel.setLayout(new BorderLayout());
        visorPanel.add("Center", scrollPane);
        visorPanel.setBorder(BorderFactory.createMatteBorder(L, L, L, L, cinzaCalculadora));

        return visorPanel;
    }

    public Component banner(){

        calculadoraPanel = new JPanel();
        calculadoraPanel.setLayout(new BorderLayout());
        calculadoraPanel.setBackground(azulCalculadora);
        return calculadoraPanel.add("North", new JLabel(new ImageIcon("Calculadora_G-Zus.png")));

    }
    public void calcular(){
        String resposta;
        try {

            resposta = ca.calcular(calculo);
            calculo = resposta;
        } catch (IOException ex) {
            System.out.println("erro ao calcular\n");
            throw new RuntimeException(ex);

        }
        escreveTf.setText(resposta);
    }
    public void limpar(){
        escreveTf.setText("");
        calculo = "";
    }
    public void mostrarHistorico(){
        JOptionPane.showMessageDialog(null, historico, "Historico", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(historico);
    }
    public void inserir(String acao){
        if (acao.equals("-") && (calculo.isEmpty() || calculo.endsWith(";"))) {
            calculo += acao;
        } else if (!acao.matches("[\\d,\\.]")) {
            calculo += ";" + acao + ";";
        } else {
            calculo += acao;
        }
        String cal = escreveTf.getText();
        escreveTf.setText(cal + " " + acao);
    }

    public void setHistorico(String sat) {
        historico += sat + "\n";
    }

    public class AcaoBotoes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand())
            {
                case "Enter":

                    calcular();
                    break;
                case "Limpar":
                    limpar();
                    break;
                case "Histórico":
                    mostrarHistorico();
                    break;
                default:
                    inserir(e.getActionCommand());
                    break;
            }

        }
    }
}

