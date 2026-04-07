package org.greatbarrierreeve.daizoubu.data.model;

public class User {

    private String UserId;
    private String Useremail;

    public User(String UserId ,String email){
        this.UserId = UserId;
        this.Useremail = email;
    }

    //getter methods
    public String getUserId(){
        return UserId;
    }

    public String getUseremail(){
        return Useremail;
    }

    //Setter method

    public void SetUserId(String UserId ){
        this.UserId= UserId;
    }
    public void SetUseremail(String email){
        this.Useremail= email;
    }
}
