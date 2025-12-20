package me.darkkir3.proxyoutpost.rep;

import me.darkkir3.proxyoutpost.model.db.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
