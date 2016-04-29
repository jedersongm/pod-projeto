
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.pingclient;

import br.ifpb.pod.pingserver.PingServer;
import br.ifpb.pod.sender.Sender;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jederson
 */

public class PingClientImpl extends UnicastRemoteObject implements PingClient, Runnable{
    
    private int latencia;
    
    public PingClientImpl() throws RemoteException, NotBoundException{
        super();
        this.latencia = test();
    }
    
     public int getLatencia() {
        
         return latencia;
    }

    public void setLatencia(int latencia) {
        this.latencia = latencia;
    }
    
    public int test() throws RemoteException, NotBoundException{
        int ti;
        int sum = 0;
        for (int i = 1; i < 4; i++) {
            ti = Tools.currentTime();
            lookup().ping();
            sum += (Tools.currentTime()-ti);
        }       
        return sum / 3;
    }
    
   
    private PingServer lookup() throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry("localhost", 8083);
        PingServer pingServer = (PingServer) registry.lookup("PingServer");
        return pingServer;
    }
    
    @Override
    public void notifyLatency(int latency) throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry("localhost", 8082);
        Sender sender = (Sender) registry.lookup("Sender");
        String msg = "High Latency: "+latency;
        sender.notifyHighLatency(msg);
    }
            
    @Override
    public void run() {
        try {
           while(true){
               int tp = test();
               setLatencia(tp);
               if(tp > 3){
                   notifyLatency(tp);
               }
               Thread.sleep(3000);
           }
        } catch (RemoteException ex) {
            Logger.getLogger(PingClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PingClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PingClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
