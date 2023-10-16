package in.nareshit.raghu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.model.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, String> {

}
