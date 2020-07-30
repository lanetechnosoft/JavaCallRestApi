/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javademo.model;

/**
 *
 * @author Lane
 */
public class Token {

    private Integer Status;
    private String header;
    private String body;

    public Token() {
    }

    public Token(Integer Status, String header, String body) {
        this.Status = Status;
        this.header = header;
        this.body = body;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
