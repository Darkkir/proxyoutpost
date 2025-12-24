package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.darkkir3.proxyoutpost.model.db.WeaponRarity;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class WeaponOutput {

    /**
     * the weapon name (e.g. 'Item_Weapon_B_Common_01_Name')
     */
    @JsonProperty("ItemName")
    public String itemName;

    /**
     * the index of the weapon rarity of this weapon
     */
    private int rarity;

    /**
     * the profession type of this weapon
     */
    @JsonProperty("ProfessionType")
    public String professionType;

    /**
     * url to weapon display image
     */
    @JsonProperty("ImagePath")
    public String imagePath;

    /**
     * the untranslated main stat value of this weapon
     */
    private WeaponProperty mainStat;

    /**
     * the untranslated secondary stat value of this weapon
     */
    private WeaponProperty secondaryStat;

    /**
     * set the untranslated main stat value of this weapon
     */
    @JsonProperty("MainStat")
    public void setMainStat(WeaponProperty mainStat) {
        this.mainStat = mainStat;
    }

    /**
     * set the untranslated secondary stat value of this weapon
     */
    @JsonProperty("SecondaryStat")
    public void setSecondaryStat(WeaponProperty secondaryStat) {
        this.secondaryStat = secondaryStat;
    }

    /**
     * set the rarity of this weapon
     * <br>2 = B-rank
     * <br>3 = A-rank
     * <br>4 = S-rank
     */
    @JsonProperty("Rarity")
    public void setRarity(int value) {
        this.rarity = value;
    }

    /**
     * @return the translated rarity of this weapon
     */
    @JsonProperty("Rarity")
    public String getRarity() {
        Optional<WeaponRarity> weaponRarity = Arrays.stream(WeaponRarity.values()).filter(t ->
                Objects.equals(this.rarity, t.getIndex())).findFirst();

        return weaponRarity.map(Enum::name).orElse(null);
    }
}
