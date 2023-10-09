

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    static final int porta = 12345;
    static Socket cliente;
    static Socket soma;
    static Socket subtracao;
    static Socket multiplicacao;
    static Socket divisao;
    static Socket radiciacao;
    static Socket percentual;

    public static void main(String[] args){

        try{


            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("Porta 12345 aberta!\nCalculadora pode ser aberta!\n");

                cliente = servidor.accept();
                soma = servidor.accept();
                subtracao = servidor.accept();
                multiplicacao = servidor.accept();
                divisao = servidor.accept();
                radiciacao = servidor.accept();
                percentual = servidor.accept();

                System.out.println("Nova conexão com o cliente"+cliente.getInetAddress().getHostAddress());
                ligarOperacao(cliente);


        } catch(IOException e){
            e.printStackTrace();
        }


    }

    private static void ligarOperacao(Socket cliente) throws IOException {
        PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        String calculo;

        while (true) {
            if ((calculo = entrada.readLine())!= null) {
                System.out.println(calculo);

                if (calculo.equals("EXIT")) {
                    System.out.println("Cliente desconectado.");
                    break;
                }

                Socket operacao = selecionarSocket(valorCampo(calculo, 1));
                System.out.println(operacao.toString());
                System.out.println(cliente.toString());
                    PrintWriter saida2 = new PrintWriter(operacao.getOutputStream(), true);
                    saida2.println(calculo);
                    BufferedReader entrada2 = new BufferedReader(new InputStreamReader(operacao.getInputStream()));
                    saida.flush();
                    String resposta;
                    if((resposta = entrada2.readLine()) != null)
                        saida.println(resposta);
                    System.out.println(resposta);
                    saida.flush();
                    operacao=null;

            }
        }
    }

    private static Socket selecionarSocket(String s) {
        switch (s){
            case "+" :
                return soma;
            case "-" :
                return subtracao;
            case "x" :
                return multiplicacao;
            case "/" :
                return divisao;
            case "%" :
                return percentual;
            case "√" :
                return radiciacao;
            default :
                return cliente;
        }
    }


    public static String valorCampo(String txt, int indice) {
        String [] campos = txt.split(";");
        return campos[indice];
    }
}
