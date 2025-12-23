package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Embeddable
public class PlayerSkillLevelPk {

    /**
     * the uid of the profile this skill and agent belongs to
     */
    @JsonIgnore
    @Column(name="profileUid", nullable = false)
    private Long profileUid;

    /**
     * the agent id this belongs to
     */
    @JsonIgnore
    @Column(name="agentId", nullable = false)
    private Long agentId;

    /**
     * the actual skill index
     * <li>0=basic-attack</li>
     * <li>1=special-attack</li>
     * <li>2=dash</li>
     * <li>3=ultimate</li>
     * <li>5=core-skill</li>
     * <li>6=assist</li>
     */
    @Column(name="skillIndex", nullable = false)
    private int skillIndex;

    public PlayerSkillLevelPk() {}

    public PlayerSkillLevelPk(Long profileUid, Long agentId, int skillIndex) {
        this.profileUid = profileUid;
        this.agentId = agentId;
        this.skillIndex = skillIndex;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public Long getAgentId() {
        return agentId;
    }

    @JsonIgnore
    public int getSkillIndex() {
        return skillIndex;
    }

    @JsonProperty("Type")
    public String getSkillType() {
        Optional<SkillType> skillType = Arrays.stream(SkillType.values()).filter(t ->
                Objects.equals(this.getSkillIndex(), t.getIndex())).findFirst();

        return skillType.map(Enum::name).orElse(null);

    }
}
