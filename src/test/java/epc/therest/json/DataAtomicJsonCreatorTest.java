package epc.therest.json;

import org.testng.Assert;
import org.testng.annotations.Test;

import epc.metadataformat.data.DataAtomic;
import epc.therest.data.RestDataAtomic;
import epc.therest.data.DataElementRest;

public class DataAtomicJsonCreatorTest {
	@Test
	public void testToJson() {
		JsonCreatorFactory jsonCreatorFactory = new JsonCreatorFactoryImp();
		DataAtomic dataAtomic = DataAtomic.withDataIdAndValue("atomicDataId", "atomicValue");
		DataElementRest dataElementRest = RestDataAtomic.fromDataAtomic(dataAtomic);

		JsonCreator jsonCreator = jsonCreatorFactory.createForDataElementRest(dataElementRest);
		String json = jsonCreator.toJson();

		Assert.assertEquals(json, "{\"atomicDataId\":\"atomicValue\"}");
	}

	@Test
	public void testToJsonEmptyValue() {
		JsonCreatorFactory jsonCreatorFactory = new JsonCreatorFactoryImp();
		DataAtomic dataAtomic = DataAtomic.withDataIdAndValue("atomicDataId", "");
		DataElementRest dataElementRest = RestDataAtomic.fromDataAtomic(dataAtomic);

		JsonCreator jsonCreator = jsonCreatorFactory.createForDataElementRest(dataElementRest);
		String json = jsonCreator.toJson();

		Assert.assertEquals(json, "{\"atomicDataId\":\"\"}");
	}
}
