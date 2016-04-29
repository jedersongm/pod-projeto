package br.ifpb.pod.receiver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jederson
 */
public interface Receiver extends Remote{
    
    public String sendServerApp(String msg) throws RemoteException;
    
    public String receive(String msg) throws RemoteException;
   
}
