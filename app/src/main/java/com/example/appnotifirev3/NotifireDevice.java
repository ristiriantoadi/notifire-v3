package com.example.appnotifirev3;

public class NotifireDevice {
    String namaDevice, statusDevice;

    public NotifireDevice(String namaDevice, String statusDevice){
        this.namaDevice = namaDevice;
        this.statusDevice = statusDevice;
    }

    public NotifireDevice(){

    }

    public String getNamaDevice() {
        return namaDevice;
    }

    public String getStatusDevice() {
        return statusDevice;
    }

    public void setNamaDevice(String namaDevice) {
        this.namaDevice = namaDevice;
    }

    public void setStatusDevice(String statusDevice) {
        this.statusDevice = statusDevice;
    }
}
