/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.receiver;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author jederson
 */
public class Main {
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
       // System.setProperty(java.rmi.server.hostname, "")
        Receiver receiver = new ReceiverImpl();
        
        Registry registry = LocateRegistry.createRegistry(8081);
        registry.bind("Receiver", receiver);
        System.out.println("Receiver ativo...");
        
    }
}
