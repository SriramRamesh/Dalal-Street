package com.example.sriram.dalalstreet;

/**
 * Created by sriram on 17/2/16.
 */
public class Users {
    public String name,email;
    public String cash,total;
    int pid;
    public Users(String name_args,String total_args){
        name=name_args;
        total=total_args;
    }
    public Users(String name_args,String total_args,String cash_args,String email_args,int pid_args){
        name=name_args;
        total=total_args;
        email=email_args;
        cash=cash_args;
        pid=pid_args;
    }
}
