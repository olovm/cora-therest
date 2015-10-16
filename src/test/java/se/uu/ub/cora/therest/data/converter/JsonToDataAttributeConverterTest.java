package se.uu.ub.cora.therest.data.converter;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.therest.data.RestDataAttribute;
import se.uu.ub.cora.therest.data.RestDataElement;
import se.uu.ub.cora.therest.json.parser.JsonObject;
import se.uu.ub.cora.therest.json.parser.JsonParseException;
import se.uu.ub.cora.therest.json.parser.JsonParser;
import se.uu.ub.cora.therest.json.parser.JsonValue;
import se.uu.ub.cora.therest.json.parser.org.OrgJsonParser;

public class JsonToDataAttributeConverterTest {
	private JsonParser jsonParser;

	@BeforeMethod
	public void beforeMethod() {
		jsonParser = new OrgJsonParser();
	}

	@Test
	public void testToClass() {
		String json = "{\"attributeNameInData\":\"attributeValue\"}";
		RestDataAttribute restDataAttribute = createRestDataAttributeForJsonString(json);
		Assert.assertEquals(restDataAttribute.getNameInData(), "attributeNameInData");
		Assert.assertEquals(restDataAttribute.getValue(), "attributeValue");
	}

	private RestDataAttribute createRestDataAttributeForJsonString(String json) {
		JsonValue jsonValue = jsonParser.parseString(json);
		JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
				.forJsonObject((JsonObject) jsonValue);
		RestDataElement restDataElement = jsonToDataConverter.toInstance();
		RestDataAttribute restDataAttribute = (RestDataAttribute) restDataElement;
		return restDataAttribute;
	}

	@Test
	public void testToClassEmptyValue() {
		String json = "{\"attributeNameInData\":\"\"}";
		RestDataAttribute restDataAttribute = createRestDataAttributeForJsonString(json);
		Assert.assertEquals(restDataAttribute.getNameInData(), "attributeNameInData");
		Assert.assertEquals(restDataAttribute.getValue(), "");
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJson() {
		String json = "{\"id\":[]}";

		JsonValue jsonValue = jsonParser.parseString(json);
		JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
				.forJsonObject((JsonObject) jsonValue);
		jsonToDataConverter.toInstance();
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraKeyValuePair() {
		String json = "{\"attributeNameInData\":\"attributeValue\",\"id2\":\"value2\"}";
		JsonValue jsonValue = jsonParser.parseString(json);
		JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
				.forJsonObject((JsonObject) jsonValue);
		jsonToDataConverter.toInstance();
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraArray() {
		String json = "{\"attributeNameInData\":\"attributeValue\",\"id2\":[]}";
		JsonValue jsonValue = jsonParser.parseString(json);
		JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
				.forJsonObject((JsonObject) jsonValue);
		jsonToDataConverter.toInstance();
	}

}