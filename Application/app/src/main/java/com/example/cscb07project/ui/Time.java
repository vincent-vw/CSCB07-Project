package com.example.cscb07project.ui;

import java.util.Objects;

public class Time {
    private String hour;
    private String minute;

    public Time() {}

    public Time(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (!Objects.equals(hour, time.hour)) return false;
        return Objects.equals(minute, time.minute);
    }

    @Override
    public int hashCode() {
        int result = hour != null ? hour.hashCode() : 0;
        result = 31 * result + (minute != null ? minute.hashCode() : 0);
        return result;
    }
}
