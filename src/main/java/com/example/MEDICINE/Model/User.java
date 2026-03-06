package com.example.MEDICINE.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    
    private String password;
    // @Column(nullable = false)
    // @Transient
    // private String confirm_password;


    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    private String pictureUrl;

    private String contactNo;

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(
    //     name = "user_roles",
    //     joinColumns = @JoinColumn(name = "user_id"),
    //     inverseJoinColumns = @JoinColumn(name = "role_id")
    // )
    //  @JsonIgnore
    private String roles;
    
    @Column(name = "unique_id", nullable = false)
    private String uniqueId; 

    //getter and setter

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getUsername() {
         return username;
     }

     public byte[] getProfilePicture() {
        return profilePicture;
    }

     public void setProfilePicture(byte[] profilePicture) {
         this.profilePicture = profilePicture;
     }

     public String getPictureUrl() {
         return pictureUrl;
     }

     public String getContactNo() {
         return contactNo;
     }

     public void setContactNo(String contactNo) {
         this.contactNo = contactNo;
     }

     public void setUsername(String username) {
         this.username = username;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }


     public String getRoles() {
         return roles;
     }

     public void setRoles(String roles) {
         this.roles = roles;
     }

     

     public String getUniqueId() {
         return uniqueId;
     }

     public void setUniqueId(String uniqueId) {
         this.uniqueId = uniqueId;
     }

     public void setPictureUrl(String pictureUrl) {
         this.pictureUrl = pictureUrl;
     }

    //  public String getConfirm_password() {
    //      return confirm_password;
    //  }

    //  public void setConfirm_password(String confirm_password) {
    //      this.confirm_password = confirm_password;
    //  }

  


}