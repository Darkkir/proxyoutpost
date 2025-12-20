package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.TitleInfo;

@Entity
@Table(name="title_arguments")
public class TitleArgs implements EnkaToDBMapping<TitleInfo> {
    /**
     * primary key of this argument
     */
    @EmbeddedId
    private TitleArgsPk titleArgsPk;

    /**
     * the actual value of this argument
     */
    @Column(name="argument")
    private String argument;

    /**
     * The profile this title argument belongs to
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false),
    })
    private Profile profile;

    public TitleArgs() {}

    public TitleArgs(TitleArgsPk titleArgsPk) {
        this.titleArgsPk = titleArgsPk;
    }

    public TitleArgsPk getTitleArgsPk() {
        return titleArgsPk;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void mapEnkaDataToDB(TitleInfo enkaData) {
        if(this.titleArgsPk != null && enkaData != null) {
            int argsIndex = this.titleArgsPk.getArgumentIndex();
            if(enkaData.args != null && argsIndex < enkaData.args.size()) {
                this.argument = enkaData.args.get(argsIndex);
            }
        }
    }
}
