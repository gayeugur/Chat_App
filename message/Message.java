/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author gayeu
 */
public class Message implements java.io.Serializable {

    public Message_Type type;
    public Object content;

    public static enum Message_Type {

        UserName, UserList, ChatPrivate, PrivateFileSender, Mess, Back, groupUsers, GroupFileSender, File
    }

    public Message(Message_Type t) {
        this.type = t;
    }

}
