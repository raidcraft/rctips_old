package de.raidcraft.tips.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author mdoering
 */
@Entity
@Getter
@Setter
@Table(name = "rc_tips_player_tips")
public class TPlayerTip {

    @Id
    private int id;
    @ManyToOne
    private TTipPlayer player;
    private String template;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp displayed;
}
