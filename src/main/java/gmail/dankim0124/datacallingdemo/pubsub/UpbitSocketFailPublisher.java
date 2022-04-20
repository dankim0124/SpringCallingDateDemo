package gmail.dankim0124.datacallingdemo.pubsub;

public interface UpbitSocketFailPublisher {
        void notifyUpbitSocketFail();
        void registerSubscriber(UpbitSocketFailSubscriber subscriber);
}
