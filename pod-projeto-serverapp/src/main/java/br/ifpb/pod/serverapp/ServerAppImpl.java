/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.serverapp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jederson
 */
public class ServerAppImpl extends UnicastRemoteObject implements ServerApp{
    private Random r;
    
    public  ServerAppImpl() throws RemoteException{
        super();
        r = new Random();
    }
    
    @Override
    public String processMessage(String msg) throws RemoteException {
        int t = 0;
        try {
            t = 5 - r.nextInt(3);
            Thread.sleep(t);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerAppImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Hello World "+msg+" ("+t+")";
    }
    
}
