package com.c8db;

import com.arangodb.velocypack.VPackSlice;
import com.c8db.http.HTTPEndPoint;
import com.c8db.http.HTTPMethod;
import com.c8db.http.HTTPRequest;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Example {
    public static void main(String[] args) throws IOException, CredentialException {

        C8DB db = new C8DB.Builder()
                .hostName("api-varden-4f0f3c4f.paas.macrometa.io")
                .port(443)
                .apiKey("madu140_gmail.com.AccessPortal.2PL8EeyIAMn2sx7YHKWMM58tmJLES4NyIWq6Cnsj0BTMjygJyF3b14zb2sidcauXccccb8")
                .build();

        HTTPEndPoint endPoint = new HTTPEndPoint("/_api/key/AccessPortal/database?full=true");

        HTTPRequest request = new HTTPRequest.Builder()
                .RequestType(HTTPMethod.GET)
                .EndPoint(endPoint)
                .build();

        VPackSlice responseBody = db.execute(request);
        while(true) {
            VPackSlice r = db.execute(request);
            System.out.println(r.toString());
        }
//         class Producer implements Runnable{
//
//            protected BlockingQueue queue = null;
//
//            public Producer(BlockingQueue queue) {
//                this.queue = queue;
//            }
//
//            public void run() {
//                try {
//                    C8DB db = new C8DB.Builder()
//                            .hostName("api-varden-4f0f3c4f.paas.macrometa.io")
//                            .port(443)
//                            .apiKey("madu140_gmail.com.AccessPortal.2PL8EeyIAMn2sx7YHKWMM58tmJLES4NyIWq6Cnsj0BTMjygJyF3b14zb2sidcauXccccb8")
//                            .build();
//
//                    HTTPEndPoint endPoint = new HTTPEndPoint("/_api/key/AccessPortal/database?full=true");
//
//                    HTTPRequest request = new HTTPRequest.Builder()
//                            .RequestType(HTTPMethod.GET)
//                            .EndPoint(endPoint)
//                            .build();
//
//                            while(true) {
//                                System.out.println("sending request to API");
//                                VPackSlice r = db.execute(request);
//                                Thread.sleep(0);
//                                Date l = new Date();
//                                queue.put(l.getTime());
//                            }
//                } catch (CredentialException | IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//         class Consumer implements Runnable{
//
//            protected BlockingQueue queue = null;
//
//            public Consumer(BlockingQueue queue) {
//                this.queue = queue;
//            }
//
//            public void run() {
//                try {
//                    while(true) {
//                        System.out.println(queue.remainingCapacity());
//                        Date now = new Date();
//                        long r = (long) queue.take();
//                        Thread.sleep(1000);
//                        System.out.println(r + " " + (now.getTime() - r));
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        BlockingQueue queue = new ArrayBlockingQueue(10);
//
//        Producer producer = new Producer(queue);
//        Consumer consumer = new Consumer(queue);
//
//        new Thread(producer).start();
//        new Thread(consumer).start();

//        Thread.sleep(4000);
    }
}
