import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Radiciacao implements Calculo, Runnable{

    private Socket socket;
    Radiciacao() throws IOException {
        socket = new Socket("127.0.0.1", 12345);
    }

    @Override
    public float calcular(float a, float b) {

        return (float) Math.sqrt(a);
    }
    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;

    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String calculo;

            while(true)
            {
                try{ if ((calculo = entrada.readLine()) != null) {
                    System.out.println("Radiciação\n");
                    saida.println(calcular(Float.parseFloat(valorCampo(calculo, 0)), 0));
                }
                } catch (NumberFormatException e) {
                    saida.println("ERRO");
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String valorCampo(String txt, int indice) {
        String [] campos = txt.split(";");
        return campos[indice];
    }
}
