package gmail.dankim0124.datacallingdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gmail.dankim0124.datacallingdemo.model.SocketTick;
import gmail.dankim0124.datacallingdemo.repository.SocketTickRepository;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import okio.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;


@Component
public class SampleRunner implements ApplicationRunner {

    @Autowired
    OkHttpClient basicOkHttpClient;

    @Autowired
    ApplicationContext ctx;

    private volatile String[] COINS =
            {"KRW-STX", "KRW-SOL", "KRW-DOT",};


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Request request = new Request.Builder().url("wss://api.upbit.com/websocket/v1").build();
        EchoWebSocketListener listener = (EchoWebSocketListener) ctx.getBean(EchoWebSocketListener.class);
        ArrayList<String> sampleCoins = new ArrayList<>();
        for(int i =0; i< 3; i++){
            sampleCoins.add(COINS[i]);
        }
        listener.setListener("dankim0124","trade",sampleCoins,"SIMPLE");
        WebSocket ws = basicOkHttpClient.newWebSocket(request, listener);


    }

}


@Component
@Scope(value = "prototype")
@Getter
@Setter
class EchoWebSocketListener extends WebSocketListener {

    @Autowired
    SocketTickRepository socketTickRepository;

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private static Long tickCount = 0L;

    private String ticket;
    private String type;
    private ArrayList<String> codes;
    private String format;

    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println(getRequestMessage());
        webSocket.send(getRequestMessage());
        System.out.println("opened");
    }


    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        String converted = bytes.utf8();
        try {
            SocketTick socketTick = objectMapper.readValue(converted, SocketTick.class);
            socketTickRepository.save(socketTick);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        System.out.println("Closing: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

    public void setListener(String ticket, String type, ArrayList<String> codes, String format) {
        this.setTicket(ticket);
        this.setType(type);
        this.setCodes(codes);
        this.setFormat(format);
    }

    public String getRequestMessage() {

        String codesString = "[";
        for (int i = 0; i < codes.size(); i++) {
            codesString += "\"" + codes.get(i) + "\"";
            if (i != codes.size() - 1) {
                codesString += ",";
            }
        }
        codesString += "]";
        System.out.println(codesString);

        return String.format("[{\"ticket\":\"%s\"},{\"type\":\"%s\",\"codes\":%s}," + "{\"format\":\"%s\"}]", ticket, type, codesString, format);

    }
}
