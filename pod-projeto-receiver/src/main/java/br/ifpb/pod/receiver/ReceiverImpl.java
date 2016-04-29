/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.receiver;

import br.ifpb.pod.serverapp.ServerApp;
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
public class ReceiverImpl extends UnicastRemoteObject implements Receiver{
    
    public ReceiverImpl() throws RemoteException{
        super();
    }

    @Override
    public String sendServerApp(String msg) throws RemoteException {
        try {
            Thread.sleep(170000);
            return lookup().processMessage(msg);
        } catch (NotBoundException ex) {
            Logger.getLogger(ReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public String receive(String msg) throws RemoteException {
        return sendServerApp(msg);
    }
    
    private ServerApp lookup() throws RemoteException, NotBoundException{
        
        Registry registry = LocateRegistry.getRegistry("localhost",8080);
        ServerApp server = (ServerApp) registry.lookup("ServerApp");
        
        return server;
    }
    
}
