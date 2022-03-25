package gmail.dankim0124.datacallingdemo;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class SampleRunner implements ApplicationRunner {

    @Autowired
    OkHttpClient basicOkHttpClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Request request = new Request.Builder().url("wss://api.upbit.com/websocket/v1").build();

        EchoWebSocketListener listener = new EchoWebSocketListener();

        WebSocket ws = basicOkHttpClient.newWebSocket(request, listener);

    }
}


class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private static Long tickCount = 0L;
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("[{\"ticket\":\"UNIQUE_TICKET\"},{\"type\":\"trade\",\"codes\":[\"KRW-BTC\",\"KRW-ETH\",\"BTC-ETH\",\"BTC-LTC\",\"BTC-XRP\",\"BTC-ETC\",\"BTC-OMG\",\"KRW-XRP\",\"BTC-CVC\",\"BTC-DGB\",\"BTC-SC\",\"BTC-SNT\",\"BTC-WAVES\",\"BTC-NMR\",\"BTC-XEM\"]}]");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving: " + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("tickCount : " + ++tickCount);
        String converted =bytes.utf8();
        System.out.println("Receiving: " + converted);
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

}
