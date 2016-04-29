/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.sender;

import br.ifpb.pod.pingclient.PingClient;
import br.ifpb.pod.receiver.Receiver;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jederson
 */
class SenderImpl extends UnicastRemoteObject implements Sender {

    private String addressReceiver = "localhost";
    private Map<String, String> db;

    public SenderImpl() throws RemoteException {
        super();
        this.db = new HashMap<>();
        this.db.put("ERROR", null);
        this.db.put("RESPONSE", null);
        this.db.put("LATENCY", null);
    }

    public String getAddressReceiver() {
        return addressReceiver;
    }

    public void setAddressReceiver(String addressReceiver) {
        this.addressReceiver = addressReceiver;
    }

    public Map<String, String> getDb() {
        return db;
    }

    public void setDb(Map<String, String> db) {
        this.db = db;
    }

    @Override
    public void comunicador(final String msg) throws RemoteException {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                String response;
                int ti, tf;
                try {
                    do {
                        ti = Tools.currentTime();
                        response = lookup().receive(msg);
                        tf = (Tools.currentTime() - ti);
                        } while (response == null && tf < 180);

                    if (response != null) {
                        SenderImpl.this.db.put("RESPONSE", response);
                    } else {
                       SenderImpl.this.db.put("ERROR", "Timeout: maior 3 min");
                        SenderImpl.this.comunicador(msg);
                    }
                } catch (NotBoundException ex) {
                    SenderImpl.this.db.put("ERROR", ex.getMessage());
                    try {
                        SenderImpl.this.comunicador(msg);
                    } catch (RemoteException ex1) {
                        Logger.getLogger(SenderImpl.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(SenderImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        Thread t = new Thread(r);
        t.start();

    }

    public Receiver lookup() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(this.addressReceiver, 8081);
        Receiver receiver = (Receiver) registry.lookup("Receiver");
        return receiver;
    }

    @Override
    public int lookupPing() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(this.addressReceiver, 8084);
        PingClient pingClient = (PingClient) registry.lookup("PingClient");
        return pingClient.getLatencia();
    }

    @Override
    public void notifyHighLatency(String msg) throws RemoteException, NotBoundException {
        this.db.put("LATENCY", msg);
    }

}
