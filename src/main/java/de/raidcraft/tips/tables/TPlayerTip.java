package de.raidcraft.tips.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;

/**
 * @author mdoering
 */
@Entity
@Getter
@Setter
@Table(name = "tips_player_tips")
public class TPlayerTip {

    @Id
    private int id;
    @ManyToOne
    private TTipPlayer player;
    private String template;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp displayed;
}
