package se.uu.ub.cora.therest.json.builder.org;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.therest.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.therest.json.builder.JsonObjectBuilder;
import se.uu.ub.cora.therest.json.builder.org.OrgJsonArrayBuilderAdapter;
import se.uu.ub.cora.therest.json.builder.org.OrgJsonObjectBuilderAdapter;
import se.uu.ub.cora.therest.json.parser.JsonArray;
import se.uu.ub.cora.therest.json.parser.JsonObject;
import se.uu.ub.cora.therest.json.parser.JsonString;

public class OrgJsonArrayBuilderAdapterTest {
	private JsonArrayBuilder jsonArrayBuilder;

	@BeforeMethod
	public void beforeMethod() {
		jsonArrayBuilder = new OrgJsonArrayBuilderAdapter();
	}

	@Test
	public void testAddStringValue() {
		jsonArrayBuilder.addString("value");
		JsonArray jsonArray = jsonArrayBuilder.toJsonArray();
		JsonString jsonString = jsonArray.getValueAsJsonString(0);
		assertEquals(jsonString.getStringValue(), "value");
	}

	@Test
	public void testAddJsonObjectBuilder() {
		JsonObjectBuilder jsonObjectBuilderChild = new OrgJsonObjectBuilderAdapter();
		jsonObjectBuilderChild.addKeyString("keyChild", "valueChild");

		jsonArrayBuilder.addJsonObjectBuilder(jsonObjectBuilderChild);
		JsonArray jsonArray = jsonArrayBuilder.toJsonArray();

		JsonObject jsonObjectChild = jsonArray.getValueAsJsonObject(0);

		JsonString jsonString = jsonObjectChild.getValueAsJsonString("keyChild");
		assertEquals(jsonString.getStringValue(), "valueChild");
	}

	@Test
	public void testAddJsonArrayBuilder() {
		JsonArrayBuilder jsonArrayBuilderChild = new OrgJsonArrayBuilderAdapter();
		jsonArrayBuilderChild.addString("value");

		jsonArrayBuilder.addJsonArrayBuilder(jsonArrayBuilderChild);
		JsonArray jsonArray = jsonArrayBuilder.toJsonArray();

		JsonArray jsonArrayChild = jsonArray.getValueAsJsonArray(0);

		JsonString jsonString = jsonArrayChild.getValueAsJsonString(0);
		assertEquals(jsonString.getStringValue(), "value");
	}

	@Test
	public void testToJsonFormattedString() {
		JsonObjectBuilder jsonObjectBuilderChild = new OrgJsonObjectBuilderAdapter();
		jsonObjectBuilderChild.addKeyString("keyChild", "valueChild");

		jsonArrayBuilder.addJsonObjectBuilder(jsonObjectBuilderChild);

		String json = jsonArrayBuilder.toJsonFormattedString();
		assertEquals(json, "[{\"keyChild\":\"valueChild\"}]");

	}
}