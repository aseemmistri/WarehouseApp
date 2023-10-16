package in.nareshit.raghu;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

@SuppressWarnings("serial")
public class ShipmentTypeIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(
			SharedSessionContractImplementor session,
			Object object) throws HibernateException
	{
	    String code = "SHIP";
	    int id = new Random().nextInt(50000);
		return code+id;
	}

}
