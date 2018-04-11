/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dynamitos
 */
@Entity
@Table(name = "termin")
public class Termin {
    @Id
    private Long id;
    private byte von;
    private byte bis;
    @Column(precision = 100)
    private String text;
    @ManyToOne
    private KalenderTag calendarday;

    public Termin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte getVon() {
        return von;
    }

    public void setVon(byte von) {
        this.von = von;
    }

    public byte getBis() {
        return bis;
    }

    public void setBis(byte bis) {
        this.bis = bis;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public KalenderTag getCalendarday() {
        return calendarday;
    }

    public void setCalendarday(KalenderTag calendarday) {
        this.calendarday = calendarday;
    }
    
}
