package me.darkkir3.proxyoutpost.rep;

import me.darkkir3.proxyoutpost.model.db.PlayerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<PlayerProfile, Long> {

}
