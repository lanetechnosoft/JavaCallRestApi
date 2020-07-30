/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javademo;

import javademo.services.WaterApiToken;
import javademo.model.Token;
import javademo.services.WaterApiCustomerInfo;

/**
 *
 * @author Lane
 */
public class CallApiCustomerInfo {
    public static void main(String[] args) {
        WaterApiToken api = new WaterApiToken();
        Token token = api.getToken();
        //System.out.println("result "+token.getStatus());
        //System.out.println("result "+token.getHeader());
        String res = new WaterApiCustomerInfo().get(token.getHeader(),"10102763");
    }
}
