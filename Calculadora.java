import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Calculadora {

    private static Tela tela;
    static Calculadora calculadora;
    private static Socket socket;

    //implementando singleton na calculadora
    public static synchronized Calculadora getCalculadora() throws IOException {
        if (calculadora == null)
        {
            calculadora = new Calculadora();
            socket = new Socket("127.0.0.1", 12345);
        }

        return calculadora;
    }
    Calculadora() throws IOException {

    }


    public void setTela(Tela tela)
    {
        this.tela = tela;
    }
    public static String calcular(String env) throws IOException {
        iniciarThread(new Soma());
        iniciarThread(new Subtracao());
        iniciarThread(new Multiplicacao());
        iniciarThread(new Divisao());
        iniciarThread(new Radiciacao());
        iniciarThread(new Percentual());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(env);
        System.out.println(env);
        System.out.println("enviou pro server");
        BufferedReader resposta = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String sat = resposta.readLine();
        System.out.println(sat);
        tela.escreveTf.setText(sat);
        tela.setHistorico(env + "=" + sat);
        return sat;

    }
    public static void iniciarThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
