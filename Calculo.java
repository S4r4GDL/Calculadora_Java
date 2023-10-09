import java.io.IOException;
import java.net.Socket;

public interface Calculo {

    float calcular(float a, float b);
    void setSocket(Socket socket);
    Socket getSocket();

}
