package com.seethalreghunath.elixir;

public class Record  {
    private String doctors_name;
    private String hospital_name;
    private String date;
    private String time;

    public Record(String doctors_name, String hospital_name, String date, String time) {
        this.doctors_name = doctors_name;
        this.hospital_name = hospital_name;
        this.date = date;
        this.time = time;
    }
}
