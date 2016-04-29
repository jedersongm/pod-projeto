/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.clientapp;

import br.ifpb.pod.sender.Sender;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author jederson
 */
public class ClientApp {
    
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        
        Registry registry = LocateRegistry.getRegistry("localhost", 8082);
        Sender sender = (Sender) registry.lookup("Sender");
        sender.comunicador("Raul Seixas");
        System.out.println("Aguardando....");
        String response;
        
        do{
            response = sender.getDb().get("RESPONSE");
            String error = sender.getDb().get("ERROR");
            String latency = sender.getDb().get("LATENCY");
            if(error != null) System.err.println("ERROR: "+error);
            if(latency != null) System.out.println("LATENCY: "+latency);
            System.out.println("      ~~~~~~~~~            ");
            Thread.sleep(6000);
        }while(response == null);
        System.out.println("CHEGOU: "+response);
       
        
    }
}
