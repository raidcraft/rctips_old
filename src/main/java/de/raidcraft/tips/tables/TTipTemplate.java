package de.raidcraft.tips.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mdoering
 */
@Entity
@Getter
@Setter
@Table(name = "tips_templates")
public class TTipTemplate {

    @Id
    private int id;
    @Column(unique = true)
    private String identifier;
    private String name;
    private String description;
    private boolean persistant;
    private boolean repeating;
    private long cooldown;
    @OneToMany
    private List<TPlayerTip> playerTips = new ArrayList<>();
}
