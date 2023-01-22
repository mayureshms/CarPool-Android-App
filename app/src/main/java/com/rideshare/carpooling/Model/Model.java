package com.rideshare.carpooling.Model;

public class Model {

    String sourceAddress;
    String destinationAddress;
    String adharNumber;
    String profilePic;
    String userName;


    String riderName,rideDate,rideTime,ridePrice,totalPassengers,userId,phoneNumber;

    public Model() {
    }

    public Model(String sourceAddress, String destinationAddress, String adharNumber, String profilePic, String userName, String riderName, String rideDate, String rideTime, String ridePrice, String totalPassengers, String userId, String phoneNumber) {
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.adharNumber = adharNumber;
        this.profilePic = profilePic;
        this.userName = userName;
        this.riderName = riderName;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.ridePrice = ridePrice;
        this.totalPassengers = totalPassengers;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getAdharNumber() {
        return adharNumber;
    }

    public void setAdharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }
    public String getMobile(){
        return phoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public String getRidePrice() {
        return ridePrice;
    }

    public void setRidePrice(String ridePrice) {
        this.ridePrice = ridePrice;
    }

    public String getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(String totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

