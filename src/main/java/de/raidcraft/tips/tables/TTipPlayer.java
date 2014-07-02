package de.raidcraft.tips.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author mdoering
 */
@Entity
@Table(name = "tips_players")
@Getter
@Setter
public class TTipPlayer {

    @Id
    private int id;
    private UUID uuid;
    private String player;
    private boolean enabled = true;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "player_id")
    private List<TPlayerTip> tips = new ArrayList<>();
}
