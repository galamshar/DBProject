package com.CarSaleWebsite.Kolesa.Models.utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class DiningTables {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long ID;

    @Min(1)
    @Max(8)
    public int chair_count=1;

    public String section;

    public DiningTables(@Min(1) @Max(8) int chair_count, String section) {
        this.chair_count = chair_count;
        this.section = section;
    }
    public DiningTables(){

    }

    public Long getTable_id() {
        return ID;
    }


    public int getChair_count() {
        return chair_count;
    }

    public void setChair_count(int chair_count) {
        this.chair_count = chair_count;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
