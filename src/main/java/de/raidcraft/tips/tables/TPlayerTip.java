package de.raidcraft.tips.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

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
    private UUID uuid;
    private String player;
    private Timestamp displayed;
    private String template;
}
