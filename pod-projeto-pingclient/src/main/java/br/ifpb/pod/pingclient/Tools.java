/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.pod.pingclient;

/**
 *
 * @author jederson
 */
public final class Tools {
    
    public static int currentTime(){
        Long aux = System.currentTimeMillis() / 1000;
        return (int) Math.round(aux.doubleValue());
    }
}
