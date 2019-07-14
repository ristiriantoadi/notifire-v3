package com.example.appnotifirev3;

public class NotifireDevice {
    String namaDevice, statusDevice,keyDevice;

    public NotifireDevice(String namaDevice, String statusDevice,String keyDevice){
        this.namaDevice = namaDevice;
        this.statusDevice = statusDevice;
        this.keyDevice = keyDevice;
    }

    public NotifireDevice(){

    }

    public String getNamaDevice() {
        return namaDevice;
    }

    public String getStatusDevice() {
        return statusDevice;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public void setNamaDevice(String namaDevice) {
        this.namaDevice = namaDevice;
    }

    public void setStatusDevice(String statusDevice) {
        this.statusDevice = statusDevice;
    }
}
