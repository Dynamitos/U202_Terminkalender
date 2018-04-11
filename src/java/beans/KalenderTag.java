/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Dynamitos
 */
@Entity(name = "kalendertag")
@Table(name = "kalendertag")
@NamedQueries({
    @NamedQuery(name = "loadAll", query = "SELECT kalender FROM kalendertag kalender")
})
public class KalenderTag {
    @Id
    @Column(precision = 255)
    private String id;
    @Column
    private LocalDate datum;
    @OneToMany(mappedBy = "calendarday")
    private List<Termin> termine;
    @Transient
    private Map<Integer, String> kalender;

    public KalenderTag() {
        this.id = UUID.randomUUID().toString();
        termine = new ArrayList<>();
        kalender = new HashMap<>();
        for(int i = 6; i <= 24; ++i)
        {
            kalender.put(i, "");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
    public String getFormat()
    {
        return datum.format(DateTimeFormatter.ofPattern("EEE, dd. MMM YYYY"));
    }

    public void addTermin(Termin t) {
        termine.add(t);
        for(int i = t.getVon(); i <= t.getBis(); ++i)
        {
            kalender.put(i, t.getText());
        }
    }

    public Map<Integer, String> getKalender() {
        return kalender;
    }
    
}
