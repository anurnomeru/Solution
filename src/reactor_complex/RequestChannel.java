package reactor_complex;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anur IjuoKaruKas on 2018/12/13
 */
public class RequestChannel {

    private ArrayBlockingQueue<Request> requestQueue;

    private ArrayBlockingQueue<Response> responseQueue;

    public RequestChannel() {
        requestQueue = new ArrayBlockingQueue<>(100);
        responseQueue = new ArrayBlockingQueue<>(100);
    }

    public void sendRequest(Request request) {
        try {
            requestQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Request receiveRequest(long timeout) {
        Request request = null;
        try {
            request = requestQueue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return request;
    }

    public Request receiveRequest() {
        return requestQueue.poll();
    }

    public Request receiveRequestBlocking() {
        Request request = null;
        try {
            request = requestQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void sendResponse(Response request) {
        try {
            responseQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Response receiveResponse(long timeout) {
        Response response = null;
        try {
            response = responseQueue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response receiveResponse() {
        return responseQueue.poll();
    }
}
