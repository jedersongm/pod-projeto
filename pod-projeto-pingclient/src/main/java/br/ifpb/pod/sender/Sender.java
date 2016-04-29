/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.sender;


import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author jederson
 */
public interface Sender extends Remote{
    
    void comunicador(String msg) throws RemoteException;
    
    int lookupPing() throws RemoteException, NotBoundException;
    
    void  notifyHighLatency(String msg) throws RemoteException, NotBoundException;
    
    Map<String, String> getDb() throws RemoteException;
     
    void setDb(Map<String, String> db) throws RemoteException;
    
}
