package com.seethalreghunath.elixir;

public class Upload {
    private String iname;
    private String imageUri;

    public Upload(String iname, String imageUri) {
        if(iname.trim().equals(""))
            iname = "No name";
        this.iname = iname;
        this.imageUri = imageUri;
    }
    public Upload() {
        this.iname = iname;
        this.imageUri = imageUri;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
