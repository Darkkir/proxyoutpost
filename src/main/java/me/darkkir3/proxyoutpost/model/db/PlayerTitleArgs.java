package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.TitleInfo;

@Entity
@Table(name="title_arguments")
public class PlayerTitleArgs implements EnkaToDBMapping<TitleInfo> {
    /**
     * primary key of this argument
     */
    @EmbeddedId
    private PlayerTitleArgsPk playerTitleArgsPk;

    /**
     * the actual value of this argument
     */
    @Column(name="argument")
    private String argument;

    /**
     * The playerProfile this title argument belongs to
     */
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    private PlayerProfile playerProfile;

    public PlayerTitleArgs() {}

    public PlayerTitleArgs(PlayerTitleArgsPk playerTitleArgsPk) {
        this.playerTitleArgsPk = playerTitleArgsPk;
    }

    public PlayerTitleArgsPk getTitleArgsPk() {
        return playerTitleArgsPk;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public PlayerProfile getProfile() {
        return playerProfile;
    }

    @Override
    public void mapEnkaDataToDB(TitleInfo enkaData) {
        if(this.playerTitleArgsPk != null && enkaData != null) {
            int argsIndex = this.playerTitleArgsPk.getArgumentIndex();
            if(enkaData.args != null && argsIndex < enkaData.args.size()) {
                this.argument = enkaData.args.get(argsIndex);
            }
        }
    }
}
