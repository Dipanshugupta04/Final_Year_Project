package com.example.MEDICINE.Service;

// DTO: EditProfileRequest.java
public class EditProfileRequest {
    private String username;
    private String password;
    private String confirm_password;
    private String pictureUrl;
    private String contactNo;
   
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
  
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public String getConfirm_password() {
        return confirm_password;
    }
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
  


}
