package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;

@Entity
@Table(name="title_arguments")
public class TitleArgs {
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

    public TitleArgsPk getTitleArgsPk() {
        return titleArgsPk;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
